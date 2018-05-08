package com.q18idc.sbms.demo.mymapper;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import java.util.Set;

/**
 * @author q18idc.com QQ993143799
 * @date 2018/4/6 18:43
 */
public class DeleteListMapperSpecialProvider extends MapperTemplate {
    public DeleteListMapperSpecialProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 批量删除
     * @return
     */
    public String deleteList(MappedStatement ms){
        final Class<?> entityClass = getEntityClass(ms);
        //开始拼sql
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.deleteFromTable(entityClass, tableName(entityClass)));
        sql.append(" WHERE ");
        Set<EntityColumn> pkColumns1 = EntityHelper.getPKColumns(entityClass);
        for (EntityColumn entityColumn : pkColumns1) {
            sql.append(entityColumn.getColumn());
        }
        sql.append(" IN( ");
        sql.append("<foreach collection=\"list\" item=\"record\" separator=\",\" >");
        sql.append("<trim suffixOverrides=\",\">");
        Set<EntityColumn> pkColumns = EntityHelper.getPKColumns(entityClass);
        for (EntityColumn pkColumn : pkColumns) {
            sql.append(pkColumn.getColumnHolder("record"));
        }
        sql.append(",");
        sql.append("</trim>");
        sql.append("</foreach>");
        sql.append(")");
        return sql.toString();
    }
}
