package com.zbeboy.blog.vo;

/**
 * Created by Administrator on 2016/2/15.
 */
public class PostsVo {
    private int id;
    private String article_title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    @Override
    public String toString() {
        return "PostsVo{" +
                "id=" + id +
                ", article_title='" + article_title + '\'' +
                '}';
    }
}
