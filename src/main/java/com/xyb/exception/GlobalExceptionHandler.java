package com.xyb.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author lian
 * @Date 2018/3/20
 */
@EnableWebMvc
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public RestInfo<String> jsonErrorHandler(HttpServletRequest req, MyException e) throws Exception {
        RestInfo<String> r = new RestInfo<>();
        r.setMessage(e.getMessage());
        r.setCode(RestInfo.ERROR);
        r.setData("Some Data");
        r.setUrl(req.getRequestURL().toString());
        return r;
    }
    @ExceptionHandler(value = AuthorityException.class)
    @ResponseBody
    public RestInfo<String> emptyResultDataErrorHandler(HttpServletRequest req, MyException e) throws Exception {
        RestInfo<String> r = new RestInfo<>();
        r.setMessage(e.getMessage());
        r.setCode(RestInfo.AUTHORITY_ERROR);
        r.setData("权限错误");
        r.setUrl(req.getRequestURL().toString());
        return r;
    }
}
