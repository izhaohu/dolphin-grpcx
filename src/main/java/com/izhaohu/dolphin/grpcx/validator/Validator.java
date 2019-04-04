package com.izhaohu.dolphin.grpcx.validator;

import com.izhaohu.dolphin.grpcx.exception.ValidationException;

public interface Validator {
    public <ReqT> void validate(ReqT message) throws ValidationException;
}
