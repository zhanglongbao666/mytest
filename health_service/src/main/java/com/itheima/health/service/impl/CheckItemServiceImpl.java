package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.dao.CheckItemDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * 包名:com.itheima.health.service.impl
 *
 * @author ZhangLongBao
 * 日期2021-01-05  17:30
 */
//没加interfaceClass, 调用No Provider 的异常
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
        //使用pagehelper插件进行分页查询
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //判断是否有查询条件
        if (StringUtil.isNotEmpty(queryPageBean.getQueryString())) {
            // 有查询条件，拼接%
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        // 紧接着的查询语句会被分页
        Page<CheckItem> page = checkItemDao.findByCondition(queryPageBean.getQueryString());
        // 封装到分页结果对象中
        PageResult<CheckItem> pageResult = new PageResult<CheckItem>(page.getTotal(), page.getResult());
        return pageResult;
    }

    @Override
    public void deleteById(Integer id) {
        //判断该checkitem是否被检查组调用
        //调用dao查询检查项的id在t_checkgroup_checkitem表中存在几条记录
        int count = checkItemDao.findCountByCheckItemId(id);
        if (count>0) {
            // 已经被检查组使用了，则不能删除，报错
            throw new HealthException(MessageConstant.QUERY_CHECKITEM_FAIL);
        }
        //没使用就可以调用dao删除
        checkItemDao.deleteById(id);
    }

    @Override
    public CheckItem findById(int id) {
        return checkItemDao.findById(id);
    }

    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }
}
