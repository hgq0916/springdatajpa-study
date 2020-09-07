package com.thizgroup.jpa.study.enums;

import lombok.Getter;
/**
 * SexEnum
 *
 * @author hgq
 * @date 2016/10/31
 */
@Getter
public enum SexEnum {

  MAN(1,"男"),// 男
  WOMAN(2,"女"); // 女

  private Integer code;

  private String msg;

  private SexEnum(Integer code,String msg){
    this.code = code;
    this.msg = msg;
  }

}
