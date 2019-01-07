package com.cobra.seckillboot.service;

import com.cobra.seckillboot.service.model.PromoModel;

/**
 * @Author: Baron
 * @Description:
 * @Date: Created in 2019/1/2 22:02
 */
public interface PromoService {

    PromoModel createPromo(PromoModel promoModel);

    PromoModel getPromoByItemId(Integer itemId);
}
