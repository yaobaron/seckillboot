package com.cobra.seckillboot.error;

/**
 * @Author: Baron
 * @Description:
 * @Date: Created in 2018/12/28 18:47
 */
public interface CommonError {

    public int getErrCode();
    public String getErrMsg();
    public CommonError setErrMsg(String errMsg);
}
