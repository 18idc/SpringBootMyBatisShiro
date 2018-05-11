package com.q18idc.sbms.demo.controller.admin;

import com.q18idc.sbms.demo.utils.MyUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * 后台控制器
 * @author q18idc.com QQ993143799
 * @date 2018/5/6 10:28
 */
@Controller
@RequestMapping("/admin")
public class AdminController{
    /**
     * 后台首页
     * @return
     */
    @RequestMapping(value = "index.html")
    public String index(Map<String, Object> map){
        map.put("username", MyUtils.getLoginUserName());
        return "admin/index";
    }

    /**
     * 后台首页 主页
     * @return
     */
    @RequestMapping(value = "main.html")
    public String main(){
        return "admin/main";
    }

}
