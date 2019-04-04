package com.izhaohu.dolphin.grpcx.interceptor;

import io.grpc.*;

import java.util.Arrays;
import java.util.List;

public class ClientInterceptorChain implements ClientInterceptor {
    private final List<ClientInterceptor> interceptors;

    public ClientInterceptorChain(List<ClientInterceptor> clientInterceptors) {
        this.interceptors = clientInterceptors;
    }

    public ClientInterceptorChain(ClientInterceptor... clientInterceptors) {
        this(Arrays.asList(clientInterceptors));
    }

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
        for (int i = interceptors.size() - 1; i >= 0; i--) {
            channel = new InterceptorChannel(channel, interceptors.get(i));
        }

        return channel.newCall(methodDescriptor, callOptions);
    }

    private static final class InterceptorChannel extends Channel {
        private final Channel channel;
        private final ClientInterceptor clientInterceptor;

        private InterceptorChannel(Channel channel, ClientInterceptor interceptor) {
            this.channel = channel;
            this.clientInterceptor = interceptor;
        }
        @Override
        public <RequestT, ResponseT> ClientCall<RequestT, ResponseT> newCall(MethodDescriptor<RequestT, ResponseT> methodDescriptor, CallOptions callOptions) {
            return clientInterceptor.interceptCall(methodDescriptor, callOptions, channel);
        }

        @Override
        public String authority() {
            return channel.authority();
        }
    }

    public List<ClientInterceptor> getInterceptors() {
        return interceptors;
    }

}
