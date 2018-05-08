package com.q18idc.sbms.demo.service.impl;

import com.github.abel533.echarts.Legend;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.Title;
import com.github.abel533.echarts.Tooltip;
import com.github.abel533.echarts.code.Orient;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.series.Pie;
import com.github.abel533.echarts.style.ItemStyle;
import com.github.abel533.echarts.style.itemstyle.Emphasis;
import com.q18idc.sbms.demo.entity.User;
import com.q18idc.sbms.demo.mapper.UserMapper;
import com.q18idc.sbms.demo.service.UserService;
import com.q18idc.sbms.demo.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author q18idc.com QQ993143799
 * @date 2018/5/7 22:51
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 获取性别统计图表
     * @return
     */
    @Override
    public Option selectSexCount() {
        Example example = new Example(User.class);
        Example.Criteria exampleCriteria = example.createCriteria();
        exampleCriteria.andEqualTo("sex", "男");
        int man = userMapper.selectCountByExample(example);

        Example example1 = new Example(User.class);
        Example.Criteria criteria = example1.createCriteria();
        criteria.andEqualTo("sex", "女");
        int woman = userMapper.selectCountByExample(example1);

        //数据库查询获取统计数据
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        map1.put("name", "男");
        map1.put("value", man);
        map2.put("name", "女");
        map2.put("value", woman);
        list.add(map1);
        list.add(map2);

        Option option = new Option();

        Title title = new Title();
        title.setText("用户性别统计");
        title.setSubtext("真实数据");
        title.setX("center");
        option.setTitle(title);

        Tooltip tooltip = new Tooltip();
        tooltip.setTrigger(Trigger.item);
        tooltip.formatter("{a} <br/>{b} : {c} ({d}%)");
        option.setTooltip(tooltip);

        Legend legend = new Legend();
        legend.setOrient(Orient.vertical);
        legend.setLeft("left");
        List<String> dataList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            dataList.add((String) map.get("name"));
        }
        legend.setData(dataList);
        option.setLegend(legend);

        Pie pie = new Pie("性别统计");
        pie.setRadius("55%");
        String[] strings = {"50%", "60%"};
        pie.setCenter(strings);
        pie.setData(list);

        ItemStyle itemStyle = new ItemStyle();
        Emphasis emphasis = new Emphasis();
        emphasis.setShadowBlur(10);
        emphasis.setShadowOffsetX(10);
        emphasis.setShadowColor("rgba(0, 0, 0, 0.5)");
        itemStyle.setEmphasis(emphasis);
        pie.setItemStyle(itemStyle);

        option.series(pie);

        return option;
    }

    /**
     * 根据用户名 用户账号 获取用户
     * @param username 用户名 用户账号
     * @return
     */
    @Override
    public User getUserByUserName(String username) {
        //判断提交的用户名是否为空  不为空则继续执行
        if(MyUtils.isNotEmpty(username)){
            Example example = new Example(User.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("username", username);
            User user = userMapper.selectOneByExample(example);
            if(user!=null){
                return user;
            }
        }
        return null;
    }
}
