package com.zbeboy.blog.web;

import com.zbeboy.blog.domain.entity.ContactEntity;
import com.zbeboy.blog.domain.repository.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lenovo on 2016-02-20.
 */
@Controller
public class ContactController {

    private final Logger log = LoggerFactory.getLogger(ContactController.class);

    private final ContactRepository contactRepository;

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
