package com.zbeboy.blog.web;

import com.zbeboy.blog.domain.entity.ContactEntity;
import com.zbeboy.blog.domain.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lenovo on 2016-02-20.
 */
@Controller
public class ContactController {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @RequestMapping("/saveContact")
    public String contact(ContactEntity contactEntity, HttpServletRequest request, ModelMap modelMap) {
        if (!StringUtils.isEmpty(contactEntity)) {
            contactRepository.save(contactEntity);
            System.out.println("hahahh");
            modelMap.addAttribute("contactError", false);
            modelMap.addAttribute("errorMsg", "");
        } else {
            modelMap.addAttribute("contactError", true);
            modelMap.addAttribute("errorMsg", "参数异常！");
        }
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrfToken != null) {
            modelMap.addAttribute("_csrf", csrfToken);
        }
        return "contact";
    }
}
