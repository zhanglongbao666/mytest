package com.itheima.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.exception.HealthException;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 包名:com.itheima.health.service.impl
 *
 * @author ZhangLongBao
 * 日期2021-01-10  12:14
 */
@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void add(List<OrderSetting> orderSettingList) {
        //遍历集合
        for (OrderSetting orderSetting : orderSettingList) {
            // 判断是否存在, 通过日期来查询, 注意：日期里是否有时分秒，数据库里的日期是没有时分秒的
            OrderSetting osInDB = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
            if (null != osInDB) {
                // 数据库存在这个预约设置, 已预约数量不能大于可预约数量
                if (osInDB.getReservations() > orderSetting.getNumber()) {
                    throw new HealthException(orderSetting.getOrderDate() + " 中已预约数量不能大于可预约数量");
                }
                orderSettingDao.updateNumber(orderSetting);
            } else {
                // 不存在
                orderSettingDao.add(orderSetting);
            }
        }
    }

    @Override
    public List<Map<String, Integer>> getOrderSettingByMonth(String month) {
        month += "%";
        return orderSettingDao.getOrderSettingByMonth(month);
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        //通过日期判断预约设置是否存在？
        OrderSetting osInDB = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
        //判断
        if (null != osInDB) {
            //存在相同日期的预约设置
            // 判断已经预约的人数是否大于要更新的最大可预约人数， reverations > 传进来的number数量，则不能更新，要报错
            if (osInDB.getReservations()>orderSetting.getNumber()) {
                throw new HealthException("最大预约人数不能小已预约人数！");
            }
            //已预约人数不大于要更新的最大可预约人数,则可以进行更新
            orderSettingDao.editNumberByOrderDate(orderSetting);
        }else{
            //如果相同日期没有设置,则直接添加设置
            orderSettingDao.add(orderSetting);
        }
    }
}

