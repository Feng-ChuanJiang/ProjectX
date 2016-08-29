package com.cci.projectx.core;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;

/**
 * Created by 33303 on 2016/8/27.
 */
@Repository
public class ElasticSearchHelp<T> {
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;
    /**
     * 插入或者更新elasticsearch
     *
     * @param entity
     * @return
     */
    public String mergeES(T entity, String id) {
        // elasticsearchTemplate.createIndex(Education.class);
        IndexQuery indexQuery = new IndexQuery();
        indexQuery.setId(id);
        indexQuery.setObject(entity);
        elasticsearchTemplate.putMapping(entity.getClass());
        String str = elasticsearchTemplate.index(indexQuery);
        elasticsearchTemplate.refresh(entity.getClass(), true);
        return str;
    }

    /**
     * 删除elasticsearch数据
     *
     * @param entity
     * @return
     */
    public String deleteES(Class<T> entity, Object id) {
        elasticsearchTemplate.putMapping(entity);
        String str = elasticsearchTemplate.delete(entity, id.toString());
        elasticsearchTemplate.refresh(entity, true);
        return str;
    }

    /**
     * 查询elasticsearch数据
     *
     * @param entity
     * @return
     */
    public List<T> findESForList(T entity) {
        BoolQueryBuilder builder = getESSql(entity);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(builder).build();
        List<T> list = (List<T>) elasticsearchTemplate.queryForList(searchQuery, entity.getClass());
        return list;
    }

    /**
     * 拼接es查询sql
     * @param o
     * @return
     */
    private BoolQueryBuilder getESSql(T o) {
        BoolQueryBuilder builder = boolQuery();
        // 获取类中的所有定义字段
        Field[] fields = o.getClass().getDeclaredFields();
        // 循环遍历字段，获取字段对应的属性值
        for (Field field : fields) {
            // 如果不为空，设置可见性，然后返回
            field.setAccessible(true);
            try {
                // 设置字段可见，即可用get方法获取属性值。
                if (field.get(o) != null && !field.get(o).toString().equals("")) {
                    //判断是否是集合
                    if (!field.getType().isInstance(new ArrayList<>())) {
                        builder.must(QueryBuilders.queryString(field.get(o).toString()).defaultField(field.getName()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();

            }

        }
        return builder;

    }

}
