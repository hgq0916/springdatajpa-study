package com.thizgroup.jpa.study.service.impl;

import com.thizgroup.jpa.study.dao.UserDao;
import com.thizgroup.jpa.study.dao.UserRepository;
import com.thizgroup.jpa.study.dto.PageRecord;
import com.thizgroup.jpa.study.dto.UserDTO;
import com.thizgroup.jpa.study.model.User;
import com.thizgroup.jpa.study.service.IUserService;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
public class UserServiceImpl implements IUserService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserDao userDao;

  @Override
  //Specification 实现单表多条件分页查询及排序
  public Page<User> findUserListByPage(UserDTO userDTO, Pageable pageable) {

    Specification<User> specification = new Specification<User>() {
      @Override
      public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery,
          CriteriaBuilder criteriaBuilder) {

        List<Predicate> andList = new ArrayList<>();//用来封装and条件
        List<Predicate> orList = new ArrayList<>();//用来封装or条件

       if(userDTO  != null){
         if(StringUtils.isNotBlank(userDTO.getName())){
           //模糊查询
           Predicate predicate = criteriaBuilder.like(root.get("name"), "%" + userDTO.getName() + "%");
           andList.add(predicate);
         }
         if(null != userDTO.getAge()){
           //精确查询
           Predicate predicate = criteriaBuilder.equal(root.get("age"), userDTO.getAge());
           andList.add(predicate);
         }
         //求生日在某个时间段范围内的用户
         if(null != userDTO.getStartTime()){
           //大于等于
           Predicate predicate = criteriaBuilder
               .greaterThanOrEqualTo(root.get("birthday"), userDTO.getStartTime());
           andList.add(predicate);
         }
         if(null != userDTO.getEndTime()){
           //小于等于
           Predicate predicate = criteriaBuilder
               .lessThanOrEqualTo(root.get("birthday"), userDTO.getEndTime());
           andList.add(predicate);
         }

         if(StringUtils.isNotBlank(userDTO.getMobile())){
           Predicate predicate = criteriaBuilder.like(root.get("mobile"), "%"+userDTO.getMobile()+"%");
           orList.add(predicate);
         }
         if(StringUtils.isNotBlank(userDTO.getEmail())){
           Predicate predicate = criteriaBuilder.like(root.get("email"), "%"+userDTO.getEmail()+"%");
           orList.add(predicate);
         }

       }

        Predicate andPredicate = null;
        Predicate orPredicate = null;

       //拼接and条件
        if(andList.size()>0){
          //转换为数组
          Predicate[] predicates = andList.toArray(new Predicate[]{});
          andPredicate = criteriaBuilder.and(predicates);
        }

        //拼接or条件
        if(orList.size()>0){
          //转换为数组
          Predicate[] predicates = orList.toArray(new Predicate[]{});
          orPredicate = criteriaBuilder.or(predicates);
        }

        //拼接查询条件
        List<Predicate> predicates = new ArrayList<>();
        if(andPredicate != null) predicates.add(andPredicate);
        if(orPredicate != null) predicates.add(orPredicate);

        Predicate predicate = null;
        if(predicates.size()>0){
          //转换为数组
          Predicate[] predicateArray = predicates.toArray(new Predicate[]{});
          predicate = criteriaBuilder.and(predicateArray);
        }

        List<Order> orderList = new ArrayList<>();//封装排序条件
        //按照创建时间 倒序排序
        orderList.add(criteriaBuilder.desc(root.get("createDate")));
        // 按照生日顺序排序
        orderList.add(criteriaBuilder.desc(root.get("birthday")));

        //设置排序条件
        criteriaQuery.orderBy(orderList);

        //返回查询条件
        return predicate;
      }
    };

    return userRepository.findAll(specification,pageable);
  }

  @Override
  public PageRecord<UserDTO> findUserDTOListByPage(UserDTO userDTO, Pageable pageable) {
    return userDao.findUserListByPage(userDTO,pageable);
  }

  @Override
  public User findById(Long id) {
    return userRepository.findById(id).orElseGet(()->null);
  }
}
