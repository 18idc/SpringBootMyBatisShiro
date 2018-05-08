package com.q18idc.sbms.demo.filter;

import com.alibaba.fastjson.JSON;
import com.q18idc.sbms.demo.pojo.JsonResult;
import com.q18idc.sbms.demo.utils.VerifyCodeUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 自定义 FormAuthenticationFilter  认证之前实现验证码效验
 * @author q18idc.com QQ993143799
 * @date 2018/5/3 20:32
 */
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //在这里进行验证码的校验

        //从session获取正确验证码
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

//        HttpSession session = httpServletRequest.getSession();
//        //取出session的验证码（正确的验证码）
//        String validateCode = (String) session.getAttribute("verCode");
        Session session = SecurityUtils.getSubject().getSession();
        String validateCode = (String) session.getAttribute("verCode");


        //取出页面的验证码
        //输入的验证和session中的验证进行对比
        String code = httpServletRequest.getParameter("yzm");
        if (code != null && validateCode != null && !code.toLowerCase().equals(validateCode.toLowerCase())) {
            //如果校验失败，将验证码错误失败信息，通过shiroLoginFailure设置到request中
            httpServletRequest.setAttribute("shiroLoginFailure", "randomCodeError");
            //清除验证码
            session.removeAttribute("verCode");
            //重新生成验证码
            session.setAttribute("verCode", VerifyCodeUtils.generateVerifyCode(4));
            //拒绝访问，不再校验账号和密码
            return true;
        }
        //清除验证码
        session.removeAttribute("verCode");
        //重新生成验证码
        session.setAttribute("verCode", VerifyCodeUtils.generateVerifyCode(4));
        return super.onAccessDenied(request, response);
    }

    /**
     * 登录成功
     * @param token
     * @param subject
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest request1 = (HttpServletRequest) request;
        HttpServletResponse response1 = (HttpServletResponse) response;
        PrintWriter out = response1.getWriter();
        JsonResult jsonResult = new JsonResult();
        jsonResult.setFlag(true);
        jsonResult.setMsg("登录成功");
        out.print(JSON.toJSONString(jsonResult));
        out.close();
//        return super.onLoginSuccess(token, subject, request, response);
        return true;
    }

}
