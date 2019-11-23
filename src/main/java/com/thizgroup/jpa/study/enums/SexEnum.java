package com.thizgroup.jpa.study.enums;

import lombok.Getter;

@Getter
public enum SexEnum {

  MAN(1,"男"),
  WOMAN(2,"女");

  private Integer code;

  private String msg;

  private SexEnum(Integer code,String msg){
    this.code = code;
    this.msg = msg;
  }

}
