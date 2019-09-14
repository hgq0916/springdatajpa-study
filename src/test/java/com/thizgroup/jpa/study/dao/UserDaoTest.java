package com.thizgroup.jpa.study.dao;

import com.thizgroup.jpa.study.JpaApplication;
import com.thizgroup.jpa.study.dto.PageRecord;
import com.thizgroup.jpa.study.dto.UserInfo;
import com.thizgroup.jpa.study.dto.UserInfo2;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes={JpaApplication.class})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
public class UserDaoTest {

  @Autowired
  private UserDao userDao;

  @Test
  public void findUserListByPage2Test(){
    PageRecord<UserInfo> pageRecord = userDao
        .findUserListByPage2(null, PageRequest.of(0, 15));

    System.out.println("分页信息：");
    System.out.println("总记录数："+pageRecord.getTotalCount()+",总页数："+pageRecord.getTotalPages());
    System.out.println("页码："+(pageRecord.getPageNumber()+1)+",每页条数："+pageRecord.getPageSize());
    List<UserInfo> content = pageRecord.getData();
    content = null == content? new ArrayList<>() : content;
    content.forEach(user->System.out.println(user));

  }

  @Test
  public void findUserListByPage3Test(){
    PageRecord<UserInfo2> pageRecord = userDao
        .findUserListByPage3(null, PageRequest.of(0, 15));

    System.out.println("分页信息：");
    System.out.println("总记录数："+pageRecord.getTotalCount()+",总页数："+pageRecord.getTotalPages());
    System.out.println("页码："+(pageRecord.getPageNumber()+1)+",每页条数："+pageRecord.getPageSize());
    List<UserInfo2> content = pageRecord.getData();
    content = null == content? new ArrayList<>() : content;
    content.forEach(user->System.out.println(user));
  }
}
