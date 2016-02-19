package com.zbeboy.blog.web;

import com.zbeboy.blog.domain.entity.ArchivesEntity;
import com.zbeboy.blog.domain.entity.BlogContentEntity;
import com.zbeboy.blog.domain.entity.BlogContentTypeEntity;
import com.zbeboy.blog.domain.repository.ArchivesRepository;
import com.zbeboy.blog.domain.repository.BlogContentRepository;
import com.zbeboy.blog.domain.repository.BlogContentTypeRepository;
import com.zbeboy.blog.vo.ArticleVo;
import com.zbeboy.blog.vo.PostsVo;
import com.zbeboy.blog.vo.RegistVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/4.
 */
@Controller
public class MainController {
    private final BlogContentRepository blogContentRepository;

    private final ArchivesRepository archivesRepository;

    private final BlogContentTypeRepository blogContentTypeRepository;

    @Autowired
    public MainController(BlogContentRepository blogContentRepository, ArchivesRepository archivesRepository, BlogContentTypeRepository blogContentTypeRepository) {
        this.blogContentRepository = blogContentRepository;
        this.archivesRepository = archivesRepository;
        this.blogContentTypeRepository = blogContentTypeRepository;
    }

    @RequestMapping(value = {"/", "/index"})
    public String home(ModelMap modelMap) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Page<BlogContentEntity> blogContentEntityList = blogContentRepository.findAll(new PageRequest(0, 3, sort));
        List<PostsVo> postsVos = new ArrayList<>();
        for (BlogContentEntity blogContentEntity : blogContentEntityList) {
            PostsVo postsVo = new PostsVo();
            postsVo.setId(blogContentEntity.getId());
            postsVo.setArticle_title(blogContentEntity.getBlogTitle());
            postsVos.add(postsVo);
        }

        Page<ArchivesEntity> archivesEntityPage = archivesRepository.findAll(new PageRequest(0, 3, sort));

        Page<BlogContentTypeEntity> blogContentTypeEntityPage = blogContentTypeRepository.findAll(new PageRequest(0, 3, sort));
        modelMap.addAttribute("posts", postsVos);
        modelMap.addAttribute("archives", archivesEntityPage);
        modelMap.addAttribute("type", blogContentTypeEntityPage);
        return "index";
    }

    @RequestMapping(value = "/blog")
    public String blog() {
        return "full-width";
    }

    @RequestMapping(value = "/about")
    public String about() {
        return "about";
    }

    @RequestMapping(value = "/contact")
    public String contact() {
        return "contact";
    }

    @RequestMapping(value = "/login")
    public String login(ModelMap modelMap) {
        return "login";
    }

    @RequestMapping(value = "/regist")
    public String regist(ModelMap modelMap) {
        return "regist";
    }

    @RequestMapping(value = "/saveRegist")
    public String saveRegist(@Valid RegistVo registVo, BindingResult bindingResult, ModelMap modelMap) {
        if (!bindingResult.hasErrors()) {

        } else {
            modelMap.put("registError", true);
            modelMap.put("errorMsg","sfsdf");
        }
        return "registError";
    }

    @RequestMapping(value = "/article")
    public String article() {
        return "single";
    }
}
