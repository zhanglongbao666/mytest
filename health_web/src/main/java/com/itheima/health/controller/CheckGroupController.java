package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 包名:com.itheima.health.controller
 *
 * @author ZhangLongBao
 * 日期2021-01-07  15:31
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds){
        // 调用业务服务
        checkGroupService.add(checkGroup, checkitemIds);
        // 响应结果
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        // 调用业务查询
        PageResult<CheckGroup> pageResult = checkGroupService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, pageResult);
    }
    @GetMapping("/findById")
    public Result findById(Integer checkGroupId){
        // 调用业务服务
        //System.out.println(checkGroupId);
        CheckGroup checkGroup = checkGroupService.findById(checkGroupId);
        //System.out.println(checkGroupId);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
    }
    @GetMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer checkGroupId){
        // 调用服务查询
        List<Integer> checkItemIds = checkGroupService.findCheckItemIdsByCheckGroupId(checkGroupId);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItemIds);
    }
    @PostMapping("/update")
    public Result update(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds){
        // 调用业务服务
        checkGroupService.update(checkGroup, checkitemIds);
        // 响应结果
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }
    @PostMapping("/deleteById")
    public Result deleteById(int id){
        //调用业务服务删除
        checkGroupService.deleteById(id);
        return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }

    @GetMapping("/findAll")
    public Result findAll(){
        List<CheckGroup> all = checkGroupService.findAll();
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,all);
    }
}
