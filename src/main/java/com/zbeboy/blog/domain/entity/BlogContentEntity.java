package com.zbeboy.blog.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/2/4.
 */
@Entity(name = "blog_content")
public class BlogContentEntity{
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "blog_title")
    @NotNull
    @Size(max = 50,message = "标题就为50字符之间!")
    private String blogTitle;
    @Column(name = "blog_content")
    @NotNull
    @Size(max = 1000,message="内容应为1000字符之间!")
    private String blogContent;

    @Column(name = "blog_create_time")
    private Date blogCreateTime;

    @ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name="blog_type_id")
    private BlogContentTypeEntity blogContentTypeEntity;

    @ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name="blog_user")
    private UsersEntity usersEntity;

    @ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name="archives_id")
    private ArchivesEntity archivesEntity;

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

    public Date getBlogCreateTime() {
        return blogCreateTime;
    }

    public void setBlogCreateTime(Date blogCreateTime) {
        this.blogCreateTime = blogCreateTime;
    }

    public BlogContentTypeEntity getBlogContentTypeEntity() {
        return blogContentTypeEntity;
    }

    public void setBlogContentTypeEntity(BlogContentTypeEntity blogContentTypeEntity) {
        this.blogContentTypeEntity = blogContentTypeEntity;
    }

    public UsersEntity getUsersEntity() {
        return usersEntity;
    }

    public void setUsersEntity(UsersEntity usersEntity) {
        this.usersEntity = usersEntity;
    }

    public ArchivesEntity getArchivesEntity() {
        return archivesEntity;
    }

    public void setArchivesEntity(ArchivesEntity archivesEntity) {
        this.archivesEntity = archivesEntity;
    }

    @Override
    public String toString() {
        return "BlogContentEntity{" +
                "id=" + id +
                ", blogTitle='" + blogTitle + '\'' +
                ", blogContent='" + blogContent + '\'' +
                ", blogCreateTime=" + blogCreateTime +
                ", blogContentTypeEntity=" + blogContentTypeEntity +
                ", usersEntity=" + usersEntity +
                ", archivesEntity=" + archivesEntity +
                '}';
    }
}
