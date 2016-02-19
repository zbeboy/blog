package com.zbeboy.blog.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Administrator on 2016/2/15.
 */
@Entity(name = "archives")
public class ArchivesEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private int id;

    @Column(name = "times")
    @Size(max = 20,message = "时间应为20字符之间!")
    private String times;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    @Override
    public String toString() {
        return "ArchivesEntity{" +
                "id=" + id +
                ", times='" + times + '\'' +
                '}';
    }
}
