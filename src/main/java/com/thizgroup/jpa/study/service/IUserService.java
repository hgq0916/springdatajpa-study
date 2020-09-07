package com.thizgroup.jpa.study.service;

import com.thizgroup.jpa.study.dto.PageRecord;
import com.thizgroup.jpa.study.dto.UserDTO;
import com.thizgroup.jpa.study.dto.UserProjection;
import com.thizgroup.jpa.study.model.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 用户服务
 */
public interface IUserService {

  /**
   * 查询用户分页信息
   * @param userDTO
   * @return
   */
  Page<User> findUserListByPage(UserDTO userDTO, Pageable pageable);

  /**
   * example单表动态条件分页查询
   */
  Page<User> findUserListByPage(User user,Pageable pageable);

  /**
   * 查询用户分页信息
   * @param userDTO
   * @return
   */
  PageRecord<UserDTO> findUserDtoListByPage(UserDTO userDTO, Pageable pageable);

  /**
   * 简单分页:查询指定城市的用户
   */
  Page<User> findUserListByCity(String city,Pageable pageable);


  /**
   * 单表动态查询条件+分页
   */
  Page<User> findUserListByCondition(String name ,String email,Pageable pageable);

  /**
   * 多表动态查询条件+分页
   */
  Page<UserDTO> findUserListByCondition2(UserDTO userDTO,Pageable pageable);

  /**
   * 根据id查询用户
   * @param id
   * @return
   */
  User findById(Long id);

  /**
   * 添加用户
   * @param userDTO
   * @return
   */
  User addUser(UserDTO userDTO);

  /***
   *
   * 更新用户
   * @param userDTO
   */
  void updateUser(UserDTO userDTO);

  /**
   * 根据登录名查询用户信息,登录名为邮箱或者手机号
   */
  UserDTO findUserByLoginName(String loginName);

  /**
   * 根据地址和年龄查询用户列表
   * @param addressId 地址id
   * @param age 年龄
   * @return
   */
  List<UserDTO> findUserByAddressIdAndAge(Long addressId,int age);

  /**
   * 使用jpa投影查询用户信息
   * @param id
   * @return
   */
  UserProjection findUserInfoById(Long id);

  /**
   * 根据用户名查询用户信息
   * @param name
   * @return
   */
  User findUserByName(String name);

  List<User> findUserByCityAndAge(String city,int age);


  List<User> findUserByNameLike(String name);

  void updateUserNameById(String name,Long id);

  void deleteById(Long id);

}
