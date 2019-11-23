package com.thizgroup.jpa.study.model;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass//这个注解的意思是这个类jpa不会为它创建数据库表，
//jpa会将这个类的所有字段映射到它的子类的数据库表中
@Data
@EntityListeners(AuditingEntityListener.class)//对实体属性变化的跟踪，它提供了保存前，保存后，更新前，
// 更新后，删除前，删除后等状态，就像是拦截器一样，你可以在拦截方法里重写你的个性化逻辑。
public abstract class BaseEntity   implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long id;

  @CreatedDate//自动填充创建时间
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "create_date",columnDefinition = "timestamp")
  protected Date createDate;//创建时间

  @LastModifiedDate//自动填充修改时间
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "modify_date",columnDefinition = "timestamp")
  protected Date modifyDate;//修改时间

  @CreatedBy//自动填充创建人
  @Column(name = "created_by",columnDefinition = "bigint")
  protected Long createdBy;//创建人

  @LastModifiedBy//自动填充修改人
  @Column(name = "last_modify_by",columnDefinition = "bigint")
  protected Long lastModifyBy;//修改人

}
