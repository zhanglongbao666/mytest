package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

/**
 * 包名:com.itheima.health.service
 *
 * @author ZhangLongBao
 * 日期2021-01-05  17:29
 */
public interface CheckItemService {
    List<CheckItem> findAll();
    void add(CheckItem checkItem);
    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);
    void deleteById(Integer id);
    CheckItem findById(int id);
    void update(CheckItem checkItem);
}
