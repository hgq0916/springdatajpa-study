package com.thizgroup.jpa.study.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data//使用lombok生成getter、setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

  private Long id;

  private String country;//国家

  private String province;//省份

  private String city;//城市

}
