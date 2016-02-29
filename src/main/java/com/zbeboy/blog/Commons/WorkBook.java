package com.zbeboy.blog.commons;

/**
 * Created by Administrator on 2016/2/15.
 */
public class WorkBook {
    // article data pagination
    private final static int article_page_size = 4;

    private final static int article_buttons = 7;

    public static int getArticle_page_size() {
        return article_page_size;
    }

    public static int getArticle_buttons() {
        return article_buttons;
    }
}
