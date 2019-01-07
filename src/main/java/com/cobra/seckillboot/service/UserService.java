package com.cobra.seckillboot.service;

import com.cobra.seckillboot.error.BusinessException;
import com.cobra.seckillboot.service.model.UserModel;

/**
 * @Author: Baron
 * @Description:
 * @Date: Created in 2018/12/28 15:19
 */
public interface UserService {

    /**
     * 通过用户id获取用户信息
     * @param id
     */
    UserModel getUserById(Integer id);

    /**
     * 完成客户注册
     * @param userModel
     */
    void register(UserModel userModel) throws BusinessException;

    /**
     * 用户登录校验
     * @param telphone 手机号
     * @param encrptPassword 加密过的密码
     */
    UserModel validateLogin (String telphone, String encrptPassword) throws BusinessException;
}
