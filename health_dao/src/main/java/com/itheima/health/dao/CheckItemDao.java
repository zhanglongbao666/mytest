package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckItem;
import java.util.List;

/**
 * 包名:com.itheima.health.dao
 *
 * @author ZhangLongBao
 * 日期2021-01-05  17:32
 */
public interface CheckItemDao {
    List<CheckItem> findAll();
    void add(CheckItem checkItem);
    PageResult<CheckItem> findPage();
    Page<CheckItem> findByCondition(String queryString);
    int findCountByCheckItemId(Integer id);
    void deleteById(Integer id);
    CheckItem findById(int id);
    void update(CheckItem checkItem);
}
