package org.nico.codegenerator.consts;

public enum FrameHandleStatus {

    PROCESSING(0, "处理中"),

    SUCCESS(1, "处理成功"),

    FAILURE(2, "处理失败")
    ;

    private int code;

    private String msg;

    private FrameHandleStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
