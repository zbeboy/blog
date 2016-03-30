package com.zbeboy.blog.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

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

    @Size(min = 2, max = 5)
    @Column(name = "lang_key", length = 5)
    private String langKey;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    private String activationKey;

    @Column(name = "is_activation")
    private boolean isActivation;

    @Size(max = 20)
    @Column(name = "reset_key",length = 20)
    private String resetKey;

    @Column(name = "reset_date", nullable = true)
    private Date resetDate;

    public Date getResetDate() {
        return resetDate;
    }

    public void setResetDate(Date resetDate) {
        this.resetDate = resetDate;
    }

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

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public boolean isActivation() {
        return isActivation;
    }

    public void setActivation(boolean activation) {
        isActivation = activation;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    @Override
    public String toString() {
        return "UsersEntity{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", langKey='" + langKey + '\'' +
                ", activationKey='" + activationKey + '\'' +
                ", isActivation=" + isActivation +
                ", resetKey='" + resetKey + '\'' +
                ", resetDate=" + resetDate +
                '}';
    }
}
