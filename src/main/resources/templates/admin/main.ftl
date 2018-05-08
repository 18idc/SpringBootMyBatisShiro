<#include "../public/public.ftl" >
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>性别统计</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link href="${bash}/script/miniui/demo.css" rel="stylesheet" type="text/css" />
    <script src="${bash}/script/miniui/boot.js" type="text/javascript"></script>
    <script src="${bash}/script/echarts/echarts.min.js" type="text/javascript"></script>
</head>
<body>

<div id="tongji" style="width: 500px;height: 500px;"></div>
<br>
<button class="mini-button" iconCls="icon-reload" onclick="echart('tongji')">刷新图表</button>
<button class="mini-button" iconCls="icon-zoomin" onclick="openechart()">打开图表</button>

<div id="win1" class="mini-window" title="用户性别统计" style="width:580px;height:450px;"
     showToolbar="false" showFooter="true" showModal="true" allowResize="true">
    <div class="ct" id="chart1" style="height:100%;width:100%;"></div>
</div>


<script>

    function openechart() {
        var win = mini.get("win1");
        win.show();
        //图表
        echart("chart1");
    }

    $(function () {
        echart("tongji");
    })

    /**
     * 获取统计图表信息
     */
    function echart(id) {
        //图表
        var myChart = echarts.init(document.getElementById(id));
        myChart.clear();
        myChart.showLoading({text: '正在努力的读取数据中...'});
        $.get('${bash}/admin/user/sex', function (data) {
            myChart.setOption(data);
            myChart.hideLoading();
        });
    }
</script>
</body>
</html>
