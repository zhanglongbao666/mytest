package com.itheima.health.service;

import com.itheima.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * 包名:com.itheima.health.service
 *
 * @author ZhangLongBao
 * 日期2021-01-10  12:13
 */
public interface OrderSettingService {
    void add(List<OrderSetting> orderSettingList);
    /**
     * 按月查询预约设置信息
     * @param month
     * @return
     */
    List<Map<String, Integer>> getOrderSettingByMonth(String month);
    void editNumberByDate(OrderSetting orderSetting);
}
