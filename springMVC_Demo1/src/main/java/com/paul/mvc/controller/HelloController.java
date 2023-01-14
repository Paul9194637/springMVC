package com.paul.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ClassName : {Name}
 * Description :
 * Author : Paul Lin
 * Create : 2023/1/13$
 * Version :
 */
@Controller
public class HelloController {

    // "/" => /WEB-INF/templates/
    // 設置request對應, "/" 即代表空 http://localhost:8080/xxx/  http://localhost:80/xxx/
    // 若 value = "/" 則可省略
    @RequestMapping("/")
    public String index() {
        // return View (html) 名稱
        return "index";
    }
}
