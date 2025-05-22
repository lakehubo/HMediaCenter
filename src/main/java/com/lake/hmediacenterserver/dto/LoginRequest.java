package com.lake.hmediacenterserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "登录请求体")
public class LoginRequest {
    @Schema(description = "用户名", example = "admin")
    private String username;
    @Schema(description = "密码", example = "123456")
    private String password;
    // getter/setter
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
