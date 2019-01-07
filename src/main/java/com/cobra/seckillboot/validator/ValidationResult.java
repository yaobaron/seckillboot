package com.cobra.seckillboot.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Baron
 * @Description:
 * @Date: Created in 2018/12/30 19:49
 */
public class ValidationResult {
    /**
     * 校验结果是否有错，需要指定开始默认值，否则get方法时报空指针
     */
    private boolean hasErrors=false;

    /**
     * 存放错误信息的map,需要new，否则get方法时得到的map为空，报空指针
     */
    private Map<String,String> errorMsgMap = new HashMap<>();

    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public Map<String, String> getErrorMsgMap() {
        return errorMsgMap;
    }

    public void setErrorMsgMap(Map<String, String> errorMsgMap) {
        this.errorMsgMap = errorMsgMap;
    }

    //实现通过的通过格式化字符串信息获取错误结果的msg方法
    public String getErrMsg() {
       return StringUtils.join(errorMsgMap.values().toArray(),",");
    }
}
