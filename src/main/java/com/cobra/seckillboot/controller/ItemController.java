package com.cobra.seckillboot.controller;

import com.cobra.seckillboot.controller.viewobject.ItemVO;
import com.cobra.seckillboot.error.BusinessException;
import com.cobra.seckillboot.error.EmBusinessError;
import com.cobra.seckillboot.response.CommonReturnType;
import com.cobra.seckillboot.service.ItemService;
import com.cobra.seckillboot.service.model.ItemModel;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Baron
 * @Description:
 * @Date: Created in 2019/1/1 21:21
 */
@RestController
@RequestMapping("/items")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class ItemController extends BaseController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/creation",method = RequestMethod.POST,consumes = CONTENT_TYPE_FORMED)
    public CommonReturnType createItem(@RequestParam("title") String title,
                                       @RequestParam("price") BigDecimal price,
                                       @RequestParam("stock") Integer stock,
                                       @RequestParam("description") String description,
                                       @RequestParam("imgUrl") String imgUrl
                                       ) throws BusinessException {
        //封装service请求用来创建商品
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setDescription(description);
        itemModel.setImgUrl(imgUrl);
        ItemModel itemModelForReturn = itemService.createItem(itemModel);

        ItemVO itemVO = this.convertVOFromModel(itemModelForReturn);
        return CommonReturnType.create(itemVO);
    }

    /**
     * 获取商品详情页浏览
     * @param id
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public CommonReturnType getItemById(@PathVariable("id")Integer id) {
        ItemModel itemModel = this.itemService.getItemById(id);
        ItemVO itemVO = this.convertVOFromModel(itemModel);
        return CommonReturnType.create(itemVO);
    }

    /**
     * 商品列表页面浏览
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public CommonReturnType getItemList() {
        List<ItemModel> itemModels = this.itemService.listItem();
        //使用stream api将list内的itemModel转化为itemVO
        List<ItemVO> itemVOS =  itemModels.stream().map(itemModel -> {
            ItemVO itemVO = this.convertVOFromModel(itemModel);
            return itemVO;
        }).collect(Collectors.toList());
        return CommonReturnType.create(itemVOS);
    }



    private ItemVO convertVOFromModel(ItemModel itemModel){
        if (itemModel==null) {
            return null;
        }
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel,itemVO);
        if (itemModel.getPromoModel() !=null) {
            //正在进行或即将进行的秒杀活动
            itemVO.setPromoStatus(itemModel.getPromoModel().getStatus());
            itemVO.setPromoId(itemModel.getPromoModel().getId());
            itemVO.setPromoStartDate(itemModel.getPromoModel().getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
            itemVO.setPromoPrice(itemModel.getPromoModel().getPromoPrice());
        }else {
            itemVO.setPromoStatus(0);
        }
        return itemVO;
    }

}
