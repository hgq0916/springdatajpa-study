package com.thizgroup.jpa.study.dao;

import com.thizgroup.jpa.study.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person,Integer> , QuerydslPredicateExecutor<Person> {

}
