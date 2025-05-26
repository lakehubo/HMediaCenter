package com.lake.hmediacenterserver.controller;

import com.lake.hmediacenterserver.common.ApiResponse;
import com.lake.hmediacenterserver.entity.MediaResource;
import com.lake.hmediacenterserver.service.MediaResourceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "多媒体资源管理")
@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
public class MediaResourceController {

    private final MediaResourceService mediaService;

    /** 查询所有资源 */
    @GetMapping("/list")
    public ApiResponse<List<MediaResource>> listAll() {
        return ApiResponse.success(mediaService.listAll());
    }

    /** 查询详情 */
    @GetMapping("/{id}")
    public ApiResponse<MediaResource> getById(@PathVariable Long id) {
        return mediaService.findById(id)
                .map(ApiResponse::success)
                .orElse(ApiResponse.fail(404, "资源不存在"));
    }

    /** 新增/编辑资源（可以用于手动补录信息） */
    @PostMapping("/save")
    public ApiResponse<MediaResource> save(@RequestBody MediaResource resource) {
        return ApiResponse.success(mediaService.save(resource));
    }

    /** 删除资源 */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        mediaService.deleteById(id);
        return ApiResponse.success();
    }

    /** 扫描指定目录并同步入库 */
    @PostMapping("/sync")
    public ApiResponse<Integer> sync(@RequestParam String folderPath) {
        int count = mediaService.syncFromFolder(folderPath);
        return ApiResponse.success(count, "同步新增 " + count + " 条资源");
    }

    /** 文件直链（流式下载/在线播放） */
    @GetMapping("/file")
    public void getFile(@RequestParam String path, HttpServletResponse response) throws Exception {
        java.io.File file = new java.io.File(path);
        if (!file.exists()) {
            response.setStatus(404);
            return;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        try (java.io.InputStream is = new java.io.FileInputStream(file);
             java.io.OutputStream os = response.getOutputStream()) {
            byte[] buf = new byte[8192];
            int len;
            while ((len = is.read(buf)) != -1) {
                os.write(buf, 0, len);
            }
        }
    }
}
