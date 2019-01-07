package com.cobra.seckillboot.service.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: Baron
 * @Description: 组合数据传给controller
 * @Date: Created in 2018/12/28 15:33
 */
public class UserModel {

    /**
     * 唯一标识
     */
    private Integer id;

    /**
     * 姓名
     */
    @NotBlank(message = "用户名不能为空")
    private String name;

    /**
     * 性别
     */
    @NotNull(message = "性别不能不填")
    private Byte gender;

    /**
     * 年龄
     */
    @NotNull(message = "年龄不能不填")
    @Min(value = 0,message = "年龄不能小于0")
    @Max(value = 150,message = "年龄不能大于150")
    private Integer age;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空111111111")
    private String telphone;

    /**
     * 注册方式
     */
    private String registarMode;

    /**
     * 第三方id
     */
    private String thirdPartyId;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String encrptPassword;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getRegistarMode() {
        return registarMode;
    }

    public void setRegistarMode(String registarMode) {
        this.registarMode = registarMode;
    }

    public String getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    public String getEncrptPassword() {
        return encrptPassword;
    }

    public void setEncrptPassword(String encrptPassword) {
        this.encrptPassword = encrptPassword;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", telphone='" + telphone + '\'' +
                ", registarMode='" + registarMode + '\'' +
                ", thirdPartyId='" + thirdPartyId + '\'' +
                ", encrptPassword='" + encrptPassword + '\'' +
                '}';
    }
}
