<#include "../public/public.ftl" >

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>后台管理</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="${bash}/script/miniui/demo.css" rel="stylesheet" type="text/css" />
    <script src="${bash}/script/miniui/boot.js" type="text/javascript"></script>
    <style type="text/css">
        body{
            margin:0;padding:0;border:0;width:100%;height:100%;overflow:hidden;
        }
    </style>
</head>
<body >

<div id="layout1" class="mini-layout" style="width:100%;height:100%;">
    <div class="app-header" region="north" height="70" showSplit="false" showHeader="false">
        <h1 style="margin:0;padding:15px;cursor:default;font-family:微软雅黑,黑体,宋体;">后台管理 V1</h1>
        <div style="position:absolute;top:18px;right:10px;">
            欢迎：${username}
            <a class="mini-button mini-button-iconTop" iconCls="icon-reload" onclick="onReloadClick"  plain="true" >刷新</a>
            <a class="mini-button mini-button-iconTop" iconCls="icon-close" onclick="onLogoutClick"  plain="true" >退出</a>
        </div>
    </div>

    <div title="south" region="south" showSplit="false" showHeader="false" height="30" >
        <div style="line-height:28px;text-align:center;cursor:default">Copyright © 上海普加软件有限公司版权所有 </div>
    </div>

    <div title="center" region="center" style="border:0;" bodyStyle="overflow:hidden;">
        <!--Splitter-->
        <div class="mini-splitter" style="width:100%;height:100%;" borderStyle="border:0;">
            <div size="180" maxSize="250" minSize="100" showCollapseButton="true" style="border:0;">
                <!--OutlookMenu-->
                <div id="leftMenu" class="mini-outlookmenu" url="${bash}/admin/menu/list" onitemselect="onItemSelect"
                     idField="id" parentField="pid" textField="text" borderStyle="border:0" expandOnLoad="false"
                >
                </div>

            </div>

            <div showCollapseButton="false" style="border:0;">
                <!--Tabs-->
                <div id="mainTabs" class="mini-tabs" activeIndex="0" style="width:100%;height:100%;"
                     plain="false">
                    <div title="首页" url="${bash}/admin/main.html" ></div>

                </div>
            </div>
        </div>
    </div>


</div>

<script type="text/javascript">
    mini.parse();
    var tabs = mini.get("mainTabs");

    //关闭标签前的询问
    tabs.on("beforecloseclick", function (e) {
        e.cancel = true;
        mini.confirm('确定要关闭 '+e.tab.title+' 标签吗？','关闭标签？',function (action) {
            if (action == "ok"){
                tabs.removeTab(e.tab);
            }
        });
    });

    function onItemSelect(e) {
        var item = e.item;
        showTab(item);
    }
    
    function showTab(item) {
        var id = "tab$" + item.id;
        var tab = tabs.getTab(id);
        if (!tab){
            tab = {};
            tab._nodeid = item.id;
            tab.name = id;
            tab.title = item.text;
            tab.showCloseButton = true;
            tab.url = '${bash}'+item.url;
            tabs.addTab(tab);
        }
        tabs.activeTab(tab);
    }

    //刷新
    function onReloadClick() {
        var tabs = mini.get("mainTabs");
        //获取选中的tab
        var tab = tabs.getActiveTab();
        //刷新选中的tab
        tabs.reloadTab(tab);
    }

    //退出
    function onLogoutClick() {
        mini.confirm('确定要退出系统吗？','退出？',function (action) {
            if (action == "ok"){
                window.location='${bash}/login/logout.html';
            }
        });
    }

</script>

</body>
</html>