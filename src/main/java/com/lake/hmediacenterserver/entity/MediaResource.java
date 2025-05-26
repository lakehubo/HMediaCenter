package com.lake.hmediacenterserver.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 多媒体资源表，集成本地文件元数据、ffprobe 技术参数及影视元信息（豆瓣风格）
 */
@Data
@Entity
@Table(name = "media_resource", indexes = {
        @Index(columnList = "filePath", unique = true)
})
public class MediaResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 本地文件基础信息
    @Column(nullable = false, length = 255)
    private String fileName;         // 文件名

    @Column(nullable = false, length = 512, unique = true)
    private String filePath;         // 本地绝对路径

    @Column
    private Long fileSize;           // 文件大小（字节）

    @Column(length = 16)
    private String mediaType;        // 类型：video/audio/image/other

    @Column
    private LocalDateTime createTime;// 入库时间

    // ffprobe 技术元数据
    @Column
    private Double duration;         // 时长（秒）

    @Column
    private Integer width;           // 视频宽度（像素）

    @Column
    private Integer height;          // 视频高度（像素）

    @Column(length = 32)
    private String videoCodec;       // 视频编码格式

    @Column(length = 32)
    private String audioCodec;       // 音频编码格式

    @Column(length = 32)
    private String format;           // 容器格式（mp4/mkv/avi等）

    // 豆瓣/影视元信息
    @Column(length = 255)
    private String title;            // 片名

    @Column(length = 255)
    private String originalTitle;    // 原名

    @Column
    private Integer year;            // 年份

    @Column(length = 255)
    private String directors;        // 导演（逗号分隔）

    @Column(length = 512)
    private String casts;            // 主演（逗号分隔）

    @Column(length = 128)
    private String genres;           // 类型（逗号分隔）

    @Lob
    @Column(columnDefinition = "TEXT")
    private String summary;          // 简介

    @Column
    private Double doubanScore;      // 豆瓣评分

    @Column
    private Integer doubanVotes;     // 豆瓣评价人数

    @Column(length = 64)
    private String region;           // 地区

    @Column(length = 64)
    private String language;         // 语言

    @Column(length = 512)
    private String coverUrl;         // 封面海报地址

    // 其它信息
    @Column(length = 32)
    private String imdbId;           // IMDB 编号

    @Column(length = 32)
    private String doubanId;         // 豆瓣ID
}
