package com.thizgroup.jpa.study.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name="tb_address")
@Data//使用lombok生成getter、setter
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "country",columnDefinition = "varchar(64)")
  private String country;

  @Column(name = "province",columnDefinition = "varchar(64)")
  private String province;

  @Column(name = "city",columnDefinition = "varchar(64)")
  private String city;

}
