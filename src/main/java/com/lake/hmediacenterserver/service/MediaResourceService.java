package com.lake.hmediacenterserver.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lake.hmediacenterserver.entity.MediaResource;
import com.lake.hmediacenterserver.repository.MediaResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MediaResourceService {

    private final MediaResourceRepository resourceRepository;

    @Value("${media.ffprobe-path:ffprobe}")
    private String ffprobePath; // 默认用 ffprobe

    /** 查询所有媒体资源 */
    public List<MediaResource> listAll() {
        return resourceRepository.findAll();
    }

    /** 按ID查找 */
    public Optional<MediaResource> findById(Long id) {
        return resourceRepository.findById(id);
    }

    /** 新增或更新媒体资源 */
    public MediaResource save(MediaResource resource) {
        if (resource.getId() == null) {
            resource.setCreateTime(LocalDateTime.now());
        }
        return resourceRepository.save(resource);
    }

    /** 删除媒体资源 */
    public void deleteById(Long id) {
        resourceRepository.deleteById(id);
    }

    /** 按路径查找 */
    public Optional<MediaResource> findByFilePath(String path) {
        return resourceRepository.findByFilePath(path);
    }

    /**
     * 同步指定目录下的多媒体文件到数据库，提取元数据
     * @return 新增的条数
     */
    public int syncFromFolder(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) return 0;
        int added = 0;
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                String lower = file.getName().toLowerCase();
                // 扩展名可自定义
                if (lower.endsWith(".mp4") || lower.endsWith(".mkv") || lower.endsWith(".avi") ||
                        lower.endsWith(".mp3") || lower.endsWith(".flac") ||
                        lower.endsWith(".jpg") || lower.endsWith(".png")) {

                    if (resourceRepository.findByFilePath(file.getAbsolutePath()).isPresent()) {
                        continue; // 已有则跳过
                    }
                    MediaResource media = new MediaResource();
                    media.setFileName(file.getName());
                    media.setFilePath(file.getAbsolutePath());
                    media.setFileSize(file.length());
                    media.setMediaType(getMediaType(lower));
                    media.setCreateTime(LocalDateTime.now());

                    // ffprobe 自动获取媒体信息（如出错可忽略不赋值）
                    try {
                        enrichWithFFprobe(media, file.getAbsolutePath());
                    } catch (Exception e) {
                        // 可记录日志
                    }

                    resourceRepository.save(media);
                    added++;
                }
            }
        }
        return added;
    }

    private String getMediaType(String name) {
        if (name.endsWith(".mp4") || name.endsWith(".mkv") || name.endsWith(".avi")) return "video";
        if (name.endsWith(".mp3") || name.endsWith(".flac")) return "audio";
        if (name.endsWith(".jpg") || name.endsWith(".png")) return "image";
        return "other";
    }

    /**
     * 用 ffprobe 自动补全技术元数据
     */
    private void enrichWithFFprobe(MediaResource media, String filePath) throws IOException {
        // ffprobePath 可是 "ffprobe" 或 "/usr/local/bin/ffprobe" 或 "D:\\tools\\ffmpeg\\bin\\ffprobe.exe"
        ProcessBuilder pb = new ProcessBuilder(
                ffprobePath,
                "-v", "quiet",
                "-print_format", "json",
                "-show_format",
                "-show_streams",
                filePath
        );
        pb.redirectErrorStream(true);
        Process process = pb.start();
        try (InputStream is = process.getInputStream()) {
            String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            // duration, format
            if (root.has("format")) {
                JsonNode format = root.get("format");
                if (format.has("duration")) {
                    media.setDuration(format.get("duration").asDouble());
                }
                if (format.has("format_name")) {
                    media.setFormat(format.get("format_name").asText());
                }
            }
            // streams
            if (root.has("streams")) {
                for (JsonNode stream : root.get("streams")) {
                    String codecType = stream.path("codec_type").asText();
                    if ("video".equals(codecType)) {
                        media.setVideoCodec(stream.path("codec_name").asText(null));
                        media.setWidth(stream.path("width").asInt(0));
                        media.setHeight(stream.path("height").asInt(0));
                    } else if ("audio".equals(codecType)) {
                        media.setAudioCodec(stream.path("codec_name").asText(null));
                    }
                }
            }
        }
    }
}

