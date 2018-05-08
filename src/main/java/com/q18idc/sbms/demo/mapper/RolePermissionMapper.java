package com.q18idc.sbms.demo.mapper;

import com.q18idc.sbms.demo.entity.RolePermission;
import com.q18idc.sbms.demo.utils.MyMapper;
import org.apache.ibatis.annotations.CacheNamespaceRef;
import org.springframework.stereotype.Repository;

@Repository
@CacheNamespaceRef(RolePermissionMapper.class)
public interface RolePermissionMapper extends MyMapper<RolePermission> {
}