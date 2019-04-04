package com.izhaohu.dolphin.grpcx.interceptor;

import com.izhaohu.dolphin.grpcx.validator.Validator;
import io.grpc.ForwardingServerCallListener;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;

public class ValidatorServerInterceptor extends AbstractServerInterceptor {
    private Validator validator;

    public ValidatorServerInterceptor() {

    }

    public ValidatorServerInterceptor(Validator validator) {
        this.validator = validator;
    }

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> handle(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(serverCallHandler.startCall(serverCall, metadata)) {

            @Override
            public void onMessage(ReqT message) {
                validator.validate(message);
                super.onMessage(message);
            }
        };
    }

    public Validator getValidator() {
        return validator;
    }
}
