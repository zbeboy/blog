package com.zbeboy.blog;

import com.zbeboy.blog.interceptor.SpringMVCInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.sql.DataSource;
import java.util.Locale;

/**
 * Created by Administrator on 2016/2/4.
 */
@EnableAutoConfiguration
@ComponentScan
public class Application extends SpringBootServletInitializer {

    /**
     * 生成war包时需要
     */
    /*@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }*/

    @Bean
    public ApplicationSecurity applicationSecurity() {
        return new ApplicationSecurity();
    }

    @Bean
    public static Md5PasswordEncoder md5() {
        Md5PasswordEncoder md5 = new Md5PasswordEncoder();
        md5.setEncodeHashAsBase64(false);
        return md5;
    }

    @Bean
    public WebConfig webConfig(){
        return  new WebConfig();
    }

    @Bean
    public static JdbcTokenRepositoryImpl jdbcTokenRepository(DataSource dataSource) {
        JdbcTokenRepositoryImpl j = new JdbcTokenRepositoryImpl();
        j.setDataSource(dataSource);
        return j;
    }

    @Bean
    public LocaleResolver localeResolver(){
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.US);
        return sessionLocaleResolver;
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setCacheSeconds(3600);
        return messageSource;
    }

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(Application.class).run(args);
        /*SpringApplication.run(Application.class, args);//生成war包时需要*/
    }

    protected static class WebConfig extends WebMvcConfigurerAdapter{
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addWebRequestInterceptor(new SpringMVCInterceptor()).addPathPatterns("/**");
        }
    }

    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {

        @Autowired
        private DataSource dataSource;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().antMatchers("/css/**", "/js/**", "/fonts/**", "/images/**", "/files/**", "/i18n/**","/").permitAll()
                    .and().formLogin().loginPage("/login").defaultSuccessUrl("/", true)
                    .failureUrl("/loginError").permitAll().and().sessionManagement().invalidSessionUrl("/login")
                    .and().logout().logoutSuccessUrl("/").permitAll().invalidateHttpSession(true)
                    .and().authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
                    .and().authorizeRequests().antMatchers("/user/**").hasAnyRole("ADMIN,USER");
        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(md5()).and().eraseCredentials(false);
        }
    }
}
