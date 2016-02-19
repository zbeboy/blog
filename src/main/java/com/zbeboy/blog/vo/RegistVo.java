package com.zbeboy.blog.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by Administrator on 2016/2/19.
 */
public class RegistVo {
    @NotNull
    @Pattern(regexp = "/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/")
    private String email;

    @NotNull
    @Pattern(regexp = "/^(\\w){6,20}$/")
    private String regist_password;

    @NotNull
    @Pattern(regexp = "/^(\\w){6,20}$/")
    private String confirm_password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegist_password() {
        return regist_password;
    }

    public void setRegist_password(String regist_password) {
        this.regist_password = regist_password;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    @Override
    public String toString() {
        return "RegistVo{" +
                "email='" + email + '\'' +
                ", regist_password='" + regist_password + '\'' +
                ", confirm_password='" + confirm_password + '\'' +
                '}';
    }
}
