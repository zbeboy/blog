package com.zbeboy.blog.interceptor;

import com.zbeboy.blog.service.UsersService;
import com.zbeboy.blog.service.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2016/3/18.
 */
public class SpringMVCInterceptor implements WebRequestInterceptor {

    /**
     * 在请求处理之前执行，该方法主要是用于准备资源数据的，然后可以把它们当做请求属性放到WebRequest中
     */
    @Override
    public void preHandle(WebRequest webRequest) throws Exception {
      /*  System.out.println("AllInterceptor...............................");
        webRequest.setAttribute("request", "request", WebRequest.SCOPE_REQUEST);//这个是放到request范围内的，所以只能在当前请求中的request中获取到
        webRequest.setAttribute("session", "session", WebRequest.SCOPE_SESSION);//这个是放到session范围内的，如果环境允许的话它只能在局部的隔离的会话中访问，否则就是在普通的当前会话中可以访问
        webRequest.setAttribute("globalSession", "globalSession", WebRequest.SCOPE_GLOBAL_SESSION);//如果环境允许的话，它能在全局共享的会话中访问，否则就是在普通的当前会话中访问
        */
    }

    /**
     * 该方法将在Controller执行之后，返回视图之前执行，ModelMap表示请求Controller处理之后返回的Model对象，所以可以在
     * 这个方法中修改ModelMap的属性，从而达到改变返回的模型的效果。
     */
    @Override
    public void postHandle(WebRequest webRequest, ModelMap modelMap) throws Exception {
        if(!StringUtils.isEmpty(modelMap)){
            CsrfToken csrfToken = (CsrfToken) webRequest.getAttribute(CsrfToken.class.getName(),WebRequest.SCOPE_REQUEST);
            if (!StringUtils.isEmpty(csrfToken)) {
                modelMap.addAttribute("_csrf", csrfToken);
            }
            modelMap.addAttribute("is_login", StringUtils.isEmpty(new UsersServiceImpl().getUserName())?false:true);
        }
    }

    /**
     * 该方法将在整个请求完成之后，也就是说在视图渲染之后进行调用，主要用于进行一些资源的释放
     */
    @Override
    public void afterCompletion(WebRequest webRequest, Exception e) throws Exception {

    }
}
