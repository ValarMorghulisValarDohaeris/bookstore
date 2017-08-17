package com.atguigu.bookstore.impl;

import com.atguigu.bookstore.dao.UserDAO;
import com.atguigu.bookstore.domain.User;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by lenovo on 2017/8/10.
 */
public class UserDaoImplTest {
    private UserDAO userDAO = new UserDaoImpl();

    @Test
    public void getUser() throws Exception {
        User user = userDAO.getUser("AAA");
        System.out.println(user);
    }

}