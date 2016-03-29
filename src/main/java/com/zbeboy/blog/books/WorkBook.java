package com.zbeboy.blog.books;

/**
 * Created by Administrator on 2016/2/15.
 */
public class WorkBook {

    // article data pagination
    private final static int article_page_size = 4;

    private final static int article_buttons = 3;

    private static final String mail_form = "863052317@qq.com";

    private static final int corePoolSize = 2;

    private static final int maxPoolSize = 50;

    private static final int queueCapacity = 10000;

    public static int getArticle_page_size() {
        return article_page_size;
    }

    public static int getArticle_buttons() {
        return article_buttons;
    }

    public static String getMail_form() {
        return mail_form;
    }

    public static int getCorePoolSize() {
        return corePoolSize;
    }

    public static int getMaxPoolSize() {
        return maxPoolSize;
    }

    public static int getQueueCapacity() {
        return queueCapacity;
    }
}
