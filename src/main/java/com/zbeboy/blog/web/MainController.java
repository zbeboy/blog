package com.zbeboy.blog.web;

import com.zbeboy.blog.data.AjaxData;
import com.zbeboy.blog.domain.entity.*;
import com.zbeboy.blog.domain.repository.*;
import com.zbeboy.blog.geettest.GeetestConfig;
import com.zbeboy.blog.geettest.GeetestLib;
import com.zbeboy.blog.service.MailService;
import com.zbeboy.blog.util.MD5Util;
import com.zbeboy.blog.util.RandomUtil;
import com.zbeboy.blog.vo.PostsVo;
import com.zbeboy.blog.vo.RegistVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

/**
 * Created by Administrator on 2016/2/4.
 */
@Controller
public class MainController {

    private final Logger log = LoggerFactory.getLogger(MainController.class);

    private final BlogSimpleContentRepository blogSimpleContentRepository;

    private final ArchivesRepository archivesRepository;

    private final BlogContentTypeRepository blogContentTypeRepository;

    private final UsersRepository usersRepository;

    private final AuthoritiesRepository authoritiesRepository;

    @Resource
    private MailService mailService;

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

    /**
     * 主页
     *
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/")
    public String home(ModelMap modelMap) {
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

        return "index";
    }

    /**
     * blog
     *
     * @return
     */
    @RequestMapping(value = "/full")
    public String blog() {
        return "full-width";
    }

    /**
     * aout
     *
     * @return
     */
    @RequestMapping(value = "/about")
    public String about() {
        return "about";
    }

    /**
     * contact
     *
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/contact")
    public String contact(ModelMap modelMap) {
        modelMap.addAttribute("contactError", false);
        modelMap.addAttribute("errorMsg", "");
        return "contact";
    }

    /**
     * login
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    /**
     * 初始化极验
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/startCaptcha", method = RequestMethod.GET)
    @ResponseBody
    public String startCaptcha(HttpServletRequest request) {
        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getCaptcha_id(), GeetestConfig.getPrivate_key());

        String resStr = "{}";

        //自定义userid
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        String userid = csrfToken.toString();

        //进行验证预处理
        int gtServerStatus = gtSdk.preProcess(userid);

        //将服务器状态设置到session中
        request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
        //将userid设置到session中
        request.getSession().setAttribute("userid", userid);

        resStr = gtSdk.getResponseStr();

        return resStr;
    }

    /**
     * 检验验证码
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/verifyCaptcha", method = RequestMethod.POST)
    @ResponseBody
    public AjaxData verifyCaptcha(HttpServletRequest request) {
        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getCaptcha_id(), GeetestConfig.getPrivate_key());

        String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
        String validate = request.getParameter(GeetestLib.fn_geetest_validate);
        String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);

        //从session中获取gt-server状态
        int gt_server_status_code = (Integer) request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);

        //从session中获取userid
        String userid = (String) request.getSession().getAttribute("userid");

        int gtResult = 0;

        if (gt_server_status_code == 1) {
            //gt-server正常，向gt-server进行二次验证

            gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, userid);
            log.info("gtResult {}", gtResult);
        } else {
            // gt-server非正常情况下，进行failback模式验证
            log.debug("failback:use your own server captcha validate");
            gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
            log.info("gtResult {}", gtResult);
        }


        if (gtResult == 1) {
            // 验证成功
            return new AjaxData().success().msg(gtSdk.getVersionInfo());
        } else {
            // 验证失败
            return new AjaxData().failed().msg("验证码错误!");
        }
    }

    /**
     * 登录失败
     *
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/loginError", method = RequestMethod.GET)
    public String loginError(ModelMap modelMap) {
        modelMap.addAttribute("loginError", true);
        return "login";
    }

    /**
     * 检验email
     *
     * @param email
     * @return
     */
    @RequestMapping(value = "/checkEmail")
    @ResponseBody
    public Map<String, Object> checkEmail(@RequestParam("email") String email) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.hasLength(email)) {
            UsersEntity usersEntity = usersRepository.findOne(email);
            if (StringUtils.isEmpty(usersEntity)) {
                map.put("state", true);
                map.put("msg", "邮箱不存在!");
            } else {
                map.put("state", false);
                map.put("msg", "邮箱已存在!");
            }
        } else {
            map.put("state", false);
            map.put("msg", "参数为空!");
        }
        return map;
    }

    /**
     * 注册
     *
     * @param registVo
     * @param bindingResult
     * @param request
     * @return
     */
    @RequestMapping(value = "/regist")
    @ResponseBody
    public Map<String, Object> regist(@Valid RegistVo registVo, BindingResult bindingResult, HttpServletRequest request) {
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
                usersEntity.setLangKey("zh-CN");
                usersEntity.setActivationKey(RandomUtil.generateActivationKey());
                usersRepository.save(usersEntity);
                AuthoritiesEntity authoritiesEntity = new AuthoritiesEntity();
                authoritiesEntity.setUsername(StringUtils.trimWhitespace(registVo.getEmail()));
                authoritiesEntity.setAuthority("ROLE_USER");
                authoritiesRepository.save(authoritiesEntity);
                //baseUrl
                String path = request.getContextPath();
                String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
                mailService.sendActivationEmail(usersEntity, basePath);
                map.put("state", true);
                map.put("msg", "注册成功，激活邮件已发送到您的邮箱!");
            } else {
                map.put("state", false);
                map.put("msg", "请确认密码!");
            }
        }
        return map;
    }

    /**
     * 激活
     *
     * @param key
     * @param modelMap
     * @param request
     * @return
     */
    @RequestMapping("/activate")
    public String activate(@RequestParam("key") String key, ModelMap modelMap, HttpServletRequest request) {
        if (StringUtils.hasLength(key)) {
            UsersEntity usersEntity = usersRepository.findByActivationKey(key);
            if (!ObjectUtils.isEmpty(usersEntity)) {
                usersEntity.setActivation(true);
                usersRepository.save(usersEntity);
                modelMap.addAttribute("status", "您的账号: " + usersEntity.getUsername() + " 激活成功!");
                //baseUrl
                String path = request.getContextPath();
                String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
                mailService.sendCreationEmail(usersEntity, basePath);
            } else {
                modelMap.addAttribute("status", "激活失败，请返回登录页重新激活!");
            }
        } else {
            modelMap.addAttribute("status", "激活失败，请返回登录页重新激活!");
        }
        return "activation";
    }

    /**
     * 检验是否激活
     *
     * @param username
     * @return
     */
    @RequestMapping("/checkActivate")
    @ResponseBody
    public AjaxData checkActivate(@RequestParam("username") String username) {
        if (StringUtils.hasLength(username)) {
            UsersEntity usersEntity = usersRepository.findOne(username);
            if (!ObjectUtils.isEmpty(usersEntity)) {
                if (usersEntity.isActivation()) {
                    return new AjaxData().success();
                } else {
                    return new AjaxData().failed().msg("账号未激活");
                }
            } else {
                return new AjaxData().failed().msg("账号不存在!");
            }
        } else {
            return new AjaxData().failed().msg("参数异常!");
        }
    }

    /**
     * 重新激活
     *
     * @param username
     * @param request
     * @return
     */
    @RequestMapping("/againActivation")
    @ResponseBody
    public AjaxData againActivation(@RequestParam("username") String username, HttpServletRequest request) {
        if (StringUtils.hasLength(username)) {
            UsersEntity usersEntity = usersRepository.findOne(username);
            if (!ObjectUtils.isEmpty(usersEntity)) {
                usersEntity.setActivationKey(RandomUtil.generateActivationKey());
                //baseUrl
                String path = request.getContextPath();
                String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
                mailService.sendActivationEmail(usersEntity, basePath);
                usersRepository.save(usersEntity);
                return new AjaxData().success().msg("激活邮件已发送，请登录邮箱激活!");
            } else {
                return new AjaxData().failed().msg("账号不存在!");
            }
        } else {
            return new AjaxData().failed().msg("参数异常!");
        }
    }

    /**
     * 重置密码
     *
     * @return
     */
    @RequestMapping("/reset")
    public String reset() {
        return "reset";
    }

    /**
     * 重置
     *
     * @param username
     * @param request
     * @return
     */
    @RequestMapping("/resetPassword")
    @ResponseBody
    public AjaxData resetPassword(@RequestParam("username") String username, HttpServletRequest request) {
        if (StringUtils.hasLength(username)) {
            UsersEntity usersEntity = usersRepository.findOne(username);
            if (!ObjectUtils.isEmpty(usersEntity)) {
                if (usersEntity.isActivation()) {
                    usersEntity.setResetKey(RandomUtil.generateResetKey());
                    usersEntity.setResetDate(new Date());
                    //baseUrl
                    String path = request.getContextPath();
                    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
                    mailService.sendPasswordResetMail(usersEntity, basePath);
                    usersRepository.save(usersEntity);
                    return new AjaxData().success().msg("激活邮件已发送，请登录邮箱激活!");
                } else {
                    return new AjaxData().failed().msg("请先激活账号!");
                }
            } else {
                return new AjaxData().failed().msg("账号不存在!");
            }
        } else {
            return new AjaxData().failed().msg("参数异常!");
        }
    }

    /**
     * 完成重置
     *
     * @param key
     * @param modelMap
     * @return
     */
    @RequestMapping("/reset/finish")
    public String resetFinish(@RequestParam("key") String key, ModelMap modelMap) {
        if (StringUtils.hasLength(key)) {
            UsersEntity usersEntity = usersRepository.findByResetKey(key);
            if (!ObjectUtils.isEmpty(usersEntity)) {
                String password = RandomUtil.generatePassword();
                usersEntity.setPassword(MD5Util.md5(password));
                usersRepository.save(usersEntity);
                modelMap.addAttribute("status", "您的账号: " + usersEntity.getUsername() + " 已成功重置密码为: ");
                modelMap.addAttribute("password", password);
            } else {
                modelMap.addAttribute("status", "重置失败!");
                modelMap.addAttribute("password", "");
            }
        } else {
            modelMap.addAttribute("status", "参数异常，重置密码失败!");
            modelMap.addAttribute("password", "");
        }
        return "reset-finish";
    }

    /**
     * 发表博客
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/user/sendBlog")
    public String sendBlog(ModelMap modelMap) {
        List<BlogContentTypeEntity> blogContentTypeEntities = blogContentTypeRepository.findAll();
        modelMap.addAttribute("type", blogContentTypeEntities);

        return "/user/send-blog";
    }
}
