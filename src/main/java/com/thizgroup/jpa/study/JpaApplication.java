package com.thizgroup.jpa.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//配置springDataJpa扫描Repository的包路径
@EntityScan(basePackages = {"com.thizgroup.jpa.study.model","com.thizgroup.jpa.study.dto"})
@EnableJpaRepositories("com.thizgroup.jpa.study.dao")
@EnableJpaAuditing
public class JpaApplication {

  public static void main(String[] args) {
    SpringApplication.run(JpaApplication.class,args);
  }

}
