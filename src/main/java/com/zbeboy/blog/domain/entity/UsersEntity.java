package com.zbeboy.blog.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Administrator on 2016/2/4.
 */
@Entity(name = "users")
public class UsersEntity {

    @Id
    @Size(max = 200, message = "用户名在200字符之间!")
    @Column(name = "username")
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean enabled;

    @Column(name = "code")
    private String code;

    @Column(name = "is_pass")
    private boolean is_pass;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean is_pass() {
        return is_pass;
    }

    public void setIs_pass(boolean is_pass) {
        this.is_pass = is_pass;
    }

    @Override
    public String toString() {
        return "UsersEntity{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", code='" + code + '\'' +
                ", is_pass=" + is_pass +
                '}';
    }
}
