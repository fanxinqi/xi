package com.xyb.exception;

/**
 * @Author lian
 * @Date 2018/3/20
 */
public class RestInfo<T> {
    public static final Integer OK = 0;
    public static final Integer ERROR = 100;
    public static final Integer AUTHORITY_ERROR = 101;
    public static final Integer PARAM_ERROR = 102;
    public static final Integer INPUT_ERROR = 103;
    public static final Integer OPERATE_ERROR = 104;
    public static final String OPERATE_ERROR_STRING = "操作失败";


    private Integer code =this.OK;
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

    public RestInfo setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public RestInfo setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public RestInfo setUrl(String url) {
        this.url = url;
        return this;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
