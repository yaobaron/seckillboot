package com.cobra.seckillboot.service.Impl;

import com.cobra.seckillboot.dao.OrderDOMapper;
import com.cobra.seckillboot.dataobject.OrderDO;
import com.cobra.seckillboot.error.BusinessException;
import com.cobra.seckillboot.error.EmBusinessError;
import com.cobra.seckillboot.service.ItemService;
import com.cobra.seckillboot.service.OrderService;
import com.cobra.seckillboot.service.UserService;
import com.cobra.seckillboot.service.model.ItemModel;
import com.cobra.seckillboot.service.model.OrderModel;
import com.cobra.seckillboot.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Author: Baron
 * @Description:
 * @Date: Created in 2019/1/2 14:13
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDOMapper orderDOMapper;



    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BusinessException {
        //1.校验下单状态，下单的商品是否存在，用户是否合法，购买数量是否正确
        ItemModel itemModel = itemService.getItemById(itemId);
        if (itemModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "商品不存在！");
        }
        UserModel userModel = userService.getUserById(userId);
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "用户信息有误！");
        }
        if (amount <= 0 || amount > 99) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "数量信息不合法！");
        }

        //校验活动信息
        if (promoId!=null) {
            //(1)校验对应活动是否存在这个适用商品
            if (promoId.intValue()!=itemModel.getPromoModel().getId()) {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "活动信息不正确！");
            }else if(itemModel.getPromoModel().getStatus().intValue()!=2){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "活动未开始！");
            }
        }

        //2.落单减库存，支付减库存
        Boolean result = itemService.decreaseStock(itemId, amount);
        if (!result) {
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }
        //3.订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        //分情况判断价格
        if (promoId!=null) {
            orderModel.setItemPrice(itemModel.getPromoModel().getPromoPrice());
        }else {
            orderModel.setItemPrice(itemModel.getPrice());
        }

        orderModel.setPromoId(promoId);
        orderModel.setOrderPrice(orderModel.getItemPrice().multiply(new BigDecimal(amount)));
        //生成交易流水号
        orderModel.setId(generateOrderNO(userId));

        OrderDO orderDO = this.convertFromOrderModel(orderModel);
        orderDOMapper.insertSelective(orderDO);

        //商品销量增加
        itemService.creaseSales(itemId,amount);

        //4.返回信息给上层
        return orderModel;
    }

    /**
     * 订单号生产规格：时间戳+用户id（6位）
     * @return
     */
    private String generateOrderNO(Integer userId) {
        StringBuilder stringBuilder = new StringBuilder();
        Date curDate = new Date();
        String dateStr4yyMMddHHmmssSSS = new SimpleDateFormat("yyMMddHHmmssSSS").format(curDate);
        stringBuilder.append(dateStr4yyMMddHHmmssSSS);
        String userIdStr = String.valueOf(userId);
        for(int i=0; i<6-userIdStr.length();i++){
            stringBuilder.append(0);
        }
        stringBuilder.append(userIdStr);
        return stringBuilder.toString();
    }


    /**
     * 转化成dataobject
     *
     * @param orderModel
     * @return
     */
    private OrderDO convertFromOrderModel(OrderModel orderModel) {
        if (orderModel == null) {
            return null;
        }
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel, orderDO);
        orderDO.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderDO.setOrderPrice(orderModel.getOrderPrice().doubleValue());

        return orderDO;
    }
}
