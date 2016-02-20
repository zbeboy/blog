package com.zbeboy.blog.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/2/4.
 */
@Entity(name = "blog_simple_content")
public class BlogSimpleContentEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "blog_title")
    @Size(max = 50,message = "标题就为50字符之间!")
    private String blogTitle;

    @Column(name = "blog_create_time")
    private Date blogCreateTime;

    @ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name="blog_type_id")
    private BlogContentTypeEntity blogTypeId;

    @ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name="blog_user")
    private UsersEntity blogUser;

    @ManyToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name="archives_id")
    private ArchivesEntity archivesId;

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

    public Date getBlogCreateTime() {
        return blogCreateTime;
    }

    public void setBlogCreateTime(Date blogCreateTime) {
        this.blogCreateTime = blogCreateTime;
    }

    public BlogContentTypeEntity getBlogTypeId() {
        return blogTypeId;
    }

    public void setBlogTypeId(BlogContentTypeEntity blogTypeId) {
        this.blogTypeId = blogTypeId;
    }

    public UsersEntity getBlogUser() {
        return blogUser;
    }

    public void setBlogUser(UsersEntity blogUser) {
        this.blogUser = blogUser;
    }

    public ArchivesEntity getArchivesId() {
        return archivesId;
    }

    public void setArchivesId(ArchivesEntity archivesId) {
        this.archivesId = archivesId;
    }

    @Override
    public String toString() {
        return "BlogSimpleContentEntity{" +
                "id=" + id +
                ", blogTitle='" + blogTitle + '\'' +
                ", blogCreateTime=" + blogCreateTime +
                ", blogTypeId=" + blogTypeId +
                ", blogUser=" + blogUser +
                ", archivesId=" + archivesId +
                '}';
    }
}
