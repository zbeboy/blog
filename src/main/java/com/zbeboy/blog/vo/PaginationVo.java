package com.zbeboy.blog.vo;

/**
 * Created by Administrator on 2016/2/15.
 */
public class PaginationVo {
    private int pageSize;
    private int buttons;
    private Long total;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getButtons() {
        return buttons;
    }

    public void setButtons(int buttons) {
        this.buttons = buttons;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "PaginationVo{" +
                "pageSize=" + pageSize +
                ", buttons=" + buttons +
                ", total=" + total +
                '}';
    }
}
