package com.thizgroup.jpa.study.service;

import com.querydsl.core.QueryResults;
import com.thizgroup.jpa.study.JpaApplication;
import com.thizgroup.jpa.study.dto.PersonDTO;
import com.thizgroup.jpa.study.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

@SpringBootTest(classes={JpaApplication.class})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Test
    public void findAll(){
        List<Person> personList = personService.findAll();
        System.out.println(personList);
    }

    @Test
    public void findByUsernameAndPassword(){
        Person person = personService.findByUsernameAndPassword("张", "4");
        System.out.println(person);
    }

    @Test
    public void findAllPage(){
        QueryResults<Person> queryResults = personService.findAllPage(PageRequest.of(2, 2));
        long offset = queryResults.getOffset();
        long limit = queryResults.getLimit();
        System.out.println(queryResults);
    }

    @Test
    public void findAllUserDto() throws ParseException {
        List<PersonDTO> allUserDto = personService.findAllUserDto();
        System.out.println(allUserDto);
    }

    @Test
    public void findUserDTO2(){
        List<PersonDTO> userDTO2 = personService.findUserDTO2();
        System.out.println(userDTO2);
    }

    @Test
    public void findByNicknameAndUsername(){
        List<Person> personList = personService.findByNicknameAndUsername();
        System.out.println(personList);
    }

    @Test
    public void findCount(){
        long count = personService.findCount();
        System.out.println(count);
    }

    @Test
    public void findByPersonCondition(){
        PersonDTO searchDTO = PersonDTO.builder()
                .userId("2")
                .build();
        Page<Person> personPage = personService.findByPersonCondition(searchDTO, PageRequest.of(0, 1));
        System.out.println(personPage);
    }

}
