package com.thizgroup.jpa.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//配置springDataJpa扫描Repository的包路径
@EnableJpaRepositories("com.thizgroup.jpa.study.dao")
public class JpaApplication {

  public static void main(String[] args) {
    SpringApplication.run(JpaApplication.class,args);
  }

}
