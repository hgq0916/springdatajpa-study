package com.thizgroup.jpa.study.service;

import com.thizgroup.jpa.study.JpaApplication;
import com.thizgroup.jpa.study.dto.AddressDTO;
import com.thizgroup.jpa.study.dto.PageRecord;
import com.thizgroup.jpa.study.dto.UserDTO;
import com.thizgroup.jpa.study.model.User;
import com.thizgroup.jpa.study.utils.DateUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes={JpaApplication.class})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
public class UserServiceImplTest {

  @Autowired
  private IUserService userService;

  private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  @Test
  public void findByIdTest(){
    User user = userService.findById(1L);
    System.out.println(user);
  }

  @Test
  public void findUserListByPageTest() throws Exception{

    UserDTO userDTO = new UserDTO();
    //userDTO.setName("张");
    //userDTO.setAge(50);
    //userDTO.setStartTime(dateFormat.parse("2001-09-16 08:00:00"));
    //userDTO.setEndTime(dateFormat.parse("2008-09-15 08:00:00"));
    userDTO.setMobile("158989");
    userDTO.setEmail("hu");
    //注意：jpa的分页是从0开始的
    Page<User> pageList = userService.findUserListByPage(userDTO, PageRequest.of(0, 15));
    System.out.println("分页信息：");
    System.out.println("总记录数："+pageList.getTotalElements()+",总页数："+pageList.getTotalPages());
    System.out.println("页码："+(pageList.getNumber()+1)+",每页条数："+pageList.getSize());
    List<User> content = pageList.getContent();
    content = null == content? new ArrayList<>() : content;
    content.forEach(user->System.out.println(user));
  }

  @Test
  public void findUserListByPage3Test() throws Exception{

    User u = new User();
    u.setName("张");
    u.setEmail("@qq.com");
    //注意：jpa的分页是从0开始的
    Page<User> pageList = userService.findUserListByPage(u, PageRequest.of(0, 15));
    System.out.println("分页信息：");
    System.out.println("总记录数："+pageList.getTotalElements()+",总页数："+pageList.getTotalPages());
    System.out.println("页码："+(pageList.getNumber()+1)+",每页条数："+pageList.getSize());
    List<User> content = pageList.getContent();
    content = null == content? new ArrayList<>() : content;
    content.forEach(user->System.out.println(user));
  }

  @Test
  public void findUserListByPage2Test() throws Exception {
    UserDTO userDTO = UserDTO.builder()
        .name("诸")
        //.age(35)
        //.startTime(dateFormat.parse("2001-09-16 08:00:00"))
        //.endTime(dateFormat.parse("2008-09-16 07:00:00"))
        .addressDTO(AddressDTO.builder()
            .city("武汉")
            .build())
        .build();
    //注意：jpa的分页是从0开始的
    PageRecord<UserDTO> userListByPage2 = userService
        .findUserDTOListByPage(userDTO, PageRequest.of(0, 15));

    System.out.println("分页信息：");
    System.out.println("总记录数："+userListByPage2.getTotalCount()+",总页数："+userListByPage2.getTotalPages());
    System.out.println("页码："+(userListByPage2.getPageNumber()+1)+",每页条数："+userListByPage2.getPageSize());
    List<UserDTO> content = userListByPage2.getData();
    content = null == content? new ArrayList<>() : content;
    content.forEach(user->System.out.println(user));

  }

  @Test
  public void findUserListByPage2(){
    String city = "武汉";
    //注意：jpa的分页是从0开始的
    Page<User> pageList = userService.findUserListByCity(city, PageRequest.of(0, 15));
    System.out.println("分页信息：");
    System.out.println("总记录数："+pageList.getTotalElements()+",总页数："+pageList.getTotalPages());
    System.out.println("页码："+(pageList.getNumber()+1)+",每页条数："+pageList.getSize());
    List<User> content = pageList.getContent();
    content = null == content? new ArrayList<>() : content;
    content.forEach(user->System.out.println(user));
  }

  @Test
  public void findUserListByCondition(){
    String name = null;
    String email = "@qq.com";
    //注意：jpa的分页是从0开始的
    Page<User> pageList = userService.findUserListByCondition(name,email, PageRequest.of(0, 15));
    System.out.println("分页信息：");
    System.out.println("总记录数："+pageList.getTotalElements()+",总页数："+pageList.getTotalPages());
    System.out.println("页码："+(pageList.getNumber()+1)+",每页条数："+pageList.getSize());
    List<User> content = pageList.getContent();
    content = null == content? new ArrayList<>() : content;
    content.forEach(user->System.out.println(user));
  }

  @Test
  public void findUserListByCondition2(){
    String name = null;
    String city = "武汉";
    UserDTO userDTO = new UserDTO();
    userDTO.setName(name);
    AddressDTO addressDTO = new AddressDTO();
    addressDTO.setCity(city);
    userDTO.setAddressDTO(addressDTO);
    //注意：jpa的分页是从0开始的
    Page<UserDTO> pageList = userService.findUserListByCondition2(userDTO, PageRequest.of(0, 1));
    System.out.println("分页信息：");
    System.out.println("总记录数："+pageList.getTotalElements()+",总页数："+pageList.getTotalPages());
    System.out.println("页码："+(pageList.getNumber()+1)+",每页条数："+pageList.getSize());
    List<UserDTO> content = pageList.getContent();
    content = null == content? new ArrayList<>() : content;
    content.forEach(user->System.out.println(user));
  }

  @Test
  public void addUserTest(){
    UserDTO userDTO = UserDTO.builder()
        .name("李元芳")
        .email("yuanfang@qq.com")
        .birthday(DateUtils.parse("1998-09-08 12:14:15", "yyyy-MM-dd HH:mm:ss"))
        .age(30)
        .mobile("18755634343")
        .build();
    userService.addUser(userDTO);
  }

  @Test
  public void updateUserTest(){
    UserDTO userDTO = UserDTO.builder()
        .id(1L)
        .name("张三丰")
        .email("feng@qq.com")
        .birthday(DateUtils.parse("1998-09-08 12:14:15", "yyyy-MM-dd HH:mm:ss"))
        .age(30)
        .mobile("18755634343")
        .build();
    userService.updateUser(userDTO);
  }

}
