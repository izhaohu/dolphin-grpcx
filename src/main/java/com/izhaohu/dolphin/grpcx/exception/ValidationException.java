package com.izhaohu.dolphin.grpcx.exception;

import com.izhaohu.dolphin.grpcx.constant.ErrorCodeEnum;

public class ValidationException extends ServiceException{
    private ValidationException() {
        super(ErrorCodeEnum.INVALID_ARGUMENT);
    }
}
