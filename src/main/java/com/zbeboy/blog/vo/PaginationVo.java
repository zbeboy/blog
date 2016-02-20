package com.zbeboy.blog.vo;

/**
 * Created by Administrator on 2016/2/15.
 */
public class PaginationVo {
    private int pageSize;
    private int buttons;
    private int totalPages;
    private Long totalDatas;
    private int archviesId;
    private int typeId;

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

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalDatas() {
        return totalDatas;
    }

    public void setTotalDatas(Long totalDatas) {
        this.totalDatas = totalDatas;
    }

    public int getArchviesId() {
        return archviesId;
    }

    public void setArchviesId(int archviesId) {
        this.archviesId = archviesId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "PaginationVo{" +
                "pageSize=" + pageSize +
                ", buttons=" + buttons +
                ", totalPages=" + totalPages +
                ", totalDatas=" + totalDatas +
                ", archviesId=" + archviesId +
                ", typeId=" + typeId +
                '}';
    }
}
