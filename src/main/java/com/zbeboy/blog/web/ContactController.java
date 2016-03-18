package com.zbeboy.blog.web;

import com.zbeboy.blog.domain.entity.ContactEntity;
import com.zbeboy.blog.domain.repository.ContactRepository;
import com.zbeboy.blog.service.UsersService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by lenovo on 2016-02-20.
 */
@Controller
public class ContactController {

    private static Logger logger = Logger.getLogger(ContactController.class);

    private final ContactRepository contactRepository;

    @Resource
    private UsersService usersService;

    @Autowired
    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @RequestMapping("/saveContact")
    public String contact(ContactEntity contactEntity, ModelMap modelMap) {
        if (!StringUtils.isEmpty(contactEntity)) {
            contactRepository.save(contactEntity);
            modelMap.addAttribute("contactError", false);
            modelMap.addAttribute("errorMsg", "");
        } else {
            modelMap.addAttribute("contactError", true);
            modelMap.addAttribute("errorMsg", "参数异常！");
        }

        return "contact";
    }
}
