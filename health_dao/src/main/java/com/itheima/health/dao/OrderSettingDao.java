package com.itheima.health.dao;

import com.itheima.health.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 包名:com.itheima.health.dao
 *
 * @author ZhangLongBao
 * 日期2021-01-10  15:41
 */
public interface OrderSettingDao {
    OrderSetting findByOrderDate(Date orderDate);
    void updateNumber(OrderSetting orderSetting);
    void add(OrderSetting orderSetting);
    List<Map<String, Integer>> getOrderSettingByMonth(String month);
    void editNumberByOrderDate(OrderSetting orderSetting);
}
