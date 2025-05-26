package com.lake.hmediacenterserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;

@Data
@Schema(description = "修改密码请求体")
public class ChangePasswordRequest {
    // getter/setter
    @Schema(description = "旧密码", example = "123456")
    private String oldPassword;
    @Schema(description = "新密码", example = "654321")
    private String newPassword;
}
