package com.cobra.seckillboot.controller;

import com.cobra.seckillboot.error.BusinessException;
import com.cobra.seckillboot.error.EmBusinessError;
import com.cobra.seckillboot.response.CommonReturnType;
import com.cobra.seckillboot.service.OrderService;
import com.cobra.seckillboot.service.model.OrderModel;
import com.cobra.seckillboot.service.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Baron
 * @Description:
 * @Date: Created in 2019/1/2 17:32
 */
@RestController
@RequestMapping("/orders")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
//@Slf4j
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 生产订单
     * @param itemId
     * @param amount
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/creation",method = RequestMethod.POST,consumes = CONTENT_TYPE_FORMED)
    public CommonReturnType createOrder(@RequestParam("itemId") Integer itemId,
                                        @RequestParam("promoId") Integer promoId,
                                        @RequestParam(value = "amount",required = false) Integer amount) throws BusinessException {
        Boolean islogin = (Boolean)this.httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (islogin==null|| !islogin.booleanValue()) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_OUT);
        }

        UserModel userModel = (UserModel)this.httpServletRequest.getSession().getAttribute("LOGIN_USER");
        OrderModel orderModel = orderService.createOrder(userModel.getId(),itemId,promoId,amount);
        //log.info("123456");
        return CommonReturnType.create(null);
    }
}
