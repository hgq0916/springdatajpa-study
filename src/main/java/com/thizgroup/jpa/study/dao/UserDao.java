package com.thizgroup.jpa.study.dao;

import com.thizgroup.jpa.study.dto.AddressDTO;
import com.thizgroup.jpa.study.dto.PageBean;
import com.thizgroup.jpa.study.dto.PageRecord;
import com.thizgroup.jpa.study.dto.UserDTO;
import com.thizgroup.jpa.study.dto.UserInfo;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * 用户服务
 */
@Repository
public class UserDao {

  @Autowired
  private EntityManager entityManager;

  //使用entityManager实现多表联合带条件带分页查询及排序
  public PageRecord<UserDTO> findUserListByPage(UserDTO userDTO, Pageable pageable){

    List<Object> args = new ArrayList<>();//用于封装参数

    StringBuffer sql = new StringBuffer();
    sql.append(
        " select u.id,u.name,u.age,u.birthday,u.mobile,u.email,a.country,a.province,a.city,"
            + " u.create_date "
            + " from tb_user u "
        + " left join  tb_address a on u.address_id = a.id "
            + " where 1=1 "
    );

    if(userDTO  != null){
      if(StringUtils.isNotBlank(userDTO.getName())){
        //模糊查询
        sql.append(" and u.name like ? ");
        args.add("%"+userDTO.getName()+"%");
      }
      if(null != userDTO.getAge()){
        //精确查询
        sql.append(" and u.age = ? ");
        args.add(userDTO.getAge());
      }
      //求生日在某个时间段范围内的用户
      if(null != userDTO.getStartTime()){
        //大于等于
        sql.append(" and u.birthday >= ? ");
        args.add(userDTO.getStartTime());
      }
      if(null != userDTO.getEndTime()){
        //小于等于
        sql.append(" and u.birthday <= ? ");
        args.add(userDTO.getEndTime());
      }

     if(userDTO.getAddressDTO() != null) {
       AddressDTO addressDTO = userDTO.getAddressDTO();
       //查询某个城市的用户
       if(StringUtils.isNotBlank(addressDTO.getCity())) {
         sql.append(" and a.city = ? ");
         args.add(addressDTO.getCity());
       }
     }

    }

    //按照创建时间倒序排序
    sql.append(" order by u.create_date desc ");

    //创建query对象
    Query query = entityManager.createNativeQuery(sql.toString());

    //设置查询参数
    if(args.size()>0){
      for(int i=0;i<args.size();i++){
        //注意：jpa的setParameter是从1开始的
        query.setParameter(i+1,args.get(i));
      }
    }

    PageBean pageBean = findPageBean(sql.toString(), args, pageable);

    //分页查询
    query.setFirstResult(pageBean.getPageNumber()*pageBean.getPageSize());
    query.setMaxResults(pageBean.getPageNumber()*pageBean.getPageSize()+pageBean.getPageSize());
    List<Object[]> resultList = query.getResultList();

    //封装查询结果
    List<UserDTO> userDTOList = new ArrayList<>();
    if(resultList != null && resultList.size() > 0) {
      resultList.forEach(objs -> {
        UserDTO userDTONew = UserDTO.builder()
            .id(((BigInteger)objs[0]).longValue())
            .name((String) objs[1])
            .age(((Short) objs[2]).intValue())
            .birthday((Date) objs[3])
            .mobile((String) objs[4])
            .email((String) objs[5])
            .addressDTO(
                AddressDTO.builder()
                  .country((String) objs[6])
                  .province((String) objs[7])
                  .city((String) objs[8])
                  .build()
            )
            .createDate((Date) objs[9])
            .build();
        //添加到列表中
        userDTOList.add(userDTONew);
      });
    }

    return new PageRecord<UserDTO>(pageBean,userDTOList);
  }

  //查询分页信息
  PageBean findPageBean(String sql,List<Object> args,Pageable pageRequest){
    String countSql = " select count(*) from (" + sql + ") tb ";

    Query countQuery = entityManager.createNativeQuery(countSql);

    if(args != null && args.size()>0){
      for(int i=0;i<args.size();i++){
        countQuery.setParameter(i+1,args.get(i));
      }
    }

    //查询总记录数
    long totalCount = ((BigInteger)countQuery.getSingleResult()).longValue();

    return new PageBean(pageRequest.getPageNumber(),pageRequest.getPageSize(),totalCount);

  }

  //使用entityManager实现多表联合带条件带分页查询及排序
  public PageRecord<UserInfo> findUserListByPage2(UserDTO userDTO, Pageable pageable){

    List<Object> args = new ArrayList<>();//用于封装参数

    StringBuffer sql = new StringBuffer();
    sql.append(
        " select u.id as id,u.name as name,u.age as age,u.birthday as birthday,u.mobile as mobile,"
            + "u.email as email,a.country as country,a.province as province,a.city as city "
            + ", u.create_date"
            + " from tb_user u "
            + " left join  tb_address a on u.address_id = a.id "
            + " where 1=1 "
    );

    if(userDTO  != null){
      if(StringUtils.isNotBlank(userDTO.getName())){
        //模糊查询
        sql.append(" and u.name like ? ");
        args.add("%"+userDTO.getName()+"%");
      }
      if(null != userDTO.getAge()){
        //精确查询
        sql.append(" and u.age = ? ");
        args.add(userDTO.getAge());
      }
      //求生日在某个时间段范围内的用户
      if(null != userDTO.getStartTime()){
        //大于等于
        sql.append(" and u.birthday >= ? ");
        args.add(userDTO.getStartTime());
      }
      if(null != userDTO.getEndTime()){
        //小于等于
        sql.append(" and u.birthday <= ? ");
        args.add(userDTO.getEndTime());
      }

      if(userDTO.getAddressDTO() != null) {
        AddressDTO addressDTO = userDTO.getAddressDTO();
        //查询某个城市的用户
        if(StringUtils.isNotBlank(addressDTO.getCity())) {
          sql.append(" and a.city = ? ");
          args.add(addressDTO.getCity());
        }
      }

    }

    //按照创建时间倒序排序
   sql.append(" order by u.create_date desc ");

    //创建query对象
    Query query = entityManager.createNativeQuery(sql.toString(), UserInfo.class);

    //设置查询参数
    if(args.size()>0){
      for(int i=0;i<args.size();i++){
        //注意：jpa的setParameter是从1开始的
        query.setParameter(i+1,args.get(i));
      }
    }

    PageBean pageBean = findPageBean(sql.toString(), args, pageable);

    //分页查询
    query.setFirstResult(pageBean.getPageNumber()*pageBean.getPageSize());
    query.setMaxResults(pageBean.getPageNumber()*pageBean.getPageSize()+pageBean.getPageSize());
    List<UserInfo> resultList = query.getResultList();

   return new PageRecord<UserInfo>(pageBean,resultList);
  }

}
