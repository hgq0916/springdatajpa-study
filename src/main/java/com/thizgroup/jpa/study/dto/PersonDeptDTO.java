package com.thizgroup.jpa.study.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonDeptDTO {
    //用户基础信息
    private String username;    //用户名
    private String nickname;    //昵称
    private String birthday;    //用户生日
    //用户的部门信息
    private String deptName;    //用户所属部门
    private String deptBirth;   //部门创建的时间
}