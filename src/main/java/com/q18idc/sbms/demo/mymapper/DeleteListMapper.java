package com.q18idc.sbms.demo.mymapper;

import org.apache.ibatis.annotations.InsertProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;

import java.util.List;

/**
 * 批量删除
 * @author q18idc.com QQ993143799
 * @date 2018/4/6 18:40
 */
@RegisterMapper
public interface DeleteListMapper<T> {
    /**
     * 批量删除，支持批量删除的数据库可以使用
     *
     * @param recordList
     * @return
     */
    @InsertProvider(type = DeleteListMapperSpecialProvider.class, method = "dynamicSQL")
    int deleteList(List<T> recordList);
}
