package com.sam.springbootmall.service.impl;

import com.sam.springbootmall.dao.UserDao;
import com.sam.springbootmall.dto.UserLoginRequest;
import com.sam.springbootmall.dto.UserRegisterRequest;
import com.sam.springbootmall.model.User;
import com.sam.springbootmall.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest request) {
        // 檢查是否已被註冊
        User user = userDao.getUserByEmail(request.getEmail());
        if (Objects.nonNull(user)) {
            log.warn("該email {} 已經被{} 註冊", request.getEmail(), user.getUserId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "該email已註冊過囉");
        }

        // 使用MD5生成密碼的雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(request.getPassword().getBytes());
        request.setPassword(hashedPassword);

        // 創建帳號
        return userDao.createUser(request);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public User login(UserLoginRequest request) {
        User user = userDao.getUserByEmail(request.getEmail());

        // 檢查user是否存在
        if (Objects.isNull(user)) {
            log.warn("該email: {}, 尚未註冊", request.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 使用MD5生成密碼的雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(request.getPassword().getBytes());

        // 比較密碼
        if(StringUtils.equals(user.getPassword(), hashedPassword)){
            return user;
        }else {
            log.warn("email:{} 的密碼不正確", request.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
