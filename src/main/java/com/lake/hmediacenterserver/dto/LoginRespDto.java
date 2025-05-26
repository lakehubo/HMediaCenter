package com.lake.hmediacenterserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;

/**
 * 登录成功响应体
 */
@Data
@Schema(description = "登录响应体")
public class LoginRespDto {
    @Schema(description = "JWT Token")
    private String token;
    @Schema(description = "用户名", example = "admin")
    private String username;

    public LoginRespDto(String token, String username) {
        this.token = token;
        this.username = username;
    }
}
