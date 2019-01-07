package com.cobra.seckillboot.service;

import com.cobra.seckillboot.error.BusinessException;
import com.cobra.seckillboot.service.model.ItemModel;

import java.util.List;

/**
 * @Author: Baron
 * @Description:
 * @Date: Created in 2019/1/1 19:20
 */
public interface ItemService {

    //创建商品
    ItemModel createItem(ItemModel itemModel) throws BusinessException;

    //商品列表浏览
    List<ItemModel> listItem();

    //商品详情浏览
    ItemModel getItemById(Integer id);

    //库存扣减
    boolean decreaseStock(Integer integer, Integer amount) throws BusinessException;

    //增加销量
    void creaseSales(Integer itemId, Integer amount) throws BusinessException;

}