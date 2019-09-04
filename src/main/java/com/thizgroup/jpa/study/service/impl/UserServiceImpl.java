package com.thizgroup.jpa.study.service.impl;

import com.thizgroup.jpa.study.dao.UserDao;
import com.thizgroup.jpa.study.dao.UserRepository;
import com.thizgroup.jpa.study.dto.AddressDTO;
import com.thizgroup.jpa.study.dto.PageRecord;
import com.thizgroup.jpa.study.dto.UserDTO;
import com.thizgroup.jpa.study.dto.UserProjection;
import com.thizgroup.jpa.study.model.User;
import com.thizgroup.jpa.study.service.IUserService;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
  public Page<User> findUserListByPage(User user, Pageable pageable) {

    Example<User> example = Example.of(user,
        ////模糊查询匹配开头，即{name}%
        ExampleMatcher.matching().withMatcher("name",ExampleMatcher.GenericPropertyMatchers.startsWith())
            //是否包含某个字符串 email like ‘%’ + ?0 + ‘%’
        .withMatcher("email",ExampleMatcher.GenericPropertyMatchers.contains()));
    return userRepository.findAll(example,pageable);
  }

  @Override
  public PageRecord<UserDTO> findUserDTOListByPage(UserDTO userDTO, Pageable pageable) {
    return userDao.findUserListByPage(userDTO,pageable);
  }

  @Override
  public Page<User> findUserListByCity(String city, Pageable pageable) {

    return userRepository.findUserListByCity(city,pageable);
  }

  @Override
  public Page<User> findUserListByCondition(String name, String email, Pageable pageable) {
    return userRepository.findUserListByCondition(name,email,pageable);
  }

  @Override
  public Page<UserDTO> findUserListByCondition2(UserDTO userDTO, Pageable pageable) {

    String name = null;
    String city = null;

    if(userDTO != null){
      if(StringUtils.isNotBlank(userDTO.getName())){
        name = userDTO.getName();
      }
      if(userDTO.getAddressDTO() != null && StringUtils.isNotBlank(userDTO.getAddressDTO().getCity())){
        city = userDTO.getAddressDTO().getCity();
      }
    }

    Page<Map<String,Object>> pageList = userRepository
        .findUserListByCondition2(name,city,pageable);

    Page<UserDTO> userDTOS = pageList.map(data ->
        UserDTO.builder()
            .id(((BigInteger) data.get("id")).longValue())
            .name(data.get("name").toString())
            .age(((Short) data.get("age")).intValue())
            .birthday((Date) data.get("birthday"))
            .email(data.get("email").toString())
            .addressDTO(
                AddressDTO.builder()
                    .country(data.get("country").toString())
                    .city(data.get("city").toString())
                    .build()
            )
            .build()
    );

    return userDTOS;
  }



  @Override
  public User findById(Long id) {
    return userRepository.findById(id).orElseGet(()->null);
  }

  @Override
  public User addUser(UserDTO userDTO) {

    userDTO.setId(null);
    User user = convertDtoToEntity(userDTO);
    user.setCreateDate(new Date());
    user.setModifyDate(new Date());

    User savedUser = userRepository.save(user);

    return savedUser;
  }

  @Override
  public void updateUser(UserDTO userDTO) {
    User user = convertDtoToEntity(userDTO);
    //查询用户信息
    User userOld = findById(user.getId());
    if(userOld == null) throw new RuntimeException("user not found");
    userOld.setModifyDate(new Date());
    userOld.setEmail(user.getEmail());
    userOld.setName(user.getName());
    userOld.setAddressId(user.getAddressId());
    userOld.setMobile(user.getMobile());
    userOld.setAge(user.getAge());
    userOld.setBirthday(user.getBirthday());

    userRepository.save(user);
  }

  @Override
  public UserDTO findUserByLoginName(String loginName) {
    return convertEntityToDto(userRepository.findUserByLoginName(loginName));
  }

  @Override
  public List<UserDTO> findUserByAddressIdAndAge(Long addressId, int age) {
    List<User> userList = userRepository.findByAddressIdAndAge(addressId,age);
    userList = userList == null? new ArrayList<>() : userList;
    List<UserDTO> userDTOS = userList.stream().map(
        user -> convertEntityToDto(user)
    ).collect(Collectors.toList());
    return userDTOS;
  }

  @Override
  public UserProjection findUserInfoById(Long id) {
    return userRepository.findUserInfoById(id);
  }

  private User convertDtoToEntity(UserDTO userDTO) {
    Assert.notNull(userDTO,"userDTO cannot be null");
    return User.builder()
        .id(userDTO.getId())
        .name(userDTO.getName())
        .age(userDTO.getAge())
        .birthday(userDTO.getBirthday())
        .addressId(userDTO.getAddressDTO() == null ? null : userDTO.getAddressDTO().getId())
        .email(userDTO.getEmail())
        .mobile(userDTO.getMobile())
        .build();
  }

  private UserDTO convertEntityToDto(User user) {
    return user == null ? null : UserDTO.builder()
        .id(user.getId())
        .name(user.getName())
        .mobile(user.getMobile())
        .email(user.getEmail())
        .addressDTO(user.getAddressId()== null?null:
            AddressDTO.builder().id(user.getAddressId()).build())
        .birthday(user.getBirthday())
        .createDate(user.getCreateDate())
        .modifyDate(user.getModifyDate())
        .age(user.getAge())
        .build();
  }

}
