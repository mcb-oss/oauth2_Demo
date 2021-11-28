package com.mcb.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Mcb
 * @Date: 2021/11/28 18:17
 * @Description: 资源服务器临时资源
 */
@RestController
public class CmsController {

    @RequestMapping("/index")
    public String index(){
        return "idnex";
    }

    @RequestMapping("/authentication")
    public Object getCurrentUser(Authentication authentication){
        return authentication;
    }
}
