package com.thizgroup.jpa.study.dao;

import com.thizgroup.jpa.study.dto.UserDTO;
import com.thizgroup.jpa.study.dto.UserProjection;
import com.thizgroup.jpa.study.model.User;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {

  /**
   * jpa原生sql查询返回实体类对象
   * @param loginName 登录名
   * @return
   */
  @Query(nativeQuery = true,value = "select * from tb_user where (email = :loginName or name = :loginName)")
  User findUserByLoginName(@Param("loginName") String loginName);

  /**
   * 分页查询1
   */
  @Query(nativeQuery = true,value = " select * from tb_user "
      + " where address_id = (select id from tb_address where city = ?1 ) ",
   countQuery = " select count(*) from tb_user "
       + " where address_id = (select id from tb_address where city = ?1 )  ")
  Page<User> findUserListByCity(String city,Pageable pageable);

  /**
   * 单表动态查询条件+分页
   */
  @Query(nativeQuery = true,value = "select * from tb_user where (?1 is null or name like %?1%) "
      + " and (?2 is null or email like %?2%) ",
      countQuery = "select count(*) from tb_user where (?1 is null or name like %?1%) "
          + " and (?2 is null or email like %?2%) ")
  Page<User> findUserListByCondition(String name ,String email,Pageable pageable);

  /**
   * 多表动态查询条件+分页
   */
  @Query(nativeQuery = true,value = "select u.id,u.name,u.age,u.birthday,u.email,a.country,a.city from tb_user u left join tb_address a "
      + "  on u.address_id=a.id  where (?1 is null or u.name like %?1%) "
      + " and (?2 is null or a.city = ?2) order by u.create_date ",
      countQuery = "select count(*) from tb_user u left join tb_address a "
          + "  on u.address_id=a.id  where (?1 is null or u.name like %?1%) "
          + " and (?2 is null or a.city = ?2) ")
  Page<Map<String,Object>> findUserListByCondition2(String name,String city,Pageable pageable);

  /**
   * 使用jpql语句查询，jpa根据方法名生成sql语句
   * @param addressId
   * @param age
   * @return
   */
  List<User> findByAddressIdAndAge(Long addressId, int age);

  @Query("select u.id as id,u.name as name,u.age as age,u.mobile as mobile,a.country as country,"
      + " a.city as city from User u "
      + " left join Address a on u.addressId=a.id where u.id =:id")
  UserProjection findUserInfoById(@Param("id") Long id);
}
