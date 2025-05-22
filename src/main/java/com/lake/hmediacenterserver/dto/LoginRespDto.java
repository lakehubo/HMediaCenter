package com.lake.hmediacenterserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 登录成功响应体
 */
@Schema(description = "登录响应体")
public class LoginRespDto {
    @Schema(description = "JWT Token")
    private String token;
    @Schema(description = "用户名", example = "admin")
    private String username;

    public LoginRespDto() {}
    public LoginRespDto(String token, String username) {
        this.token = token;
        this.username = username;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
