package com.zbeboy.blog.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zbeboy.blog.books.WorkBook;
import com.zbeboy.blog.domain.entity.ArchivesEntity;
import com.zbeboy.blog.domain.entity.BlogContentEntity;
import com.zbeboy.blog.domain.entity.BlogContentTypeEntity;
import com.zbeboy.blog.domain.entity.BlogSimpleContentEntity;
import com.zbeboy.blog.domain.repository.*;
import com.zbeboy.blog.service.UsersService;
import com.zbeboy.blog.util.PaginationUtil;
import com.zbeboy.blog.vo.ArticleVo;
import com.zbeboy.blog.vo.PaginationVo;
import com.zbeboy.blog.vo.PostsVo;
import com.zbeboy.blog.vo.SendBlogVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2016/2/15.
 */
@Controller
public class BlogController {

    private final Logger log = LoggerFactory.getLogger(BlogController.class);

    private final BlogContentRepository blogContentRepository;

    private final BlogSimpleContentRepository blogSimpleContentRepository;

    private final ArchivesRepository archivesRepository;

    private final BlogContentTypeRepository blogContentTypeRepository;

    private final UsersRepository usersRepository;

    @Resource
    private UsersService usersService;

    @Autowired
    public BlogController(BlogContentRepository blogContentRepository, BlogSimpleContentRepository blogSimpleContentRepository,
                          ArchivesRepository archivesRepository, BlogContentTypeRepository blogContentTypeRepository,
                          UsersRepository usersRepository) {
        this.blogContentRepository = blogContentRepository;
        this.blogSimpleContentRepository = blogSimpleContentRepository;
        this.archivesRepository = archivesRepository;
        this.blogContentTypeRepository = blogContentTypeRepository;
        this.usersRepository = usersRepository;
    }

    /**
     * 文章数据
     * @param page
     * @param archviesId
     * @param typeId
     * @return
     */
    @RequestMapping(value = "/articleDatas")
    @ResponseBody
    public Map<String, Object> articleDatas(int page, @RequestParam(value = "archviesId", defaultValue = "0") int archviesId,
                                            @RequestParam(value = "typeId", defaultValue = "0") int typeId) {
        Map<String, Object> map = new HashMap<>();
        int pageSize = WorkBook.getArticle_page_size();
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(page, pageSize, sort);
        Page<BlogSimpleContentEntity> blogSimpleContentEntities = null;
        if (archviesId == 0 && typeId == 0) {
            blogSimpleContentEntities = blogSimpleContentRepository.findAll(pageable);
        }

        if (archviesId > 0 && typeId == 0) {
            blogSimpleContentEntities = blogSimpleContentRepository.findByArchivesId(pageable, archivesRepository.findOne(archviesId));
        }

        if (archviesId == 0 && typeId > 0) {
            blogSimpleContentEntities = blogSimpleContentRepository.findByBlogTypeId(pageable, blogContentTypeRepository.findOne(typeId));
        }

        List<ArticleVo> articleVos = new ArrayList<>();
        for (BlogSimpleContentEntity blogSimpleContentEntity : blogSimpleContentEntities) {
            ArticleVo articleVo = new ArticleVo();
            articleVo.setId(blogSimpleContentEntity.getId());
            articleVo.setBlogTitle(blogSimpleContentEntity.getBlogTitle());

            Page<BlogContentEntity> blogContentEntities =
                    blogContentRepository.findByBlogSimpleContentId(new PageRequest(0, 1), blogSimpleContentEntity.getId());
            if (!StringUtils.isEmpty(blogContentEntities)) {
                for (BlogContentEntity blogContentEntity : blogContentEntities) {
                    articleVo.setBlogContent(blogContentEntity.getBlogContent());
                }
            }
            articleVo.setBlogCreateTime(new SimpleDateFormat("yyyy.MM.dd hh:mm:ss").format(blogSimpleContentEntity.getBlogCreateTime()));
            articleVo.setBlogType(blogSimpleContentEntity.getBlogTypeId().getTypeName());
            articleVo.setBlogTypeId(blogSimpleContentEntity.getBlogTypeId().getId());
            articleVo.setUsername(blogSimpleContentEntity.getBlogUser().getUsername());
            articleVo.setFormatTime(blogSimpleContentEntity.getArchivesId().getTimes());
            articleVo.setArchivesId(blogSimpleContentEntity.getArchivesId().getId());
            articleVos.add(articleVo);
        }
        map.put("items", articleVos);
        PaginationVo paginationVo = PaginationUtil.createPage(blogSimpleContentEntities.getTotalElements(),blogSimpleContentEntities.getTotalPages(),pageSize,archviesId,typeId);
        map.put("pagination", paginationVo);
        return map;
    }

    /**
     * 文章单条数据
     * @param blogSimpleContentId
     * @param modelMap
     * @param request
     * @return
     */
    @RequestMapping("/article")
    public String singleArticle(@RequestParam(value = "id", defaultValue = "0") int blogSimpleContentId, ModelMap modelMap,
                                HttpServletRequest request) {
        modelMap.addAttribute("is_login",StringUtils.isEmpty(usersService.getUserName())?false:true);
        if (blogSimpleContentId > 0) {
            BlogSimpleContentEntity blogSimpleContentEntity = blogSimpleContentRepository.findOne(blogSimpleContentId);
            List<BlogContentEntity> blogContentEntities = new ArrayList<>();
            ArticleVo articleVo = new ArticleVo();
            if (!StringUtils.isEmpty(blogSimpleContentEntity)) {
                blogContentEntities = blogContentRepository.findByBlogSimpleContentId(blogSimpleContentId);
                articleVo.setId(blogSimpleContentEntity.getId());
                articleVo.setBlogTitle(blogSimpleContentEntity.getBlogTitle());
                articleVo.setBlogCreateTime(new SimpleDateFormat("yyyy.MM.dd hh:mm:ss").format(blogSimpleContentEntity.getBlogCreateTime()));
                articleVo.setBlogType(blogSimpleContentEntity.getBlogTypeId().getTypeName());
                articleVo.setBlogTypeId(blogSimpleContentEntity.getBlogTypeId().getId());
                articleVo.setUsername(blogSimpleContentEntity.getBlogUser().getUsername());
                articleVo.setArchivesId(blogSimpleContentEntity.getArchivesId().getId());
                articleVo.setFormatTime(blogSimpleContentEntity.getArchivesId().getTimes());
            }
            Sort sort = new Sort(Sort.Direction.DESC, "id");
            Page<BlogSimpleContentEntity> blogSimpleContentEntities = blogSimpleContentRepository.findAll(new PageRequest(0, 3, sort));
            List<PostsVo> postsVos = new ArrayList<>();
            for (BlogSimpleContentEntity blogSimpleContentEntity1 : blogSimpleContentEntities) {
                PostsVo postsVo = new PostsVo();
                postsVo.setArticle_title(blogSimpleContentEntity1.getBlogTitle());
                postsVo.setId(blogSimpleContentEntity1.getId());
                postsVos.add(postsVo);
            }

            Page<ArchivesEntity> archivesEntityPage = archivesRepository.findAll(new PageRequest(0, 3, sort));

            Page<BlogContentTypeEntity> blogContentTypeEntityPage = blogContentTypeRepository.findAll(new PageRequest(0, 3, sort));
            modelMap.addAttribute("posts", postsVos);
            modelMap.addAttribute("archives", archivesEntityPage);
            modelMap.addAttribute("type", blogContentTypeEntityPage);
            modelMap.addAttribute("descriptions", articleVo);
            modelMap.addAttribute("content", blogContentEntities);

            CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
            if (csrfToken != null) {
                modelMap.addAttribute("_csrf", csrfToken);
            }
            return "single";
        } else {
            return "full-width";
        }

    }

    /**
     * 保存文章
     * @param articleDatas
     * @return
     */
    @RequestMapping(value = "/user/saveArticle", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveArtitcle(@RequestParam(value = "articledatas") String articleDatas) {
        Map<String, Object> map = new HashMap<>();
        try {
            SendBlogVo[] sendBlogVos = new ObjectMapper().readValue(articleDatas, SendBlogVo[].class);
            if (sendBlogVos.length > 0) {
                BlogSimpleContentEntity blogSimpleContentEntity = new BlogSimpleContentEntity();

                //时间
                String currentDate = new SimpleDateFormat("yyyy.MM").format(new Date());
                List<ArchivesEntity> archivesEntities = archivesRepository.findByTimes(currentDate);
                if (!archivesEntities.isEmpty()) {
                    blogSimpleContentEntity.setArchivesId(archivesEntities.get(0));
                } else {
                    ArchivesEntity archivesEntity = new ArchivesEntity();
                    archivesEntity.setTimes(currentDate);
                    int archivesId = archivesRepository.save(archivesEntity).getId();
                    blogSimpleContentEntity.setArchivesId(archivesRepository.findOne(archivesId));
                }
                blogSimpleContentEntity.setBlogUser(usersRepository.findOne(usersService.getUserName()));
                blogSimpleContentEntity.setBlogTypeId(blogContentTypeRepository.findOne(sendBlogVos[0].getType()));
                blogSimpleContentEntity.setBlogTitle(sendBlogVos[0].getTitle());
                blogSimpleContentEntity.setBlogCreateTime(new Date());

                int blogSimpleContentId = blogSimpleContentRepository.save(blogSimpleContentEntity).getId();

                for (int i = 0; i < sendBlogVos.length; i++) {
                    BlogContentEntity blogContentEntity = new BlogContentEntity();
                    blogContentEntity.setBlogContent(sendBlogVos[i].getContent());
                    blogContentEntity.setBlogSimpleContentId(blogSimpleContentId);
                    blogContentRepository.save(blogContentEntity);
                }
                map.put("state", true);
                map.put("msg", "文章保存成功！");
            } else {
                map.put("state", false);
                map.put("msg", "参数为空！");
            }
        } catch (IOException e) {
            e.printStackTrace();
            map.put("state", false);
            map.put("msg", e.getMessage());
        }
        return map;
    }

    /**
     * 搜索
     * @param search
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/search")
    public String search(@RequestParam(value = "search") String search, ModelMap modelMap) {
        List<ArticleVo> articleVos = new ArrayList<>();
        List<BlogSimpleContentEntity> blogSimpleContentEntities = blogSimpleContentRepository.findByBlogTitleLike("%" + search + "%");
        for (BlogSimpleContentEntity blogSimpleContentEntity : blogSimpleContentEntities) {
            ArticleVo articleVo = new ArticleVo();
            articleVo.setId(blogSimpleContentEntity.getId());
            articleVo.setBlogTitle(blogSimpleContentEntity.getBlogTitle());
            articleVo.setBlogCreateTime(new SimpleDateFormat("yyyy.MM.dd hh:mm:ss").format(blogSimpleContentEntity.getBlogCreateTime()));
            articleVo.setBlogType(blogSimpleContentEntity.getBlogTypeId().getTypeName());
            articleVo.setBlogTypeId(blogSimpleContentEntity.getBlogTypeId().getId());
            articleVo.setUsername(blogSimpleContentEntity.getBlogUser().getUsername());
            articleVo.setArchivesId(blogSimpleContentEntity.getArchivesId().getId());
            articleVo.setFormatTime(blogSimpleContentEntity.getArchivesId().getTimes());

            Page<BlogContentEntity> blogContentEntities =
                    blogContentRepository.findByBlogSimpleContentId(new PageRequest(0, 1), blogSimpleContentEntity.getId());
            if (!StringUtils.isEmpty(blogContentEntities)) {
                for (BlogContentEntity blogContentEntity : blogContentEntities) {
                    articleVo.setBlogContent(blogContentEntity.getBlogContent());
                }
            }

            articleVos.add(articleVo);
        }

        modelMap.addAttribute("searchDatas", articleVos);

        return "search";
    }
}
