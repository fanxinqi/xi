package com.xyb.exception;

/**
 * @Author lian
 * @Date 2018/3/20
 */
public class RestInfo<T> {
    public static final Integer OK = 0;
    public static final Integer ERROR = 100;
    public static final Integer PARAM_ERROR = 101;
    public static final Integer INPUT_ERROR = 101;


    private Integer code;
    private String message;
    private String url;
    private T data;

    public RestInfo() {
    }

    public RestInfo(T data) {
        this.data = data;
    }

    public static Integer getOK() {
        return OK;
    }

    public static Integer getERROR() {
        return ERROR;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
