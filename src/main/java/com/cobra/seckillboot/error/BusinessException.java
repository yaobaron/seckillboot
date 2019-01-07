package com.cobra.seckillboot.error;

/**
 * @Author: Baron
 * @Description: 包装期业务异常类实现
 * @Date: Created in 2018/12/28 19:01
 */
public class BusinessException extends Exception implements CommonError {

    private CommonError commonError;

    /**
     * 直接接收EmBusinessError的传参用于构造业务异常
     * @param commonError
     */
    public BusinessException (CommonError commonError) {
        super();
        this.commonError=commonError;
    }

    //接收自定义errMsg的方式构造业务异常
    public BusinessException(CommonError commonError,String errMsg) {
        super();
        this.commonError=commonError;
        this.commonError.setErrMsg(errMsg);
    }

    @Override
    public int getErrCode() {
        return this.commonError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return this.commonError.getErrMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.commonError.setErrMsg(errMsg);
        return this;
    }
}
