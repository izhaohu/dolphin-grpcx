package com.izhaohu.dolphin.grpcx.interceptor;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;

public abstract class AbstractServerInterceptor implements ServerInterceptor {

    public abstract <ReqT, RespT>  ServerCall.Listener<ReqT> handle(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler);


    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        return handle(serverCall, metadata, serverCallHandler);
    }

}
