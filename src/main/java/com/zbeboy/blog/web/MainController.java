package com.zbeboy.blog.web;

import com.zbeboy.blog.domain.entity.*;
import com.zbeboy.blog.domain.repository.*;
import com.zbeboy.blog.service.UsersService;
import com.zbeboy.blog.util.MD5Util;
import com.zbeboy.blog.vo.PostsVo;
import com.zbeboy.blog.vo.RegistVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/4.
 */
@Controller
public class MainController {

    private final BlogSimpleContentRepository blogSimpleContentRepository;

    private final ArchivesRepository archivesRepository;

    private final BlogContentTypeRepository blogContentTypeRepository;

    private final UsersRepository usersRepository;

    private final AuthoritiesRepository authoritiesRepository;

    @Resource
    private UsersService usersService;

    @Autowired
    public MainController(BlogSimpleContentRepository blogSimpleContentRepository, ArchivesRepository archivesRepository,
                          BlogContentTypeRepository blogContentTypeRepository, UsersRepository usersRepository,
                          AuthoritiesRepository authoritiesRepository) {
        this.blogSimpleContentRepository = blogSimpleContentRepository;
        this.archivesRepository = archivesRepository;
        this.blogContentTypeRepository = blogContentTypeRepository;
        this.usersRepository = usersRepository;
        this.authoritiesRepository = authoritiesRepository;
    }

    @RequestMapping(value = "/")
    public String home(ModelMap modelMap,HttpServletRequest request) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Page<BlogSimpleContentEntity> blogSimpleContentEntities = blogSimpleContentRepository.findAll(new PageRequest(0, 3, sort));
        List<PostsVo> postsVos = new ArrayList<>();
        for (BlogSimpleContentEntity blogSimpleContentEntity : blogSimpleContentEntities) {
            PostsVo postsVo = new PostsVo();
            postsVo.setId(blogSimpleContentEntity.getId());
            postsVo.setArticle_title(blogSimpleContentEntity.getBlogTitle());
            postsVos.add(postsVo);
        }

        Page<ArchivesEntity> archivesEntityPage = archivesRepository.findAll(new PageRequest(0, 3, sort));

        Page<BlogContentTypeEntity> blogContentTypeEntityPage = blogContentTypeRepository.findAll(new PageRequest(0, 3, sort));
        modelMap.addAttribute("posts", postsVos);
        modelMap.addAttribute("archives", archivesEntityPage);
        modelMap.addAttribute("type", blogContentTypeEntityPage);

        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrfToken != null) {
            modelMap.addAttribute("_csrf", csrfToken);
        }

        if(!StringUtils.isEmpty(usersService.getUserName())){
            modelMap.addAttribute("is_login",true);
        } else {
            modelMap.addAttribute("is_login",false);
        }

        return "index";
    }

    @RequestMapping(value = "/blog")
    public String blog(ModelMap modelMap,HttpServletRequest request) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrfToken != null) {
            modelMap.addAttribute("_csrf", csrfToken);
        }

        if(!StringUtils.isEmpty(usersService.getUserName())){
            modelMap.addAttribute("is_login",true);
        } else {
            modelMap.addAttribute("is_login",false);
        }

        return "full-width";
    }

    @RequestMapping(value = "/about")
    public String about(ModelMap modelMap,HttpServletRequest request) {
        if(!StringUtils.isEmpty(usersService.getUserName())){
            modelMap.addAttribute("is_login",true);
        } else {
            modelMap.addAttribute("is_login",false);
        }

        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrfToken != null) {
            modelMap.addAttribute("_csrf", csrfToken);
        }
        return "about";
    }

    @RequestMapping(value = "/contact")
    public String contact(ModelMap modelMap,HttpServletRequest request) {
        if(!StringUtils.isEmpty(usersService.getUserName())){
            modelMap.addAttribute("is_login",true);
        } else {
            modelMap.addAttribute("is_login",false);
        }

        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrfToken != null) {
            modelMap.addAttribute("_csrf", csrfToken);
        }

        modelMap.addAttribute("contactError",false);
        modelMap.addAttribute("errorMsg","");

        return "contact";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap modelMap, HttpServletRequest request) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrfToken != null) {
            modelMap.addAttribute("_csrf", csrfToken);
        }
        return "login";
    }

    @RequestMapping(value = "/loginError", method = RequestMethod.GET)
    public String loginError(ModelMap modelMap, HttpServletRequest request) {
        modelMap.addAttribute("loginError", true);
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrfToken != null) {
            modelMap.addAttribute("_csrf", csrfToken);
        }
        return "login";
    }

    @RequestMapping(value = "/checkEmail")
    @ResponseBody
    public Map<String, Object> checkEmail(@RequestParam("email") String email) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.hasLength(email)) {
            UsersEntity usersEntity = usersRepository.findOne(email);
            if (StringUtils.isEmpty(usersEntity)) {
                map.put("state", true);
            } else {
                map.put("state", false);
                map.put("msg", "邮箱已存在！");
            }
        } else {
            map.put("state", false);
            map.put("msg", "参数为空！");
        }
        return map;
    }

    @RequestMapping(value = "/regist")
    @ResponseBody
    public Map<String, Object> regist(@Valid RegistVo registVo, BindingResult bindingResult) {
        Map<String, Object> map = new HashMap<>();
        if (bindingResult.hasErrors()) {
            map.put("state", false);
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
        } else {
            if (registVo.getRegist_password().equals(registVo.getConfirm_password())) {
                UsersEntity usersEntity = new UsersEntity();
                usersEntity.setUsername(StringUtils.trimWhitespace(registVo.getEmail()));
                usersEntity.setPassword(MD5Util.md5(StringUtils.trimWhitespace(registVo.getConfirm_password())));
                usersEntity.setEnabled(true);
                usersRepository.save(usersEntity);
                AuthoritiesEntity authoritiesEntity = new AuthoritiesEntity();
                authoritiesEntity.setUsername(StringUtils.trimWhitespace(registVo.getEmail()));
                authoritiesEntity.setAuthority("ROLE_USER");
                authoritiesRepository.save(authoritiesEntity);
                map.put("state", true);
                map.put("msg", "注册成功！");
            } else {
                map.put("state", false);
                map.put("msg", "请确认密码！");
            }
        }
        return map;
    }

    @RequestMapping("/user/home")
    public String userHome(ModelMap modelMap,HttpServletRequest request){
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Page<BlogSimpleContentEntity> blogSimpleContentEntities = blogSimpleContentRepository.findAll(new PageRequest(0, 3, sort));
        List<PostsVo> postsVos = new ArrayList<>();
        for (BlogSimpleContentEntity blogSimpleContentEntity : blogSimpleContentEntities) {
            PostsVo postsVo = new PostsVo();
            postsVo.setArticle_title(blogSimpleContentEntity.getBlogTitle());
            postsVo.setId(blogSimpleContentEntity.getId());
            postsVos.add(postsVo);
        }

        Page<ArchivesEntity> archivesEntityPage = archivesRepository.findAll(new PageRequest(0, 3, sort));

        Page<BlogContentTypeEntity> blogContentTypeEntityPage = blogContentTypeRepository.findAll(new PageRequest(0, 3, sort));
        modelMap.addAttribute("posts", postsVos);
        modelMap.addAttribute("archives", archivesEntityPage);
        modelMap.addAttribute("type", blogContentTypeEntityPage);
        if(!StringUtils.isEmpty(usersService.getUserName())){
            modelMap.addAttribute("is_login",true);
        } else {
            modelMap.addAttribute("is_login",false);
        }

        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrfToken != null) {
            modelMap.addAttribute("_csrf", csrfToken);
        }
        return "/user/index";
    }

    @RequestMapping("/user/sendBlog")
    public String sendBlog(ModelMap modelMap,HttpServletRequest request){
        List<BlogContentTypeEntity> blogContentTypeEntities = blogContentTypeRepository.findAll();
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrfToken != null) {
            modelMap.addAttribute("_csrf", csrfToken);
        }
        modelMap.addAttribute("type", blogContentTypeEntities);

        if(!StringUtils.isEmpty(usersService.getUserName())){
            modelMap.addAttribute("is_login",true);
        } else {
            modelMap.addAttribute("is_login",false);
        }
        return "/user/send-blog";
    }
}
