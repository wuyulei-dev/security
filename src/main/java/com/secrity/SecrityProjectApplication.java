package com.secrity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.secrity.mapper")
@SpringBootApplication
public class SecrityProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecrityProjectApplication.class, args);
	}

}
