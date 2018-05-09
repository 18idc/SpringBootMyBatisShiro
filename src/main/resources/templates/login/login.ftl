<#include "../public/public.ftl" >
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>后台登录</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="${bash}/script/miniui/demo.css" rel="stylesheet" type="text/css" />

    <style type="text/css">
        body
        {
            width:100%;height:100%;margin:0;overflow:hidden;
        }
    </style>
    <script src="${bash}/script/miniui/boot.js" type="text/javascript"></script>

</head>
<body >
<div id="loginWindow" class="mini-window" title="用户登录" style="width:300px;height:260px;"
     showModal="true" showCloseButton="false"
>

    <div id="loginForm" style="padding:15px;padding-top:10px;">
        <table >
            <tr>
                <td style="width:60px;"><label for="username$text">帐号：</label></td>
                <td>
                    <input id="username" emptyText="请输入账号" name="username" required="true" requiredErrorText="账号不能为空" onvalidation="onUserNameValidation" class="mini-textbox" required="true" style="width:150px;"/>
                </td>
            </tr>
            <tr>
                <td style="width:60px;"><label for="pwd$text">密码：</label></td>
                <td>
                    <input id="pwd" emptyText="请输入密码" name="password" onvalidation="onPwdValidation" class="mini-password" requiredErrorText="密码不能为空" required="true" style="width:150px;"/>
                </td>
            </tr>
            <tr>
                <td style="width:60px;"><label for="pwd$text">验证码：</label></td>
                <td>
                    <input id="yzminput" name="yzm" emptyText="请输入验证码"  class="mini-textbox" requiredErrorText="验证码不能为空" required="true" style="width:150px;" onenter="onLoginClick"/>
                </td>
            </tr>
            <tr>
                <td style="width:60px;"></td>
                <td><img id="yzm" src="${bash}/login/code.jpg" alt="验证码"></td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <input name="rememberMe" class="mini-checkbox" text="记住登录？" />
                </td>
            </tr>
            <tr>
                <td></td>
                <td style="padding-top:5px;">
                    <a onclick="onLoginClick" class="mini-button" style="width:60px;">登录</a>
                    <a onclick="onResetClick" class="mini-button" style="width:60px;">重置</a>
                </td>
            </tr>
        </table>
    </div>

</div>

<script type="text/javascript">
    mini.parse();

    var loginWindow = mini.get("loginWindow");
    loginWindow.show();


    $("#yzm").on('click', function () {
        $('#yzm')[0].src = '${bash}/login/code.jpg?t_' + Math.random();
    });

    function onLoginClick(e) {
        var form = new mini.Form("#loginWindow");
        form.validate();
        if (form.isValid() == false) return;

        var login_load = mini.loading('登录中，请稍后','登录');

        //提交数据
        var data = form.getData();
       $.ajax({
           url: "${bash}/login/login.html",
            type: "post",
            data: data,
            success: function (res) {
               //隐藏登录加载框
                mini.hideMessageBox(login_load);

               res = mini.decode(res);
               if (res.flag){
                   loginWindow.hide();
                   mini.loading("登录成功，马上转到系统...", "登录成功");
                   setTimeout(function () {
                       window.location = "${bash}/admin/index.html";
                   }, 1500);
               }else {
                   //刷新验证码
                   $("#yzm").click();
                   data.yzm='';
                   form.setData(data);
                   mini.alert(res.msg, '信息');
               }
            }
        });


    }
    function onResetClick(e) {
        var form = new mini.Form("#loginWindow");
        form.clear();
    }

    function onUserNameValidation(e) {
        if (e.isValid) {
            if (e.value.length < 5) {
                e.errorText = "用户名不能少于5个字符";
                e.isValid = false;
            }
        }
    }
    function onPwdValidation(e) {
        if (e.isValid) {
            if (e.value.length < 5) {
                e.errorText = "密码不能少于5个字符";
                e.isValid = false;
            }
        }
    }
</script>

</body>
</html>