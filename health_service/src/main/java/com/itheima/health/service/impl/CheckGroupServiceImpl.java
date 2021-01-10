package com.itheima.health.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.CheckGroupDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 包名:com.itheima.health.service.impl
 *
 * @author ZhangLongBao
 * 日期2021-01-07  15:36
 */
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    @Transactional
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        // 添加检查组
        checkGroupDao.add(checkGroup);
        // 获取检查组的id
        Integer checkGroupId = checkGroup.getId();
        // 遍历检查项id, 添加检查组与检查项的关系
        if (null != checkitemIds) {
            // 有钩选
            for (Integer checkitemId : checkitemIds) {
                //添加检查组与检查项的关系
                checkGroupDao.addCheckGroupCheckItem(checkGroupId, checkitemId);
            }
        }
    }

    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
        // 使用PageHelper.startPage
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // 有查询条件的处理, 模糊查询
        if(!StringUtils.isEmpty(queryPageBean.getQueryString())){
            // 拼接%
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString()+ "%");
        }
        // 紧接着的查询会被分页
        Page<CheckGroup> page = checkGroupDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult<CheckGroup>(page.getTotal(), page.getResult());
    }

    @Override
    public CheckGroup findById(Integer checkGroupId) {
        return checkGroupDao.findById(checkGroupId);
    }

    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer checkGroupId) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(checkGroupId);
    }

    @Override
    @Transactional
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
        // 先更新检查组
        checkGroupDao.update(checkGroup);
        // 删除旧关系
        checkGroupDao.deleteCheckGroupCheckItem(checkGroup.getId());
        // 建立新关系
        if(null != checkitemIds){
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckGroupCheckItem(checkGroup.getId(), checkitemId);
            }
        }
    }

    @Override
    @Transactional
    public void deleteById(int id) throws HealthException {
        // 检查 这个检查组是否被套餐使用了
        int count = checkGroupDao.findSetmealCountByCheckGroupId(id);
        if(count > 0){
            // 被使用了
            throw new HealthException(MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
        // 没有被套餐使用,就可以删除数据
        // 先删除检查组与检查项的关系
        checkGroupDao.deleteCheckGroupCheckItem(id);
        // 删除检查组
        checkGroupDao.deleteById(id);
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
}
