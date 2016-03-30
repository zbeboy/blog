package com.zbeboy.blog.service;

import com.zbeboy.blog.domain.entity.UsersEntity;

/**
 * Created by Administrator on 2016/3/29.
 */
public interface MailService {

    /**
     * 发送邮件
     * @param to
     * @param subject
     * @param content
     * @param isMultipart
     * @param isHtml
     */
    void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml);

    /**
     * 发送激活邮件
     * @param usersEntity
     * @param baseUrl
     */
    void sendActivationEmail(UsersEntity usersEntity, String baseUrl);

    /**
     * 发送账号创建成功邮件
     * @param usersEntity
     * @param baseUrl
     */
    void sendCreationEmail(UsersEntity usersEntity, String baseUrl);

    /**
     * 发送密码重置邮件
     * @param usersEntity
     * @param baseUrl
     */
    void sendPasswordResetMail(UsersEntity usersEntity, String baseUrl);
}
