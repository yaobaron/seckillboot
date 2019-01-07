package com.cobra.seckillboot.service.Impl;

import com.cobra.seckillboot.dao.PromoDOMapper;
import com.cobra.seckillboot.dataobject.PromoDO;
import com.cobra.seckillboot.service.PromoService;
import com.cobra.seckillboot.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @Author: Baron
 * @Description:
 * @Date: Created in 2019/1/2 22:09
 */
@Service
public class PromoServiceImpl implements PromoService {

    @Autowired
    private PromoDOMapper promoDOMapper;

    @Override
    public PromoModel createPromo(PromoModel promoModel) {
        return null;
    }

    @Override
    public PromoModel getPromoByItemId(Integer itemId) {
        //获取对应商品的秒杀活动信息
        PromoDO promoDO = promoDOMapper.selectByItemId(itemId);

        PromoModel promoModel = this.convertFromDataObject(promoDO);

        if (promoModel==null) {
            return null;
        }

        //判断当前时间是否秒杀活动即将开始或正在进行
        if (promoModel.getStartDate().isAfterNow()) {
            promoModel.setStatus(1);
        }else if(promoModel.getEndDate().isBeforeNow()) {
            promoModel.setStatus(3);
        }else {
            promoModel.setStatus(2);
        }
        return promoModel;
    }

    private PromoModel convertFromDataObject(PromoDO promoDO) {

        if (promoDO==null) {
            return null;
        }
        PromoModel promoModel = new PromoModel();

        BeanUtils.copyProperties(promoDO,promoModel);
        promoModel.setPromoPrice(new BigDecimal(promoDO.getPromoItemPrice()));
        promoModel.setStartDate(new DateTime(promoDO.getStartDate()));
        promoModel.setEndDate(new DateTime(promoDO.getEndDate()));
        return promoModel;
    }

}
