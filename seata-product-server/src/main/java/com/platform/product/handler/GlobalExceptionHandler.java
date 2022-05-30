package com.platform.product.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * author:HUAWEI
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public AjaxResult handleException(Exception e, HttpServletRequest request) {
        logger.warn("请求url:[{}]出错", request.getRequestURL(), e);
        return AjaxResult.error(e.getMessage());
    }

//    @ExceptionHandler(BusinessException.class)
//    @ResponseBody
//    public Result handleException(BusinessException e, HttpServletRequest request) {
//        logger.warn("请求url:[{}]出错", request.getRequestURL(), e);
//        return ResultUtils.error(e.getCode(),e.getMessage());
//    }
}
