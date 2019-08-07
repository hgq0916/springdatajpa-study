package com.thizgroup.jpa.study.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tb_user")
@Data//使用lombok生成getter、setter
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name",columnDefinition = "varchar(64)")
  private String name;

  @Column(name = "mobile",columnDefinition = "varchar(64)")
  private String mobile;

  @Column(name = "email",columnDefinition = "varchar(64)")
  private String email;

  @Column(name = "age",columnDefinition = "smallint(64)")
  private Integer age;

  @Column(name = "birthday",columnDefinition = "timestamp")
  private Date birthday;

  //地址
  @Column(name = "address_id",columnDefinition = "bigint(20)")
  private Long addressId;

  @Column(name = "create_date",columnDefinition = "timestamp")
  private Date createDate;

  @Column(name = "modify_date",columnDefinition = "timestamp")
  private Date modifyDate;

}
