package com.thizgroup.jpa.study.dto;

import org.springframework.beans.factory.annotation.Value;

/**
 * 用户信息 jpa 投影
 */
public interface UserProjection {

  public Long getId();

  public String getName();//姓名

  public Integer getAge();//年龄

  public String getMobile();//手机号

  public String getCountry();//国家

  public String getCity();//城市

  @Value("#{target.country+target.city}")
  public String getAddress();//地址

}
