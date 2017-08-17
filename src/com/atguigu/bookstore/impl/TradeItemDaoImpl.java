package com.atguigu.bookstore.impl;

import com.atguigu.bookstore.dao.TradeItemDAO;
import com.atguigu.bookstore.domain.TradeItem;

import java.util.*;

/**
 * Created by lenovo on 2017/8/14.
 */
public class TradeItemDaoImpl extends BaseDao<TradeItem> implements TradeItemDAO{
    @Override
    public void batchSave(Collection<TradeItem> items) {
        String sql = "INSERT INTO tradeitem (bookid,quantity,tradeid) VALUES(?,?,?)";
        Object [][] params = new Object[items.size()][3];

        List<TradeItem> tradeItems = new ArrayList<>(items);
        for(int i = 0;i < tradeItems.size();i++){
            params[i][0] = tradeItems.get(i).getBookId();
            params[i][1] = tradeItems.get(i).getQuantity();
            params[i][2] = tradeItems.get(i).getTradeId();
        }
        batch(sql,params);
    }

    @Override
    public Set<TradeItem> getTradeItemsWithTradeId(Integer tradeId) {
        String sql = "SELECT itemId,bookId,quantity,tradeId FROM tradeitem WHERE tradeId = ?";
        return new HashSet<>(queryForList(sql,tradeId));
    }
}
