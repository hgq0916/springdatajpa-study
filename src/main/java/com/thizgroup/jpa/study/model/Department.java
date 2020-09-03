package com.thizgroup.jpa.study.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "t_department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer deptId;    //部门id
    private String deptName;   //部门名称
    private Date createDate;   //创建时间
}