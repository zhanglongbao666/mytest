package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 包名:com.itheima.health.dao
 *
 * @author ZhangLongBao
 * 日期2021-01-07  15:37
 */
public interface CheckGroupDao {
    void add(CheckGroup checkGroup);
    void addCheckGroupCheckItem(@Param("checkGroupId") Integer checkGroupId, @Param("checkitemId") Integer checkitemId);
    Page<CheckGroup> findByCondition(String queryString);
    CheckGroup findById(Integer checkGroupId);
    List<Integer> findCheckItemIdsByCheckGroupId(Integer checkGroupId);
    void update(CheckGroup checkGroup);
    void deleteCheckGroupCheckItem(Integer id);
    int findSetmealCountByCheckGroupId(int id);
    void deleteById(int id);
    List<CheckGroup> findAll();
}
