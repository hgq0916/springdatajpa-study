package com.thizgroup.jpa.study.dto;

import com.thizgroup.jpa.study.enums.SexEnum;
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

  private String name;//姓名

  private Integer age;//年龄

  private String mobile;//手机号

  private String email;//邮箱

  private Date birthday;//生日

  private AddressDTO addressDTO;//地址

  private Date createDate;//创建时间

  private Date modifyDate;//修改时间

  private Date startTime;//开始时间

  private Date endTime;//结束时间

  private SexEnum sex;//性别
}
