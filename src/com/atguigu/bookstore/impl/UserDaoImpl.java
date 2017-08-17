package com.atguigu.bookstore.impl;

import com.atguigu.bookstore.dao.UserDAO;
import com.atguigu.bookstore.domain.User;

/**
 * Created by lenovo on 2017/8/10.
 */
public class UserDaoImpl extends BaseDao<User> implements UserDAO{
    @Override
    public User getUser(String username) {
        String sql = "SELECT userId,username,accountId FROM userinfo WHERE username = ?";
        return query(sql,username);
    }
}
