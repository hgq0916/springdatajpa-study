package com.thizgroup.jpa.study.dto;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo2 {

  private Long id;

  private String name;//姓名

  private int age;//年龄

  private Date birthday;//生日

  private String mobile;//电话

  private String email;//邮箱

  private String country;//国家

  private String province;//省份

  private String city;//城市

  private Date createTime;//创建时间

}
