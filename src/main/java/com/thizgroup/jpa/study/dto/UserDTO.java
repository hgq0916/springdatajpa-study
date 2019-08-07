package com.thizgroup.jpa.study.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data//使用lombok生成getter、setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

  private Long id;

  private String name;

  private Integer age;

  private String mobile;

  private String email;

  private Date birthday;

  private AddressDTO addressDTO;

  private Date createDate;

  private Date modifyDate;

  private Date startTime;

  private Date endTime;

}
