package com.sam.springbootmall.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class User {
    private Integer userId;
    private String email;
    @JsonIgnore
    private String password;
    private Date createdDate;
    private Date lastModifiedDate;
}
