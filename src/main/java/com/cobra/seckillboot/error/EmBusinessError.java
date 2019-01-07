package com.cobra.seckillboot.error;

/**
 * @Author: Baron
 * @Description:
 * @Date: Created in 2018/12/28 18:50
 */
public enum EmBusinessError implements CommonError{

    //通用错误类型10001
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
    //通用错误类型10001
    UNKONWN_ERROR(10002,"未知错误"),

    //20000开头为用户信息相关错误定义
    USER_NOT_EXIST(20001,"用户不存在"),
    USER_LOGIN_FAIL(20002,"用户手机号或密码不正确"),
    USER_LOGIN_OUT(20003,"用户未登录！"),

    //30000开头为用户信息相关错误定义
    ITEM_NOT_EXIST(30001,"商品不存在"),

    //40000开头为交易新错误定义
    STOCK_NOT_ENOUGH(40001,"库存不足")
    ;

    private EmBusinessError(int errCode,String errMsg) {
        this.errCode=errCode;
        this.errMsg=errMsg;
    }

    private int errCode;
    private String errMsg;

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg=errMsg;
        return this;
    }
}
