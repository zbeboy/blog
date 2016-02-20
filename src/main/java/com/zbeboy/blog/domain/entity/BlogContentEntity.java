package com.zbeboy.blog.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

/**
 * Created by lenovo on 2016-02-20.
 */
@Entity(name = "blog_content")
public class BlogContentEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "blog_content")
    @Size(max = 1000,message="内容应为1000字符之间!")
    private String blogContent;

    @Column(name = "blog_simple_content_id")
    private int blogSimpleContentId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBlogContent() {
        return blogContent;
    }

    public void setBlogContent(String blogContent) {
        this.blogContent = blogContent;
    }

    public int getBlogSimpleContentId() {
        return blogSimpleContentId;
    }

    public void setBlogSimpleContentId(int blogSimpleContentId) {
        this.blogSimpleContentId = blogSimpleContentId;
    }

    @Override
    public String toString() {
        return "BlogContentEntity{" +
                "id=" + id +
                ", blogContent='" + blogContent + '\'' +
                ", blogSimpleContentId=" + blogSimpleContentId +
                '}';
    }
}
