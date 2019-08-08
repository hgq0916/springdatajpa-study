package com.thizgroup.jpa.study.service;

import com.thizgroup.jpa.study.dto.PageRecord;
import com.thizgroup.jpa.study.dto.UserDTO;
import com.thizgroup.jpa.study.model.User;
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
   * 查询用户分页信息
   * @param userDTO
   * @return
   */
  PageRecord<UserDTO> findUserDTOListByPage(UserDTO userDTO, Pageable pageable);

  /**
   * 简单分页:查询指定城市的用户
   */
  Page<User> findUserListByCity(String city,Pageable pageable);

  /**
   * 根据id查询用户
   * @param id
   * @return
   */
  User findById(Long id);

}
