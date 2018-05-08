package com.q18idc.sbms.demo.controller.admin;

import com.q18idc.sbms.demo.entity.Permission;
import com.q18idc.sbms.demo.service.SysService;
import com.q18idc.sbms.demo.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author q18idc.com QQ993143799
 * @date 2018/5/6 10:59
 */
@Controller
@RequestMapping("/admin/menu/")
public class MenuController{
    @Autowired
    private SysService sysService;

    /**
     * 根据登录的用户获取菜单
     * @return
     */
    @RequestMapping(value = "list")
    @ResponseBody
    public List<Map<String ,Object>>  list(){
        List<Map<String ,Object>> data = new ArrayList<>();

        String username = MyUtils.getLoginUserName();
        List<Permission> menuList = sysService.findMenuListByUserName(username);

        Map<String ,Object> map = new HashMap<>();
        map.put("id", "menu");
        map.put("text", "后台管理");
        data.add(map);

        String[] icons = {"icon-note","icon-reports"};
        int c=0;

        if (menuList!=null){
            for (Permission permission : menuList) {
                Map<String ,Object> map1 = new HashMap<>();
                map1.put("id", permission.getId());
                map1.put("text", permission.getName());
                map1.put("url", permission.getUrl());
                map1.put("pid", "menu");
                map1.put("iconCls", icons[c]);
                map1.put("iconPosition", "top");
                data.add(map1);
                c++;
            }
        }

        return data;
    }

}
