package com.zbeboy.blog.service;

/**
 * Created by lenovo on 2016-01-05.
 */
public interface UsersService {
    /**
     * 从 spring security 获取用户名
     *
     * @return Users username
     */
    String getUserName();

    /**
     * 从 spring security 获取密码
     *
     * @return Users password
     */
    String getPassword();
}
