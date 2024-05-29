package com.sam.springbootmall.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserRegisterRequest {
    @NotBlank
    @Email
    String email;
    @NotBlank
    String password;
}
