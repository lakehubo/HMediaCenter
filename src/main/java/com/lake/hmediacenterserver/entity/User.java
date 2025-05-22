package com.lake.hmediacenterserver.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户实体类，对应数据库中的 user 表
 */
@Entity
@Table(name = "user")
@Data
public class User {
    /** 用户ID，自增主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 用户名，唯一且不能为空 */
    @Column(unique = true, nullable = false)
    private String username;

    /** 密码，不能为空（建议加密存储） */
    @Column(nullable = false)
    private String password;

    /** 是否启用，默认 true */
    private Boolean enabled = true;

    /** 创建时间，默认当前时间 */
    private LocalDateTime createTime = LocalDateTime.now();
}
