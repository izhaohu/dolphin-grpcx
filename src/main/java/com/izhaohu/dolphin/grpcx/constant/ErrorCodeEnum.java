package com.izhaohu.dolphin.grpcx.constant;

public enum ErrorCodeEnum {
    OK(0, "正常"),
    CANCELLED(1,"取消"),
    UNKNOWN(2,"未知"),
    INVALID_ARGUMENT(3,"参数异常"),
    DEADLINE_EXCEEDED(4,"处理超时"),
    NOT_FOUND(5,"未找到数据"),
    ALREADY_EXISTS(6,"数据已经存在"),
    PERMISSION_DENIED(7,"访问被拒绝"),
    RESOURCE_EXHAUSTED(8,"无可用资源"),
    FAILED_PRECONDITION(9,"预先处理失败"),
    ABORTED(10,"访问被中断"),
    OUT_OF_RANGE(11,"访问越界"),
    UNIMPLEMENTED(12,"方法没有被实现"),
    INTERNAL(13,"内部错误"),
    UNAVAILABLE(14,"不可访问"),
    DATA_LOSS(15,"数据丢失"),
    UNAUTHENTICATED(16,"认证失败");

    private final int code;
    private final String message;

    ErrorCodeEnum(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

