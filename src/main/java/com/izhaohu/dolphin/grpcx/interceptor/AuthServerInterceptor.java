package com.izhaohu.dolphin.grpcx.interceptor;

import com.izhaohu.dolphin.grpcx.constant.ErrorCodeEnum;
import com.izhaohu.dolphin.grpcx.exception.ServiceException;
import io.grpc.*;

import java.util.logging.Logger;

public class AuthServerInterceptor extends AbstractServerInterceptor {
    private Logger logger = Logger.getLogger(AuthServerInterceptor.class.getName());
    public static final String USER_ID_KEY = "user_id";
    public static final String TOKEN_KEY = "token";


    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> handle(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        Metadata.Key<String> token = Metadata.Key.of(TOKEN_KEY, Metadata.ASCII_STRING_MARSHALLER);
        final Metadata.Key<String> userIdKey = Metadata.Key.of(USER_ID_KEY, Metadata.ASCII_STRING_MARSHALLER);


        String tokenStr = metadata.get(token);
        logger.info("收到客户端token: "+tokenStr);

        //处理请求头信息是否合法
        if (tokenStr==null || tokenStr.length()==0){
            serverCall.close(Status.UNAUTHENTICATED
                    .withCause(new ServiceException(ErrorCodeEnum.UNAUTHENTICATED.getCode(),
                            ErrorCodeEnum.UNAUTHENTICATED.getMessage())).withDescription(ErrorCodeEnum.UNAUTHENTICATED.getMessage()), new Metadata());
        }

        final String userId = "1000";

        ServerCall<ReqT, RespT> result = new ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT>(serverCall) {
            @Override
            public void sendHeaders(Metadata headers) {
                //给客户端返回头信息
                headers.put(userIdKey,userId);
                super.sendHeaders(headers);
            }
        };
        return serverCallHandler.startCall(result,metadata);
    }
}
