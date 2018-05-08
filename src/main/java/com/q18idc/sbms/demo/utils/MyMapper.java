package com.q18idc.sbms.demo.utils;

import com.q18idc.sbms.demo.mymapper.DeleteListMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author q18idc.com QQ993143799
 * @date 2018/4/4 0:54
 */
public interface MyMapper<T> extends Mapper<T>,MySqlMapper<T>,DeleteListMapper<T> {
}
