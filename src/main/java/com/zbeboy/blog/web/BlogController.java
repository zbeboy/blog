package com.zbeboy.blog.web;

import com.zbeboy.blog.Commons.WorkBook;
import com.zbeboy.blog.domain.entity.BlogContentEntity;
import com.zbeboy.blog.domain.repository.BlogContentRepository;
import com.zbeboy.blog.vo.ArticleVo;
import com.zbeboy.blog.vo.PaginationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/15.
 */
@Controller
public class BlogController {
    private final BlogContentRepository blogContentRepository;

    @Autowired
    public BlogController(BlogContentRepository blogContentRepository) {
        this.blogContentRepository = blogContentRepository;
    }

    @RequestMapping(value = "/articleDatas")
    @ResponseBody
    public Map<String ,Object> articleDatas(int page){
        Map<String ,Object> map = new HashMap<>();
        int pageSize = WorkBook.getArticle_page_size();
        Pageable pageable = new PageRequest(page,pageSize);
        Page<BlogContentEntity> blogContentEntities = blogContentRepository.findAll(pageable);
        List<ArticleVo> articleVos = new ArrayList<>();
        for(BlogContentEntity blogContentEntity:blogContentEntities){
            ArticleVo articleVo = new ArticleVo();
            articleVo.setId(blogContentEntity.getId());
            articleVo.setBlogTitle(blogContentEntity.getBlogTitle());
            articleVo.setBlogContent(blogContentEntity.getBlogContent());
            articleVo.setBlogCreateTime(new SimpleDateFormat("yyyy.MM.dd hh:mm:ss").format(blogContentEntity.getBlogCreateTime()));
            articleVo.setBlogType(blogContentEntity.getBlogContentTypeEntity().getTypeName());
            articleVo.setUsername(blogContentEntity.getUsersEntity().getUsername());
            articleVo.setFormatTime(blogContentEntity.getArchivesEntity().getTimes());
            articleVos.add(articleVo);
        }
        map.put("items",articleVos);
        PaginationVo paginationVo = new PaginationVo();
        paginationVo.setPageSize(pageSize);
        paginationVo.setButtons(WorkBook.getArticle_buttons());
        paginationVo.setTotal(blogContentRepository.count());
        map.put("pagination",paginationVo);
        return map;
    }
}
