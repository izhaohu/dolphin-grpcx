package com.izhaohu.dolphin.grpcx.util;

import io.grpc.Metadata;
import io.grpc.stub.AbstractStub;
import io.grpc.stub.MetadataUtils;

import java.util.Map;

public class MetadataUtil<T> {

    private MetadataUtil() {

    }

    

    public static <T extends AbstractStub<T>> T attachHeaders(T stub, final Map<String, String> headerMap) {
        Metadata extraHeaders = new Metadata();
        if (headerMap != null) {
            for (String key : headerMap.keySet()) {
                Metadata.Key<String> customHeaderKey = Metadata.Key.of(key, Metadata.ASCII_STRING_MARSHALLER);
                extraHeaders.put(customHeaderKey, headerMap.get(key));
            }
        }
        return MetadataUtils.attachHeaders(stub, extraHeaders);
    }
}
