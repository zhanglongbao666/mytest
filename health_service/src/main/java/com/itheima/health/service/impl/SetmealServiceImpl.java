package com.itheima.health.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.dao.SetmealDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 包名:com.itheima.health.service.impl
 *
 * @author ZhangLongBao
 * 日期2021-01-08  22:16
 */
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    @Override
    @Transactional
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //- 先添加套餐
        setmealDao.add(setmeal);
        //- 获取套餐的id
        Integer setmealId = setmeal.getId();
        //- 遍历checkgroupIds数组
        if(null != checkgroupIds){
            //- 添加套餐与检查组的关系
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealCheckGroup(setmealId,checkgroupId);
            }
        }
        //- 事务控制
    }

    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // 查询条件
        if(!StringUtils.isEmpty(queryPageBean.getQueryString())){
            // 模糊查询 %
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        // 条件查询，这个查询语句会被分页
        Page<Setmeal> page = setmealDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult<Setmeal>(page.getTotal(), page.getResult());
    }

    @Override
    public Setmeal findById(int id) {
        return setmealDao.findById(id);
    }

    @Override
    public List<Integer> findCheckgroupIdsBySetmealId(int id) {
        return setmealDao.findCheckgroupIdsBySetmealId(id);
    }

    @Override
    @Transactional
    public void update(Setmeal setmeal, Integer[] checkgroupIds) {
        // 先更新套餐信息
        setmealDao.update(setmeal);
        // 删除旧关系
        setmealDao.deleteSetmealCheckGroup(setmeal.getId());
        // 添加新关系
        if(null != checkgroupIds){
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealCheckGroup(setmeal.getId(), checkgroupId);
            }
        }
    }

    @Override
    public void deleteById(int id) {
        //查询与套餐有关的检查组有几个
        int count = setmealDao.findOrderCountBySetmealId(id);
        if (count>0) {
            //如果有调用关系,则不能删除,抛出异常
            throw new HealthException("删除套餐失败,有调用关系!");
        }
        //否则,先删除套餐与检查组的关系
        setmealDao.deleteSetmealCheckGroup(id);
        // 再删除套餐
        setmealDao.deleteById(id);
    }

    @Override
    public List<String> findImgs() {
        return setmealDao.findImgs();
    }
}
