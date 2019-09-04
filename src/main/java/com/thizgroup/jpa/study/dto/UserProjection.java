package com.thizgroup.jpa.study.dto;

/**
 * 用户信息 jpa 投影
 */
public interface UserProjection {

  public Long getId();

  public String getName();

  public Integer getAge();

  public String getMobile();

  public String getCountry();

  public String getCity();

}
