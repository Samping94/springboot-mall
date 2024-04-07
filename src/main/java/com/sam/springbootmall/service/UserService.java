package com.sam.springbootmall.service;

import com.sam.springbootmall.dto.UserRegisterRequest;
import com.sam.springbootmall.model.User;

public interface UserService {

    Integer register(UserRegisterRequest request);

    User getUserById(Integer userId);
}
