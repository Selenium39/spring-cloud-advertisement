package com.selenium.ad.dao;

import com.selenium.ad.entity.AdUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdUserRepository extends JpaRepository<AdUser, Long> {

    /**
     * 根据用户名查找用户记录
     *
     * @param username 用户名
     * @return 用户信息
     */
    AdUser findByUsername(String username);

}
