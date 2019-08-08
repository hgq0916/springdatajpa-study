package com.thizgroup.jpa.study.dao;

import com.thizgroup.jpa.study.model.User;
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

}
