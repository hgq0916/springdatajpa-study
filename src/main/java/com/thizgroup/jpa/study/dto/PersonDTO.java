package com.thizgroup.jpa.study.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonDTO {
    private String userId;
    private String username;
    private String nickname;
    private String birthday;
}