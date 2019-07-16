package org.nico.codegenerator.consts;

public enum MailType {

    REGISTER(1, "注册"),
    ;
    
    private int code;
    
    private String msg;

    private MailType(int code, String msg) {
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
    
    public static boolean contains(int code) {
        for(MailType t: values()) {
            if(t.code == code) {
                return true;
            }
        }
        return false;
    }
    
    public static MailType parse(int code) {
        for(MailType t: values()) {
            if(t.code == code) {
                return t;
            }
        }
        return null;
    }
    
}
