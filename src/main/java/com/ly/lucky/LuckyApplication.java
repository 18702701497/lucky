package com.ly.lucky;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ly.lucky.dao")//dao包下所有的接口都会变成实现类
public class LuckyApplication {

	public static void main(String[] args) {
		SpringApplication.run(LuckyApplication.class, args);
	}

}
