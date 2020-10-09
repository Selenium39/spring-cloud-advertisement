package com.selenium.ad.service;

import com.selenium.ad.exception.AdException;
import com.selenium.ad.vo.CreateUserRequest;
import com.selenium.ad.vo.CreateUserResponse;

public interface IUserService {
    CreateUserResponse createUser(CreateUserRequest request) throws AdException;
}
