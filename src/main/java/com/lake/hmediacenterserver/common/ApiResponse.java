package com.lake.hmediacenterserver.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一API响应包装类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "统一接口响应包装")
public class ApiResponse<T> {
    @Schema(description = "业务状态码（200表示成功）")
    private int code;

    @Schema(description = "提示信息")
    private String msg;

    @Schema(description = "响应数据")
    private T data;

    /** 成功（有数据，有消息） */
    public static <T> ApiResponse<T> success(T data, String msg) {
        return new ApiResponse<>(200, msg, data);
    }
    /** 成功（有数据，默认消息） */
    public static <T> ApiResponse<T> success(T data) {
        return success(data, "ok");
    }
    /** 成功（无数据，默认消息） */
    public static <T> ApiResponse<T> success() {
        return success(null, "ok");
    }
    /** 失败（指定code和消息） */
    public static <T> ApiResponse<T> fail(int code, String msg) {
        return new ApiResponse<>(code, msg, null);
    }
    /** 失败（默认500） */
    public static <T> ApiResponse<T> fail(String msg) {
        return fail(500, msg);
    }
}
