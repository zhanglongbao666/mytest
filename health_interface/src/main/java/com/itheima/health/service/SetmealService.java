package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.Setmeal;

import java.util.List;

/**
 * 包名:com.itheima.health.service
 *
 * @author ZhangLongBao
 * 日期2021-01-08  20:58
 */
public interface SetmealService {
    void add(Setmeal setmeal, Integer[] checkgroupIds);
    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);
    Setmeal findById(int id);
    List<Integer> findCheckgroupIdsBySetmealId(int id);
    void update(Setmeal setmeal, Integer[] checkgroupIds);
    void deleteById(int id);
    List<String> findImgs();
}
