package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.pojo.Setmeal;
import com.itheima.health.service.SetmealService;
import com.itheima.health.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 包名:com.itheima.health.controller
 *
 * @author ZhangLongBao
 * 日期2021-01-08  20:56
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    private static Logger log = LoggerFactory.getLogger(SetmealController.class);

    @Reference
    private SetmealService setmealService;

    @PostMapping("/upload")
    public Result upload(MultipartFile imgFile) {
        //1. 获取源文件名
        String originalFilename = imgFile.getOriginalFilename();
        //2. 截取后缀名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //3. 生成唯一id,
        String uniqueId = UUID.randomUUID().toString();
        //4. 拼接唯一文件名
        String filename = uniqueId + suffix;
        //5. 调用7牛工具上传图片
        try {
            QiNiuUtils.uploadViaByte(imgFile.getBytes(), filename);
            //6. 构建返回的数据
            /*
             {
                imgName: 图片名 , 补全formData.img
                domain: 七牛的域名 图片回显imageUrl = domain+图片名
            }
             */
            Map<String, String> map = new HashMap<String, String>(2);
            map.put("imgName", filename);
            map.put("domain", QiNiuUtils.DOMAIN);
            //7. 放到result里，再返回给页面
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, map);
        } catch (IOException e) {
            log.error("上传文件失败了", e);
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){
        // 调用业务服务添加
        setmealService.add(setmeal, checkgroupIds);
        // 响应结果
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        // 调用服务分页查询
        PageResult<Setmeal> pageResult = setmealService.findPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,pageResult);
    }

    /**
     * 通过id查询套餐信息
     */
    @GetMapping("/findById")
    public Result findById(int id){
        // 调用服务查询
        Setmeal setmeal = setmealService.findById(id);
        // 前端要显示图片需要全路径
        // 封装到map中，解决图片路径问题
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("setmeal", setmeal); // formData
        resultMap.put("domain", QiNiuUtils.DOMAIN); // domain
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,resultMap);
    }

    /**
     * 通过id查询选中的检查组ids
     */
    @GetMapping("/findCheckgroupIdsBySetmealId")
    public Result findCheckgroupIdsBySetmealId(int id){
        List<Integer> checkgroupIds = setmealService.findCheckgroupIdsBySetmealId(id);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkgroupIds);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public Result update(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){
        // 调用业务服务修改
        setmealService.update(setmeal, checkgroupIds);
        // 响应结果
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    @PostMapping("/deleteById")
    public Result deleteById(int id){
        setmealService.deleteById(id);
        return new Result(true,"删除套餐成功");
    }
}
