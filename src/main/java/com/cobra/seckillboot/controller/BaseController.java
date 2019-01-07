package com.cobra.seckillboot.controller;

import com.cobra.seckillboot.error.BusinessException;
import com.cobra.seckillboot.error.EmBusinessError;
import com.cobra.seckillboot.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Baron
 * @Description:
 * @Date: Created in 2018/12/28 19:52
 */
public class BaseController {

    public static final String CONTENT_TYPE_FORMED="application/x-www-form-urlencoded";

    //定义exceptionhandler解决未被controller层吸收的exception
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonReturnType handlerException(HttpServletRequest request, Exception ex) {
        Map<String,Object> responseDate = new HashMap<>(16);
        if (ex instanceof BusinessException){
            BusinessException businessException = (BusinessException) ex;
            //输出错误信息
            responseDate.put("errCode",businessException.getErrCode());
            responseDate.put("errMsg",businessException.getErrMsg());
        }else {
            //输出错误信息
            ex.printStackTrace();
            responseDate.put("errCode",EmBusinessError.UNKONWN_ERROR.getErrCode());
            responseDate.put("errMsg",EmBusinessError.UNKONWN_ERROR.getErrMsg());
        }
        return CommonReturnType.create(responseDate,"fail");
    }

}
