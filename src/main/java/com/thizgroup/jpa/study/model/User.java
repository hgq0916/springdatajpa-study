package com.thizgroup.jpa.study.model;

import com.thizgroup.jpa.study.converter.SexEnumAttributeConverter;
import com.thizgroup.jpa.study.enums.SexEnum;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_user")
@Data//使用lombok生成getter、setter
@NoArgsConstructor//生成无参构造方法
public class User extends BaseEntity{

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

  //@Convert(converter = SexEnumAttributeConverter.class)
  @Column(name="sex",columnDefinition = "int(1)")
  private SexEnum sex;

  @Builder(toBuilder = true)
  public User(Long id,String name, String mobile, String email, Integer age, Date birthday,
      Long addressId,SexEnum sex) {
    this.id = id;
    this.name = name;
    this.mobile = mobile;
    this.email = email;
    this.age = age;
    this.birthday = birthday;
    this.addressId = addressId;
    this.sex = sex;
  }
}
