package com.zbeboy.blog.util;

import com.zbeboy.blog.commons.WorkBook;
import com.zbeboy.blog.vo.PaginationVo;

/**
 * Created by lenovo on 2016-02-20.
 */
public class PaginationUtil {

    public static PaginationVo createPage(Long total, int totalPages, int pageSize, int archviesId, int typeId) {

        PaginationVo paginationVo = new PaginationVo();
        if (totalPages > WorkBook.getArticle_buttons() || totalPages == 1) {
            paginationVo.setButtons(WorkBook.getArticle_buttons());
        } else {
            paginationVo.setButtons(totalPages);
        }

        paginationVo.setPageSize(pageSize);

        paginationVo.setTotalPages(totalPages);

        paginationVo.setTotalDatas(total);

        if (archviesId > 0) {
            paginationVo.setArchviesId(archviesId);
        } else {
            paginationVo.setArchviesId(0);
        }

        if (typeId > 0) {
            paginationVo.setTypeId(typeId);
        } else {
            paginationVo.setTypeId(0);
        }

        return paginationVo;
    }

}
