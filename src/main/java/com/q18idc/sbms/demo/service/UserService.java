package com.q18idc.sbms.demo.service;

import com.github.abel533.echarts.Option;
import com.q18idc.sbms.demo.entity.User;

/**
 * @author q18idc.com QQ993143799
 * @date 2018/5/7 22:46
 */
public interface UserService {
    /**
     * 根据用户名 用户账号 获取用户
     * @param username 用户名 用户账号
     * @return
     */
    User getUserByUserName(String username);

    /**
     * 获取性别统计图表
     * @return
     */
    public Option selectSexCount();

}
