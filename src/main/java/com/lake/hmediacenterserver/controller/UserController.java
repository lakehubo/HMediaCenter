package com.lake.hmediacenterserver.controller;

import com.lake.hmediacenterserver.common.ApiResponse;
import com.lake.hmediacenterserver.common.BizException;
import com.lake.hmediacenterserver.dto.ChangePasswordRequest;
import com.lake.hmediacenterserver.dto.LoginRequest;
import com.lake.hmediacenterserver.dto.LoginRespDto;
import com.lake.hmediacenterserver.entity.User;
import com.lake.hmediacenterserver.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * 用户相关接口
 */
@RestController
@RequestMapping("/api/user")
@Tag(name = "用户管理", description = "用户登录、资料、鉴权接口")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录（免鉴权）
     */
    @Operation(
            summary = "登录",
            description = "管理员登录，返回JWT Token"
//            responses = {
//                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "登录成功"),
//                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "用户名或密码错误")
//            }
    )
    @PostMapping("/login")
    public ApiResponse<LoginRespDto> login(@RequestBody LoginRequest req) {
        User user = userService.findByUsername(req.getUsername());
        if (user == null || !userService.checkPassword(req.getPassword(), user.getPassword())) {
            throw new BizException(401, "用户名或密码错误");
        }
        if (!Boolean.TRUE.equals(user.getEnabled())) {
            throw new BizException(403, "账号已被禁用");
        }

        // 这里应生成真实的 JWT Token，示例用假 token 替代
        String token = userService.generateToken(user); // 建议封装 JWT 生成
        LoginRespDto resp = new LoginRespDto(token, user.getUsername());
        return ApiResponse.success(resp);
    }

    /**
     * 获取当前用户信息（需鉴权）
     */
    @Operation(
            summary = "当前用户信息",
            description = "返回当前登录用户信息",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping("/me")
    public ApiResponse<User> me(Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new BizException(404, "用户不存在");
        }
        user.setPassword(null); // 不返回密码
        return ApiResponse.success(user);
    }

    /**
     * 修改密码（需鉴权）
     */
    @Operation(
            summary = "修改密码",
            description = "修改当前登录用户的密码",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PostMapping("/change-password")
    public ApiResponse<Void> changePassword(
            @RequestBody ChangePasswordRequest req,
            Authentication authentication) {
        String username = authentication.getName();
        boolean ok = userService.changePassword(username, req.getOldPassword(), req.getNewPassword());
        if (!ok) {
            throw new BizException(400, "原密码错误或新密码不合法");
        }
        return ApiResponse.success("修改成功", null);
    }

    /**
     * 校验token有效性（需鉴权）
     */
    @Operation(
            summary = "校验token",
            description = "判断token是否有效（前端可用来校验登录态）",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @GetMapping("/check-token")
    public ApiResponse<Void> checkToken() {
        return ApiResponse.success("Token 有效", null);
    }
}
