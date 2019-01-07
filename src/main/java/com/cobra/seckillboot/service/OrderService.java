package com.cobra.seckillboot.service;

import com.cobra.seckillboot.error.BusinessException;
import com.cobra.seckillboot.service.model.OrderModel;

/**
 * @Author: Baron
 * @Description:
 * @Date: Created in 2019/1/2 14:09
 */
public interface OrderService {

    //（项目使用方案）1通过前端url上传过来秒杀活动id，然后下单接口校验对应id是否属于对应商品且活动已开始
    //2直接在下单接口内判断对应的商品是否存在秒杀活动，若存在进行中的则以秒杀价格下单
    OrderModel createOrder(Integer userId,Integer itemId,Integer promoId,Integer amount) throws BusinessException;
}
