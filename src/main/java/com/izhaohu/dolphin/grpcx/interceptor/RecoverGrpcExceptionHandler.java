package com.izhaohu.dolphin.grpcx.interceptor;

import com.izhaohu.dolphin.grpcx.exception.ServiceException;
import io.grpc.*;

public class RecoverGrpcExceptionHandler implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(final ServerCall<ReqT, RespT> serverCall,
                                                                 Metadata metadata,
                                                                 ServerCallHandler<ReqT, RespT> serverCallHandler) {
        ServerCall.Listener<ReqT> call = serverCallHandler.startCall(serverCall, metadata);
        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(call) {
            @Override
            public void onHalfClose() {
                try {
                    super.onHalfClose();
                } catch (ServiceException e) {
                    int errorCode = e.getCode();
                    String errorMsg = e.getMessage();
                    serverCall.close(Status.fromCodeValue(errorCode).withCause(e).withDescription(errorMsg), new Metadata());
                }catch (Exception e) {
                    serverCall.close(Status.INTERNAL.withCause(e).withDescription("error message"), new Metadata());
                }
            }
        };
    }
}
