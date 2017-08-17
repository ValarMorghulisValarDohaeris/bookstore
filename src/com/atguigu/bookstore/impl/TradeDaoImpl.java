package com.atguigu.bookstore.impl;

import com.atguigu.bookstore.dao.TradeDAO;
import com.atguigu.bookstore.domain.Trade;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lenovo on 2017/8/14.
 */
public class TradeDaoImpl extends BaseDao<Trade> implements TradeDAO {
    @Override
    public void insert(Trade trade) {
        String sql = "INSERT INTO trade (userid,tradetime) VALUES (?,?)";
        long tradeId = insert(sql,trade.getUserId(),trade.getTradeTime());
        trade.setTradeId((int) tradeId);
    }

    @Override
    public Set<Trade> getTradesWithUserId(Integer userId) {
        String sql = "SELECT tradeId,userId,tradeTime FROM trade WHERE userId = ?";
        Set<Trade> trades = new HashSet(queryForList(sql,userId));
        System.out.println(trades);
        return trades;
    }
}
