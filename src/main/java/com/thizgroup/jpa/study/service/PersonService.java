package com.thizgroup.jpa.study.service;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.thizgroup.jpa.study.dao.PersonRepository;
import com.thizgroup.jpa.study.dto.PersonDTO;
import com.thizgroup.jpa.study.dto.PersonDeptDTO;
import com.thizgroup.jpa.study.model.Person;
import com.thizgroup.jpa.study.model.QDepartment;
import com.thizgroup.jpa.study.model.QPerson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    JPAQueryFactory jpaQueryFactory;

    //以下展示使用原生的dsl/

    /**
     * 查询所有的实体,根据uIndex字段排序
     *
     * @return
     */
    public List<Person> findAll() {
       QPerson qPerson = QPerson.person;
        List<Person> personList = jpaQueryFactory.selectFrom(qPerson)
                .orderBy(qPerson.birthday.asc())
                .fetch();
        return personList;
    }

    /**
     * 根据用户名和密码查询
     */
    public Person findByUsernameAndPassword(String username,String password){
        QPerson qPerson = QPerson.person;
        Person person = jpaQueryFactory.selectFrom(qPerson)
                .where(qPerson.username.contains(username)
                    .and(qPerson.password.eq(password))
                )
                .fetchOne();

        return person;
    }

    /**
     * 分页查询
     */
    public QueryResults<Person> findAllPage(Pageable pageable){
        QPerson qPerson = QPerson.person;
        QueryResults<Person> queryResults = jpaQueryFactory.selectFrom(qPerson)
                .orderBy(qPerson.uIndex.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return queryResults;
    }

    /**
     * 查询指定的字段
     */
    public List<PersonDTO> findAllUserDto() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        QPerson qPerson = QPerson.person;
        List<PersonDTO> personDTOS = jpaQueryFactory.select(qPerson.uIndex, qPerson.userId, qPerson.birthday,
                qPerson.username,qPerson.nickName)
                .where(qPerson.birthday.between(sdf.parse("2020-09-07 12:00:00"),
                        sdf.parse("2020-09-30 12:00:00")))
                .from(qPerson)
                .offset(0)
                .limit(1)
                .fetch()
                .stream().map(
                        tuple -> {
                            PersonDTO personDTO = PersonDTO.builder()
                                    .userId(tuple.get(qPerson.userId).toString())
                                    .birthday(tuple.get(qPerson.birthday).toString())
                                    .username(tuple.get(3, String.class))
                                    .nickName(tuple.get(4, String.class))
                                    .build();
                            return personDTO;

                        }
                ).collect(Collectors.toList());

        return personDTOS;
    }

    /**
     * 部分字段映射查询
     */
    public List<PersonDTO> findUserDTO2(){
        QPerson qPerson = QPerson.person;

        List<PersonDTO> personDTOS = jpaQueryFactory.select(Projections.bean(PersonDTO.class,
                qPerson.nickName, qPerson.username))
                .from(qPerson)
                .fetch();
        return personDTOS;
    }

    /**
     * 使用jpa整合的dsl
     */
    public List<Person> findByNicknameAndUsername(){
        QPerson qPerson = QPerson.person;
        List<Person> personList = (List<Person>) personRepository.findAll(
                qPerson.username.like("里%").and(
                        qPerson.nickName.like("哈%")
                ),
                qPerson.uIndex.asc()
        );

        return personList;

    }

    /**
     * 根据条件统计人数
     */
    public long findCount(){
        long count = personRepository.count(QPerson.person.username.like("里%"));
        return count;
    }

    public Page<Person> findByPersonCondition(PersonDTO searchDTO,Pageable pageable){

        QPerson qPerson = QPerson.person;

        Predicate predicate = qPerson.isNotNull().or(qPerson.isNull());

        if(searchDTO != null){
            if(StringUtils.isNotBlank(searchDTO.getNickName())){
                predicate = ExpressionUtils.and(predicate,qPerson.nickName.like("哈%"));
            }
            if(StringUtils.isNotBlank(searchDTO.getUserId())){
                predicate = ExpressionUtils.and(predicate,qPerson.userId.eq(Integer.parseInt(searchDTO.getUserId())));
            }
        }

        Page<Person> personPage = personRepository.findAll(predicate, pageable);

        return personPage;

    }

    List<Person> findByUserPropertiesGroupByUIndex(PersonDTO searchDTO){
        QPerson qPerson = QPerson.person;

        Predicate predicate = qPerson.isNotNull().or(qPerson.isNull());

        if(searchDTO != null){
            if(StringUtils.isNotBlank(searchDTO.getNickName())){
                predicate = ExpressionUtils.and(predicate,qPerson.nickName.like("哈%"));
            }
            if(StringUtils.isNotBlank(searchDTO.getUserId())){
                predicate = ExpressionUtils.and(predicate,qPerson.userId.eq(Integer.parseInt(searchDTO.getUserId())));
            }
        }

        List<Person> personList = jpaQueryFactory.selectFrom(qPerson)
                .where(predicate)
                .groupBy(qPerson.uIndex)
                .having(qPerson.uIndex.longValue().max().goe(5))
                .orderBy(qPerson.uIndex.asc())
                .fetch();
        return personList;

    }

    public List<PersonDeptDTO> findByDepatmentIdDTO(String deptId){
        QPerson qPerson = QPerson.person;
        QDepartment qDepartment = QDepartment.department;

        List<PersonDeptDTO> personDeptDTOS = jpaQueryFactory.select(
                qPerson.nickName, qPerson.userId, qPerson.birthday, qPerson.username,
                qDepartment.deptName, qDepartment.createDate
        )
                .from(qPerson)
                //.join(qPerson.department, qDepartment)
                .leftJoin(qDepartment)
                .on(qPerson.department.deptId.eq(qDepartment.deptId))
                .where(qDepartment.deptId.eq(Integer.parseInt(deptId)))
                .offset(0)
                .limit(1)
                .fetch()
                .stream().map(
                        tuple -> {
                            PersonDeptDTO personDeptDTO = PersonDeptDTO.builder()
                                    .birthday(tuple.get(qPerson.birthday).toString())
                                    .deptBirth(tuple.get(qDepartment.createDate).toString())
                                    .deptName(tuple.get(qDepartment.deptName))
                                    .nickname(tuple.get(qPerson.nickName))
                                    .username(tuple.get(qPerson.username))
                                    .build();
                            return personDeptDTO;
                        }
                ).collect(Collectors.toList());

        return personDeptDTOS;
    }

}
