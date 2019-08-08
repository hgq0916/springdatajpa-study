package com.thizgroup.jpa.study.dao;

import com.thizgroup.jpa.study.model.User;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {

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

}
