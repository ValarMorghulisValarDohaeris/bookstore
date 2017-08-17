package com.atguigu.bookstore.impl;

import com.atguigu.bookstore.dao.TradeDAO;
import com.atguigu.bookstore.domain.Trade;
import org.junit.Test;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by lenovo on 2017/8/14.
 */
public class TradeDaoImplTest {

    private TradeDAO tradeDAO = new TradeDaoImpl();

    @Test
    public void insert() throws Exception {
        Trade trade = new Trade();
        trade.setUserId(3);
        trade.setTradeTime(new Date(new java.util.Date().getTime()));

        tradeDAO.insert(trade);
    }

    @Test
    public void getTradesWithUserId() throws Exception {
        Set<Trade> trades = tradeDAO.getTradesWithUserId(1);
        System.out.println(trades);
    }

}