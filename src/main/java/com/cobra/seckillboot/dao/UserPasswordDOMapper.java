package com.cobra.seckillboot.dao;

import com.cobra.seckillboot.dataobject.UserPasswordDO;
import org.springframework.stereotype.Component;

@Component(value = "userPasswordDOMapper")//可以不加，但是用@Autowired时，对象下会有下划红线报错
public interface UserPasswordDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Thu Dec 27 23:46:51 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Thu Dec 27 23:46:51 CST 2018
     */
    int insert(UserPasswordDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Thu Dec 27 23:46:51 CST 2018
     */
    int insertSelective(UserPasswordDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Thu Dec 27 23:46:51 CST 2018
     */
    UserPasswordDO selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Thu Dec 27 23:46:51 CST 2018
     */
    int updateByPrimaryKeySelective(UserPasswordDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_password
     *
     * @mbg.generated Thu Dec 27 23:46:51 CST 2018
     */
    int updateByPrimaryKey(UserPasswordDO record);

    /**
     * 通过用户id获取对象，手动建方法
     */
    UserPasswordDO selectByUserId(Integer userId);
}