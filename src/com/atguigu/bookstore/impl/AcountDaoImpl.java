package com.atguigu.bookstore.impl;

import com.atguigu.bookstore.dao.AccountDAO;
import com.atguigu.bookstore.domain.Account;

/**
 * Created by lenovo on 2017/8/10.
 */
public class AcountDaoImpl extends BaseDao<Account> implements AccountDAO {
    @Override
    public Account get(Integer accountId) {
        String sql = "SELECT accountId,balance FROM account WHERE accountId = ?";
        return query(sql,accountId);
    }

    @Override
    public void updateBalance(Integer accountId, float amount) {
        String sql = "UPDATE account SET balance = balance - ? WHERE accountId = ?";
        update(sql,amount,accountId);
    }
}
