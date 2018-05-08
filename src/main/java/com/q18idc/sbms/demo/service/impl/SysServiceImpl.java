package com.q18idc.sbms.demo.service.impl;

import com.q18idc.sbms.demo.entity.Permission;
import com.q18idc.sbms.demo.mapper.PermissionMapper;
import com.q18idc.sbms.demo.service.SysService;
import com.q18idc.sbms.demo.utils.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author q18idc.com QQ993143799
 * @date 2018/5/7 22:50
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysServiceImpl implements SysService {

    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 根据用户账号获取权限列表
     * @param username 用户账号
     * @return
     */
    @Override
    public List<Permission> findPermissionListByUserName(String username) {
        if(MyUtils.isNotEmpty(username)){
            List<Permission> permissionList = permissionMapper.getUserPermissionListByUserName(username);
            if(permissionList!=null){
                if(permissionList.size()>0){
                    return permissionList;
                }
            }
        }
        return null;
    }

    /**
     * 根据用户账号获取有权限的菜单
     * @param username 用户账号
     * @return
     */
    @Override
    public List<Permission> findMenuListByUserName(String username) {
        if(MyUtils.isNotEmpty(username)){
            List<Permission> menuList = permissionMapper.getUserPermissionMenuListByUserName(username);
            if (menuList!=null){
                if(menuList.size()>0){
                    return menuList;
                }
            }
        }
        return null;
    }
}
