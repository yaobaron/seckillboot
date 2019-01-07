package com.cobra.seckillboot.controller;

import com.alibaba.druid.util.StringUtils;
import com.cobra.seckillboot.controller.viewobject.UserVO;
import com.cobra.seckillboot.error.BusinessException;
import com.cobra.seckillboot.error.EmBusinessError;
import com.cobra.seckillboot.response.CommonReturnType;
import com.cobra.seckillboot.service.UserService;
import com.cobra.seckillboot.service.model.UserModel;
import org.apache.tomcat.util.security.MD5Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Author: Baron
 * @Description:
 * @Date: Created in 2018/12/28 14:53
 */
@RestController
@RequestMapping("/users")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String SALT="FDLK4@$%&^*((#$";

    @RequestMapping(value = "/login",method = RequestMethod.POST,consumes = CONTENT_TYPE_FORMED)
    public CommonReturnType login(@RequestParam("telphone") String telphone,
                                  @RequestParam("password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //入参校验
        if(StringUtils.isEmpty(telphone)||StringUtils.isEmpty(password)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        //用户登录服务，用来校验用户登录是合法
        UserModel userModel = userService.validateLogin(telphone,encodeByMd5(password));

        //将登陆凭证加入到用户登陆成功的session内
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);

        return CommonReturnType.create(null);
    }

    /**
     * 用户注册接口
     * @param telphone
     * @param otpCode
     * @param name
     * @param gender
     * @param age
     * @param password
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST,consumes = CONTENT_TYPE_FORMED)
    public CommonReturnType register(@RequestParam("telphone") String telphone,
                                    @RequestParam("otpCode") String otpCode,
                                    @RequestParam("name") String name,
                                    @RequestParam("gender") Integer gender,
                                    @RequestParam("age") Integer age,
                                    @RequestParam("password") String password
                                    ) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //验证手机号和otpcode符合
        String inSessionOtpCode = (String) this.httpServletRequest.getSession().getAttribute(telphone);;
        if (!StringUtils.equals(otpCode,inSessionOtpCode)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"短信验证码不符");
        }
        //用户注册流程
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setGender(new Byte(String.valueOf(gender.intValue())));
        userModel.setAge(age);
        userModel.setTelphone(telphone);
        userModel.setRegistarMode("byphone");
        userModel.setEncrptPassword(this.encodeByMd5(password));
        userService.register(userModel);
        return CommonReturnType.create(null);
    }

    /**
     *
     * @param str
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public String encodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确认计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Decoder = new BASE64Encoder();
        //加密字符串，加上盐值
        String newstr = base64Decoder.encode(md5.digest((str+SALT).getBytes("UTF-8")));
        return newstr;
    }

    /**
     * 用户获取otp短信接口
     * @param telphone
     * @return
     */
    @RequestMapping(value = "/getotp",method = RequestMethod.POST,consumes = CONTENT_TYPE_FORMED)
    public CommonReturnType getOtp(@RequestParam("telphone") String telphone) {
        //需要按照一定的规则生成OTP验证码
        Random random = new Random();
        int randomInt = random.nextInt(9999);
        String otpCode = String.valueOf(randomInt);
        StringBuffer str = new StringBuffer();
        for (int i = 0; i <4-otpCode.length(); i++) {
            str.append("0");
        }
        str.append(otpCode);
        //将OTP验证码同对应用户的手机号关联,暂时先使用httpsessionb绑定telephone和otpCode，实际生成中使用redis存放key-value
        httpServletRequest.getSession().setAttribute(telphone,str.toString());

        //将OTP验证码通过短信发给用户（省略）,这里用打印输出代替,实际开发也不能打印到日志里
        logger.info("telephone:{},otpCode:{}",telphone,str);
        return CommonReturnType.create(null);
    }

    /**
     * 通过用户id获取用户
     * @param id
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public CommonReturnType getUserById(@PathVariable("id") Integer id) throws BusinessException {

        UserModel userModel = userService.getUserById(id);
        if(userModel==null) {
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        logger.info("userModel:{}",userModel);
        //将核心领域模型Model用户对象转化为供UI使用的ViewObject
        UserVO userVO = convertFromModel(userModel);
        //返回通用对象
        return CommonReturnType.create(userVO);
    }

    /**
     * 将userModel转化为userVO供返回给前端
     * @param userModel
     * @return
     */
    private UserVO convertFromModel (UserModel userModel) {
        if (userModel==null) {
            return null;
        }
        UserVO userVO  = new UserVO();
        BeanUtils.copyProperties(userModel,userVO);
        return  userVO;
    }

}
