package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 包名:com.itheima.health.controller
 *
 * @author ZhangLongBao
 * 日期2021-01-05  17:25
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    //查询所有数据
    @RequestMapping("/findAll")
    public Result findAll(){
        // 调用服务查询所有的检查项
        List<CheckItem> list = checkItemService.findAll();
        // 封装返回的结果
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
    }
    //新增体检检查管理
    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        // 调用服务添加
        checkItemService.add(checkItem);
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        // 调用业务来分页，获取分页结果
        PageResult<CheckItem> pageResult = checkItemService.findPage(queryPageBean);
        //return pageResult;
        // 返回给页面, 包装到Result, 统一风格
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,pageResult);
    }

    @RequestMapping("/deleteById")
    public Result deleteById(Integer id){
        checkItemService.deleteById(id);
        // 响应结果
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }


    @GetMapping("/findById")
    public Result findById(int id){
        CheckItem checkItem = checkItemService.findById(id);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
    }

    @PostMapping("/update")
    public Result update(@RequestBody CheckItem checkItem){
        // 调用服务更新
        checkItemService.update(checkItem);
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }
}
