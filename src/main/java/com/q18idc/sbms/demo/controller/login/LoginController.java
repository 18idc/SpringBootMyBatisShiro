package com.q18idc.sbms.demo.controller.login;

import com.alibaba.fastjson.JSON;
import com.q18idc.sbms.demo.pojo.JsonResult;
import com.q18idc.sbms.demo.utils.VerifyCodeUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 登录控制器
 * @author q18idc.com QQ993143799
 * @date 2018/5/3 16:50
 */
@Controller
@RequestMapping(value = {"/login"})
public class LoginController {

    /**
     * 登录处理
     * @return
     */
    @RequestMapping(value = {"login.html"},method = {RequestMethod.GET})
    public String login1(HttpServletRequest request,HttpServletResponse response){
        return "login/login";
    }
    @RequestMapping(value = {"login.html"},method = {RequestMethod.POST})
    public void login(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String  shiroLoginFailure = (String) request.getAttribute("shiroLoginFailure");
        System.err.println("==============="+shiroLoginFailure);
        JsonResult json = new JsonResult();
        if(shiroLoginFailure!=null){
            if("randomCodeError".equals(shiroLoginFailure)){
                json.setFlag(false);
                json.setMsg("验证码错误");
                PrintWriter out = response.getWriter();
                out.print(JSON.toJSONString(json));
                out.close();
            }else if(UnknownAccountException.class.getName().equals(shiroLoginFailure)){
                json.setFlag(false);
                json.setMsg("账号或密码错误");
                PrintWriter out = response.getWriter();
                out.print(JSON.toJSONString(json));
                out.close();
            }else if(IncorrectCredentialsException.class.getName().equals(shiroLoginFailure)){
                json.setFlag(false);
                json.setMsg("账号或密码错误");
                PrintWriter out = response.getWriter();
                out.print(JSON.toJSONString(json));
                out.close();
            }

        }

    }

    /**
     * 验证码
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "code.jpg")
    public void verificationCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        //生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);

//        //存入会话session
//        HttpSession session = request.getSession(true);
        Session session = SecurityUtils.getSubject().getSession();
//        //删除以前的
        session.removeAttribute("verCode");
        session.setAttribute("verCode", verifyCode.toLowerCase());

        //生成图片
        int w = 100, h = 30;
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
    }

}
