package com.lake.hmediacenterserver.common;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 业务异常基类，用于统一处理业务层手动抛出的异常
 */
@Schema(description = "业务异常")
public class BizException extends RuntimeException {

    private final int code;

    /**
     * 构造业务异常，默认消息
     * @param code 业务错误码
     */
    public BizException(int code) {
        super("业务异常");
        this.code = code;
    }

    /**
     * 构造业务异常，带错误提示
     * @param code 业务错误码
     * @param message 错误说明
     */
    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 获取错误码
     */
    public int getCode() {
        return code;
    }
}
