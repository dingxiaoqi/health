package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;

public interface CheckItemDAO {
    public void add(CheckItem checkItem);

    public Page<CheckItem> selectByCondition(String queryString);
}
