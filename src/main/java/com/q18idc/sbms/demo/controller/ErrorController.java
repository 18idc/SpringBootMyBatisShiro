package com.q18idc.sbms.demo.controller;

import com.alibaba.fastjson.JSON;
import com.q18idc.sbms.demo.pojo.JsonResult;
import com.q18idc.sbms.demo.utils.MyUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author q18idc.com QQ993143799
 * @date 2018/5/6 10:16
 */
@Controller
public class ErrorController {
    /**
     * 未授权处理
     * @return
     */
    @RequestMapping("refuse")
    public String refuse(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");

        boolean ajax = MyUtils.isAjax(request);
        if(ajax){
//            response.setContentType("application/json;charset=UTF-8");
            JsonResult json = new JsonResult();
            json.setFlag(false);
            json.setMsg("没有权限");
            PrintWriter out = response.getWriter();
            out.print(JSON.toJSONString(json));
            out.close();
        }
        return "refuse";
    }
}
