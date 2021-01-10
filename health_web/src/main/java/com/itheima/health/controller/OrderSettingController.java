package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.OrderSetting;
import com.itheima.health.service.OrderSettingService;
import com.itheima.health.utils.POIUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 包名:com.itheima.health.controller
 *
 * @author ZhangLongBao
 * 日期2021-01-10  10:48
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    private static final Logger log = LoggerFactory.getLogger(OrderSettingController.class);

    @Reference
    private OrderSettingService orderSettingService;
    @RequestMapping("/upload")
    //Multipartfile表示上传过来的文件的意思
    public Result upload(MultipartFile excelFile){
        try {
            /*//解析excel文件,使用POIUtils进行解析,每个数组,代表一行数据
            List<String[]> datas = POIUtils.readExcel(excelFile);
            log.debug("导入预约设置读取到了{}条记录",datas.size());
            //转成List<Ordersetting>，再调用service 方法做导入，返回给页面
            // String[] 长度为2, 0:日期，1：数量
            final SimpleDateFormat sdf = new SimpleDateFormat(POIUtils.DATE_FORMAT);
            List<OrderSetting> orderSettingList = datas.stream().map(arr -> {
                OrderSetting os = new OrderSetting();
                try {
                    os.setOrderDate(sdf.parse(arr[0]));
                    os.setNumber(Integer.valueOf(arr[1]));
                } catch (ParseException e) {
                }
                return os;
            }).collect(Collectors.toList());*/

            // 读取excel内容
            List<String[]> strings = POIUtils.readExcel(excelFile);
            // 转成List<OrderSetting>
            List<OrderSetting> orderSettingList = new ArrayList<OrderSetting>();
            // 日期格式解析
            SimpleDateFormat sdf = new SimpleDateFormat(POIUtils.DATE_FORMAT);
            Date orderDate = null;
            OrderSetting os = null;
            for (String[] string : strings) {
                //String[0]就是excel表格中的第一列,String类型的日期列
                orderDate = sdf.parse(string[0]);
                //String[1]就是excel表格中的第二列,可预约数量
                int number = Integer.valueOf(string[1]);
                //将这两种属性作为参数,创建OrderSetting类并赋值给os
                os = new OrderSetting(orderDate,number);
                //将遍历出来的每一个创建OrderSetting类的对象os添加到OrderSetting类的集合中,这样就实现了将excel表格中的String类型的数据转换成OrderSetting类的集合
                orderSettingList.add(os);
            }
            //调用业务层将集合添加到数据库中
            orderSettingService.add(orderSettingList);
            //返回Result
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            log.error("导入预约设置失败",e);
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }
    /**
     * 通过月份查询预约设置信息
     */
    @GetMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String month){
        // 按月查询预约设置信息
        List<Map<String,Integer>> data = orderSettingService.getOrderSettingByMonth(month);
        return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS,data);
    }
    @PostMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        // 调用服务更新
        orderSettingService.editNumberByDate(orderSetting);
        return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
    }
}
