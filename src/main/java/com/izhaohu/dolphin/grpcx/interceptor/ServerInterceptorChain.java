package com.izhaohu.dolphin.grpcx.interceptor;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;

import java.util.Arrays;
import java.util.List;

public class ServerInterceptorChain implements ServerInterceptor {
    private final List<ServerInterceptor> interceptors;

    public ServerInterceptorChain(List<ServerInterceptor> serverInterceptors) {
        this.interceptors = serverInterceptors;
    }

    public ServerInterceptorChain(ServerInterceptor... serverInterceptors) {
        this(Arrays.asList(serverInterceptors));
    }

    @Override

    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        for (int i = interceptors.size() - 1; i >= 0; i--) {
            serverCallHandler = new InterceptorServerCallHandler<>(serverCallHandler, interceptors.get(i));
        }
        return serverCallHandler.startCall(serverCall, metadata);
    }

    private static class InterceptorServerCallHandler<ReqT, RespT> implements ServerCallHandler<ReqT, RespT> {
        private final ServerCallHandler<ReqT, RespT> serverCallHandler;
        private final ServerInterceptor interceptor;

        public InterceptorServerCallHandler(ServerCallHandler<ReqT, RespT> serverCallHandler, ServerInterceptor interceptor) {
            this.serverCallHandler = serverCallHandler;
            this.interceptor = interceptor;
        }

        @Override
        public ServerCall.Listener<ReqT> startCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata) {
            return interceptor.interceptCall(serverCall, metadata, serverCallHandler);
        }
    }

    public List<ServerInterceptor> getInterceptors() {
        return interceptors;
    }

}
