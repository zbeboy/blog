package com.zbeboy.blog.data;

/**
 * Created by lenovo on 2016-03-12.
 */
public class AjaxData {
    private String msg;
    private Boolean state;

    public AjaxData success() {
        this.setState(true);
        return this;
    }

    public AjaxData failed() {
        this.setState(false);
        return this;
    }

    public AjaxData msg(Object o) {
        this.setMsg(o.toString());
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
