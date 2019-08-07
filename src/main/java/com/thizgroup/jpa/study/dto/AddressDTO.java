package com.thizgroup.jpa.study.dto;

import lombok.Builder;
import lombok.Data;

@Data//使用lombok生成getter、setter
@Builder
public class AddressDTO {

  private Long id;

  private String country;

  private String province;

  private String city;

}
