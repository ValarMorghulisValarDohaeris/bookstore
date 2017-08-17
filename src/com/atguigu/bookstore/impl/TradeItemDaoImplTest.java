package com.atguigu.bookstore.impl;

import com.atguigu.bookstore.dao.TradeItemDAO;
import com.atguigu.bookstore.domain.TradeItem;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by lenovo on 2017/8/14.
 */
public class TradeItemDaoImplTest {

    private TradeItemDAO tradeItemDAO = new TradeItemDaoImpl();

    @Test
    public void batchSave() throws Exception {
        Collection<TradeItem> items = new ArrayList<>();

        items.add(new TradeItem(null,1,10,3));
        items.add(new TradeItem(null,2,5,1));

        tradeItemDAO.batchSave(items);
    }

    @Test
    public void getTradeItemsWithTradeId() throws Exception {
        Set<TradeItem> items = tradeItemDAO.getTradeItemsWithTradeId(3);
        System.out.println(items);
    }

}