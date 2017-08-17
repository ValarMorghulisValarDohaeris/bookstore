package com.atguigu.bookstore.impl;

import com.atguigu.bookstore.dao.AccountDAO;
import com.atguigu.bookstore.domain.Account;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by lenovo on 2017/8/10.
 */
public class AcountDaoImplTest {

    AccountDAO accountDAO = new AcountDaoImpl();

    @Test
    public void get() throws Exception {
        Account account = accountDAO.get(1);
        System.out.println(account.getBalance());
    }

    @Test
    public void updateBalance() throws Exception {
        accountDAO.updateBalance(1,500);
    }

}