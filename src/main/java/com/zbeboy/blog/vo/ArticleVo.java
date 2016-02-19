package com.zbeboy.blog.vo;

import com.zbeboy.blog.domain.entity.ArchivesEntity;
import com.zbeboy.blog.domain.entity.BlogContentTypeEntity;
import com.zbeboy.blog.domain.entity.UsersEntity;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by Administrator on 2016/2/15.
 */
public class ArticleVo {

    private int id;

    private String blogTitle;

    private String blogContent;

    private String  blogCreateTime;

    private String blogType;

    private String username;

    private String formatTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogContent() {
        return blogContent;
    }

    public void setBlogContent(String blogContent) {
        this.blogContent = blogContent;
    }

    public String getBlogCreateTime() {
        return blogCreateTime;
    }

    public void setBlogCreateTime(String  blogCreateTime) {
        this.blogCreateTime = blogCreateTime;
    }

    public String getBlogType() {
        return blogType;
    }

    public void setBlogType(String blogType) {
        this.blogType = blogType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFormatTime() {
        return formatTime;
    }

    public void setFormatTime(String formatTime) {
        this.formatTime = formatTime;
    }

    @Override
    public String toString() {
        return "ArticleVo{" +
                "id=" + id +
                ", blogTitle='" + blogTitle + '\'' +
                ", blogContent='" + blogContent + '\'' +
                ", blogCreateTime=" + blogCreateTime +
                ", blogType='" + blogType + '\'' +
                ", username='" + username + '\'' +
                ", formatTime='" + formatTime + '\'' +
                '}';
    }
}
