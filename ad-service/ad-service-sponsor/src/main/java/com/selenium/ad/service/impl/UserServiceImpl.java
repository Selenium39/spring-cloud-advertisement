package com.selenium.ad.service.impl;

import com.selenium.ad.constant.Constants;
import com.selenium.ad.dao.AdUserRepository;
import com.selenium.ad.entity.AdUser;
import com.selenium.ad.exception.AdException;
import com.selenium.ad.service.IUserService;
import com.selenium.ad.utils.CommonUtils;
import com.selenium.ad.vo.CreateUserRequest;
import com.selenium.ad.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    private final AdUserRepository userRepository;

    public UserServiceImpl(AdUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) throws AdException {

        if (!request.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        //不能有同名用户名
        AdUser oldUser = userRepository.findByUsername(request.getUsername());
        if (oldUser != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_ERROR);
        }

        AdUser newUser = userRepository.save(new AdUser(request.getUsername(), CommonUtils.md5(request.getUsername())));

        return new CreateUserResponse(newUser.getId(), newUser.getUsername(), newUser.getToken(), newUser.getCreateTime(), newUser.getUpdateTime());
    }
}
