package com.hanergy.out.exceptionHandler;

/**
 * 自定义登录异常
 */
public class LoginException extends RuntimeException{

    //状态码
    private int code;

    //错误信息
    private String message;

    public LoginException(int code,String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
