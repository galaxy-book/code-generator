package org.nico.codegenerator.exception;

import org.nico.codegenerator.consts.RespCode;

public class ByteAnimationException extends Exception{

	private static final long serialVersionUID = -4285570151529511652L;

	private int code;
	
	private String message;
	
	private RespCode respCode;

	public ByteAnimationException(RespCode respCode) {
		this.code = respCode.getCode();
		this.message = respCode.getMsg();
		this.respCode = respCode;
	}
	
	public ByteAnimationException() {
		super();
	}

	public ByteAnimationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ByteAnimationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ByteAnimationException(String message) {
		super(message);
	}

	public ByteAnimationException(Throwable cause) {
		super(cause);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public RespCode getRespCode() {
        return respCode;
    }

    public void setRespCode(RespCode respCode) {
        this.respCode = respCode;
    }
	
}
