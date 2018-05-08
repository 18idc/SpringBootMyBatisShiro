package com.q18idc.sbms.demo.realm;

import com.q18idc.sbms.demo.entity.Permission;
import com.q18idc.sbms.demo.entity.User;
import com.q18idc.sbms.demo.pojo.ActiveUser;
import com.q18idc.sbms.demo.service.SysService;
import com.q18idc.sbms.demo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author q18idc.com QQ993143799
 * @date 2018/5/3 15:49
 */
public class MyRealm extends AuthorizingRealm  {
    @Autowired
    private UserService userService;

    @Autowired
    private SysService sysService;

    /**
     * 设置Realm的名称
     *
     * @param name
     */
    @Override
    public void setName(String name) {
        super.setName(MyRealm.class.getName());
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //token是用户输入的
        //第一步从token中取出用户的身份信息
        String username = (String) token.getPrincipal();

        //第二步 根据用户输入的账号  username 去数据库中查询
        User user = userService.getUserByUserName(username);

        //如果查询结果返回null  直接返回null
        if(user == null){
            return null;
        }
        //密码 从数据库中查询获取
        String pass = user.getPassword();

        //盐  这个是从数据库中获取的
        ByteSource salt = ByteSource.Util.bytes(user.getSalt());

        // 如果查询到返回认证信息AuthenticationInfo
        //activeUser就是用户身份信息
        ActiveUser activeUser = new ActiveUser();
        activeUser.setId(user.getId());
        activeUser.setUsername(user.getUsername());

        //取出菜单和权限设置到activeuser中
        List<Permission> menuList = sysService.findMenuListByUserName(user.getUsername());
        activeUser.setMenuList(menuList);

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activeUser, pass, salt, getName());

        return info;
    }

    /**
     * 身份授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //从principals中获取主身份信息
        //将getPrimaryPrincipal方法返回值转为真实的身份类型（在上边的doGetAuthenticationInfo认证通过填充到SimpleAuthenticationInfo中的身份类型）
        ActiveUser activeUser = (ActiveUser) principals.getPrimaryPrincipal();

        //根据身份信息获取权限信息
        List<Permission> permissionList = sysService.findPermissionListByUserName(activeUser.getUsername());
        if(permissionList!=null){
            //定义的权限集合
            List<String> permissions = new ArrayList<>();
            if (permissionList != null) {
                for (Permission permission : permissionList) {
                    //将数据库中的权限标识符放入集合
                    permissions.add(permission.getPercode());
                }
            }

            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            //将查询出来的权限信息放进info
            info.addStringPermissions(permissions);

            return info;
        }
        return null;
    }


    /**
     * 清空缓存
     */
    public void clearCached() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }


}
