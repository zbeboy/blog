package com.zbeboy.blog.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Administrator on 2016/2/4.
 */
@Entity(name = "contact")
public class ContactEntity  {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "contact_username",nullable = false)
    @Size(max = 64,message = "用户名应为64字符之间!")
    private String contactUsername;

    @Column(name = "email",nullable = false)
    @Size(max = 200,message = "邮箱应为200字符之间!")
    private String email;

    @Column(name = "subject",nullable = false)
    @Size(max = 50,message = "主题应为50字符之间!")
    private String subject;

    @Column(name = "message",nullable = false)
    @Size(max = 200,message = "消息应为200字符之间!")
    private String message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContactUsername() {
        return contactUsername;
    }

    public void setContactUsername(String contactUsername) {
        this.contactUsername = contactUsername;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ContactEntity{" +
                "id=" + id +
                ", contactUsername='" + contactUsername + '\'' +
                ", email='" + email + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
