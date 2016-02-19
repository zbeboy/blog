package com.zbeboy.blog.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Administrator on 2016/2/4.
 */
@Entity(name = "authorities")
@IdClass(AuthoritiesEntity.class)
public class AuthoritiesEntity implements Serializable{

    @Id
    @ManyToOne
    private UsersEntity usersEntity;
    @Id
    @NotNull
    @Size(max = 50,message = "权限应在50字符之间!")
    @Column(nullable = false)
    private String authority;

    public UsersEntity getUsersEntity() {
        return usersEntity;
    }

    public void setUsersEntity(UsersEntity usersEntity) {
        this.usersEntity = usersEntity;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthoritiesEntity that = (AuthoritiesEntity) o;

        if (usersEntity != null ? !usersEntity.equals(that.usersEntity) : that.usersEntity != null) return false;
        return authority != null ? authority.equals(that.authority) : that.authority == null;

    }

    @Override
    public int hashCode() {
        int result = usersEntity != null ? usersEntity.hashCode() : 0;
        result = 31 * result + (authority != null ? authority.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AuthoritiesEntity{" +
                "usersEntity=" + usersEntity +
                ", authority='" + authority + '\'' +
                '}';
    }
}
