package com.q18idc.sbms.demo.pojo;

import com.q18idc.sbms.demo.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crazycake.shiro.AuthCachePrincipal;

import java.io.Serializable;
import java.util.List;

/**
 * @author q18idc.com QQ993143799
 * @date 2018/5/4 17:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiveUser implements Serializable,AuthCachePrincipal {
    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 用户名 用户账号
     */
    private String username;

    /**
     * 用户具有权限的菜单
     */
    private List<Permission> menuList;

    @Override
    public String getAuthCacheKey() {
        return getUsername();
    }
}
