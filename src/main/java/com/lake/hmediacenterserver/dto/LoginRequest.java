package com.lake.hmediacenterserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;

@Data
@Schema(description = "登录请求体")
public class LoginRequest {
    // getter/setter
    @Schema(description = "用户名", example = "admin")
    private String username;
    @Schema(description = "密码", example = "123456")
    private String password;
}
