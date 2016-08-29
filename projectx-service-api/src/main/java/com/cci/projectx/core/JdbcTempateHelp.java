package com.cci.projectx.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 33303 on 2016/8/27.
 */
@Repository
public class JdbcTempateHelp<T> {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    /**
     * 插入实体
     *
     * @param entity
     * @return
     */
    public int add(T entity) {
        if (!simpleJdbcInsert.isCompiled()) {
            String tableName = toUnderline(entity.getClass().getSimpleName()).toLowerCase();
            simpleJdbcInsert.setTableName(tableName.toLowerCase());
        }
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(entity);
        return simpleJdbcInsert.execute(namedParameters);
    }

    /**
     * 插入并返回自增主键
     *
     * @param entity
     * @return
     */
    public long addAndReturnId(T entity) {
        String tableName = toUnderline(entity.getClass().getSimpleName()).toLowerCase();
        if (!simpleJdbcInsert.isCompiled()) {
            simpleJdbcInsert.setTableName(tableName.toLowerCase());
            simpleJdbcInsert.setGeneratedKeyName("id");
        }
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(entity);
        Number newId = simpleJdbcInsert.executeAndReturnKey(namedParameters);
        return newId.longValue();
    }



    /**
     * 分页查找
     *
     * @param sql
     * @param args
     * @return
     */
    public Page<T> findBySqlForPage(String sql, Object[] args, Pageable pageable) {
        return findBySqlForPage(sql, args, pageable, null);
    }
    /**
     * 分页查找对象
     *
     * @param sql
     * @param args
     * @return
     */
    public Page<T> findBySqlForPage(String sql, Object[] args, Pageable pageable, Class<T> tClass) {
        Long total = getCount(sql, args);
        sql = sql + getPageLimit(pageable);
        Page page = null;
        //装入结果集
        if (tClass == null) {
            List<T> datas = (List<T>) jdbcTemplate.queryForList(sql, args);
            page = new PageImpl<>(datas, pageable, total);
        } else {
            BeanPropertyRowMapper<?> argTypes = new BeanPropertyRowMapper<>(tClass);
            List<T> datas = ((List<T>) jdbcTemplate.query(sql, argTypes, args));
            page = new PageImpl<>(datas, pageable, total);
        }
        return page;
    }


    /**
     * 自定义sql获取总数
     *
     * @param sql
     * @param args
     * @return
     */
    public Long getCount(String sql, Object[] args) {
        sql = sql.toLowerCase();
        String e = sql.substring(sql.indexOf("from"));
        String c = "select count(*) " + e;
        return namedParameterJdbcTemplate.getJdbcOperations().queryForObject(c, args, Long.class);
    }

    /**
     * 为自定义的sql添加Limit
     *
     * @param page
     * @return
     */
    public String getPageLimit(Pageable page) {
        StringBuilder sb = new StringBuilder(" limit ");
        sb.append(page.getOffset());
        sb.append(",");
        sb.append(page.getPageSize());
        return sb.toString();
    }
    /**
     * 转下滑线
     *
     * @param str
     * @return
     */
    private String toUnderline(String str) {
        char[] cstr = str.toCharArray();
        String sSplice = "";
        for (int i = 0; i < cstr.length; i++) {
            char c = cstr[i];
            if (Character.isUpperCase(c) && i != 0) {
                char to_lower = c += 32;
                sSplice += "_" + to_lower;
            } else {
                sSplice += c;
            }

        }
        return sSplice;
    }

}
