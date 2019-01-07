package com.cobra.seckillboot;

import com.cobra.seckillboot.dao.UserDOMapper;
import com.cobra.seckillboot.dataobject.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@SpringBootApplication
//@SpringBootApplication(scanBasePackages = "com.cobra.seckillboot")
@MapperScan("com.cobra.seckillboot.dao")
public class SeckillbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckillbootApplication.class, args);
    }

}

