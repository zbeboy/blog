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
@Entity(name = "blog_content_type")
public class BlogContentTypeEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "type_name",nullable = false)
    @NotNull
    @Size(max = 20,message = "类型名应在20字符之间!")
    private String typeName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "BlogContentTypeEntity{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
