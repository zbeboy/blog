package com.zbeboy.blog.vo;

/**
 * Created by lenovo on 2016-02-20.
 */
public class SendBlogVo {

    private String title;
    private int type;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SendBlogVo{" +
                "title='" + title + '\'' +
                ", type=" + type +
                ", content='" + content + '\'' +
                '}';
    }
}
