package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckItemDAO;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service(interfaceClass = CheckItemService.class)// dubbo注解，暴露服务
@Transactional
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDAO checkItemDAO;

    @Override
    public void add(CheckItem checkItem) {
        checkItemDAO.add(checkItem);

    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        // 基于mybatis框架提供的分页助手插件完成,不需要返回值
        PageHelper.startPage(currentPage, pageSize);

        // select * from t_checkitem limit 0,10;
        Page<CheckItem> page = checkItemDAO.selectByCondition(queryString);
        long total = page.getTotal();
        List<CheckItem> rows = page.getResult();

        return new PageResult(total,rows);
    }
}
