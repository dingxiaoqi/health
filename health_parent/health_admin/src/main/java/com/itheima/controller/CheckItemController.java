package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    // 注入远程dubbo服务接口
    @Reference
    private CheckItemService checkItemService;

    // 新增检查项
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){

        try {
            // 如何判断远程接口调用成功/失败呢？可以通过加try...catch解决
            checkItemService.add(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS);

    }
    // 检查项-查询列表
    @RequestMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = checkItemService.pageQuery(queryPageBean);
        return pageResult;
    }

    // 检查项-根据id删除检查项
    @RequestMapping("delteById")
    public Result deleteById(Integer id){

        return null;
    }



}
