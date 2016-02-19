package com.zbeboy.blog.web;

import com.zbeboy.blog.domain.repository.AuthoritiesRepository;
import com.zbeboy.blog.hello.Greeting;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on 2016/2/4.
 */
@RestController
public class GreetingController {

    private static final String template = "Hello,%s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name",defaultValue = "World") String name){
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }
}
