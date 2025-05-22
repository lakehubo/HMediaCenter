package com.lake.hmediacenterserver.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "修改密码请求体")
public class ChangePasswordRequest {
    @Schema(description = "旧密码", example = "123456")
    private String oldPassword;
    @Schema(description = "新密码", example = "654321")
    private String newPassword;
    // getter/setter
    public String getOldPassword() { return oldPassword; }
    public void setOldPassword(String oldPassword) { this.oldPassword = oldPassword; }
    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
}
