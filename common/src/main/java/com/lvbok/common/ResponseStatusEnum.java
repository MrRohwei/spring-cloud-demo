package com.lvbok.common;

public enum ResponseStatusEnum {

    SUCCESS(200, "成功"),
    FAIL(500, "失败");

    private int code;
    private String msg;
    private ResponseStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static boolean match(ResponseStatusEnum responseStatusEnum) {
        ResponseStatusEnum[] values = ResponseStatusEnum.values();
        for (ResponseStatusEnum value : values) {
            if (responseStatusEnum.getCode() == value.getCode()) {
                return true;
            }
        }
        return false;
    }

    public static ResponseStatusEnum getByCode(int code) {
        ResponseStatusEnum[] values = ResponseStatusEnum.values();
        for (ResponseStatusEnum value : values) {
            if (code == value.getCode()) {
                return value;
            }
        }
        return null;
    }
}
