package com.lvbok.common;

import lombok.Data;

@Data
public class CommonResponse<T> {

    private int code;
    private String msg;
    private T data;

    public static CommonResponse success() {
        return CommonResponse.success(null);
    }

    public static CommonResponse success(Object data) {
        CommonResponse response = new CommonResponse<>();
        response.setCode(ResponseStatusEnum.SUCCESS.getCode());
        response.setMsg(ResponseStatusEnum.SUCCESS.getMsg());
        response.setData(data);
        return response;
    }

    public static CommonResponse fail() {
        return CommonResponse.fail(ResponseStatusEnum.FAIL);
    }

    public static CommonResponse fail(ResponseStatusEnum responseStatusEnum) {
        return CommonResponse.fail(responseStatusEnum.getCode(), responseStatusEnum.getMsg());
    }

    public static CommonResponse fail(ResponseStatusEnum responseStatusEnum, String msg) {
        return CommonResponse.fail(responseStatusEnum.getCode(), msg);
    }

    public static CommonResponse fail(int code, String msg) {
        return CommonResponse.fail(code, msg, null);
    }

    public static CommonResponse fail(int code, String msg, Object data) {
        CommonResponse response = new CommonResponse<>();
        response.setCode(code);
        response.setMsg(msg);
        response.setData(data);
        return response;
    }
}
