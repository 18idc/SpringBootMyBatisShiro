package com.q18idc.sbms.demo.mapper;

import com.q18idc.sbms.demo.entity.Role;
import com.q18idc.sbms.demo.utils.MyMapper;
import org.apache.ibatis.annotations.CacheNamespaceRef;
import org.springframework.stereotype.Repository;

@Repository
@CacheNamespaceRef(RoleMapper.class)
public interface RoleMapper extends MyMapper<Role> {
}