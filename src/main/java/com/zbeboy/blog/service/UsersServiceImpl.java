package com.zbeboy.blog.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by lenovo on 2016-02-20.
 */
@Service("usersService")
public class UsersServiceImpl implements UsersService{
    @Override
    public String getUserName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = null;
        if (principal instanceof UserDetails) {
            username = StringUtils.trimWhitespace(((UserDetails) principal).getUsername());
        }
        return username;
    }

    @Override
    public String getPassword() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String password = null;
        if (principal instanceof UserDetails) {
            password = StringUtils.trimWhitespace(((UserDetails) principal).getPassword());
        }
        return password;
    }
}
