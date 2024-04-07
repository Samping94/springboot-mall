package com.sam.springbootmall.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserRegisterRequest {
    @NotBlank
    String email;
    @NotBlank
    String password;
}
