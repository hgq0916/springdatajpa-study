package com.thizgroup.jpa.study;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.EntityManager;

@SpringBootApplication
//配置springDataJpa扫描Repository的包路径
@EntityScan(basePackages = {"com.thizgroup.jpa.study.model","com.thizgroup.jpa.study.dto","com.thizgroup.jpa.study.converter"})
@EnableJpaRepositories("com.thizgroup.jpa.study.dao")
@EnableJpaAuditing//启动jpa审计功能
public class JpaApplication {

  public static void main(String[] args) {
    SpringApplication.run(JpaApplication.class,args);
  }

  @Bean
  JPAQueryFactory jpaQueryFactory(EntityManager entityManager){
    return new JPAQueryFactory(entityManager);
  }

}
