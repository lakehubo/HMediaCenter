package com.lake.hmediacenterserver.common;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 通用 API 响应包装类
 */
@Schema(description = "统一API返回格式")
public class ApiResponse<T> {
    @Schema(description = "业务码，0表示成功")
    private int code;

    @Schema(description = "提示消息")
    private String msg;

    @Schema(description = "响应数据")
    private T data;

    // 构造方法
    public ApiResponse() {}

    public ApiResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // 快速构建静态方法
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(0, "success", data);
    }

    public static <T> ApiResponse<T> success(String msg, T data) {
        return new ApiResponse<>(0, msg, data);
    }

    public static <T> ApiResponse<T> fail(int code, String msg) {
        return new ApiResponse<>(code, msg, null);
    }

    // getter/setter
    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }
    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
}
