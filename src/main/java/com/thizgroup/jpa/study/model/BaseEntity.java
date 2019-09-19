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

@MappedSuperclass
@Data
@EntityListeners(AuditingEntityListener.class)
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
