package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckGroup;

import java.util.List;

/**
 * 包名:com.itheima.health.service
 *
 * @author ZhangLongBao
 * 日期2021-01-07  15:36
 */
public interface CheckGroupService {
    void add(CheckGroup checkGroup, Integer[] checkitemIds);
    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);
    CheckGroup findById(Integer checkGroupId);
    List<Integer> findCheckItemIdsByCheckGroupId(Integer checkGroupId);
    void update(CheckGroup checkGroup, Integer[] checkitemIds);
    void deleteById(int id);
    List<CheckGroup> findAll();
}
