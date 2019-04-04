package com.izhaohu.dolphin.grpcx.exception;

import com.izhaohu.dolphin.grpcx.constant.ErrorCodeEnum;

public class ServiceException extends  RuntimeException {
    private final Integer code;

    public ServiceException(ErrorCodeEnum error) {
        this(error.getCode(), error.getMessage());
    }

    public ServiceException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
