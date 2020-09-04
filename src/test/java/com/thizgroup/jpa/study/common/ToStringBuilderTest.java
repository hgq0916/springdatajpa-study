package com.thizgroup.jpa.study.common;

import com.thizgroup.jpa.study.dto.PersonDTO;
import com.thizgroup.jpa.study.model.Person;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;

import java.util.Date;

public class ToStringBuilderTest {

    @Test
    public void test1(){
        PersonDTO personDTO = PersonDTO.builder()
                .nickName("haha")
                .birthday(new Date().toString())
                .userId("111")
                .build();

        System.out.println(ToStringBuilder.reflectionToString(personDTO, ToStringStyle.SHORT_PREFIX_STYLE));

    }
}
