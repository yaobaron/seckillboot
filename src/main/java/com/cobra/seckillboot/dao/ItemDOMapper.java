package com.cobra.seckillboot.dao;

import com.cobra.seckillboot.dataobject.ItemDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("itemDOMapper")
public interface ItemDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Jan 01 20:24:16 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Jan 01 20:24:16 CST 2019
     */
    int insert(ItemDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Jan 01 20:24:16 CST 2019
     */
    int insertSelective(ItemDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Jan 01 20:24:16 CST 2019
     */
    ItemDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Jan 01 20:24:16 CST 2019
     */
    int updateByPrimaryKeySelective(ItemDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table item
     *
     * @mbg.generated Tue Jan 01 20:24:16 CST 2019
     */
    int updateByPrimaryKey(ItemDO record);

    /**
     * 增加销量 (参数需要加@Param("amount"))
     * @param id
     * @param amount
     */
    void creaseSales(@Param("id")Integer id,@Param("amount")Integer amount);

    /**
     * 查询所有商品
     * @return
     */
    List<ItemDO> listItem();
}