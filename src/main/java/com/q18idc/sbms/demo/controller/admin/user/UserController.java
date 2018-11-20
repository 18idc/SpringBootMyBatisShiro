package com.q18idc.sbms.demo.controller.admin.user;

import com.github.abel533.echarts.Option;
import com.q18idc.sbms.demo.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 后台用户控制器
 * @author q18idc.com QQ993143799
 * @date 2018/5/7 23:07
 */
@Controller
@RequestMapping("/admin/user/")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("list.html")
    @RequiresPermissions("user:list")
    public String list(){
        return "admin/user/list";
    }

    @RequestMapping("disables.html")
    @RequiresPermissions("user:disables")
    public String disables(){
        return "admin/user/disables";
    }

    /**
     * 性别统计
     *
     * @return
     */
    @RequestMapping("sex")
    @ResponseBody
    public Option sexCount() {
        return userService.selectSexCount();
    }


}
