package com.q18idc.sbms.demo.service;

import com.q18idc.sbms.demo.entity.Permission;

import java.util.List;

/**
 * @author q18idc.com QQ993143799
 * @date 2018/5/7 22:46
 */
public interface SysService {
    /**
     * 根据用户账号获取有权限的菜单
     * @param username 用户账号
     * @return
     */
    List<Permission> findMenuListByUserName(String username);

    /**
     * 根据用户账号获取权限列表
     * @param username 用户账号
     * @return
     */
    List<Permission> findPermissionListByUserName(String username);
}
