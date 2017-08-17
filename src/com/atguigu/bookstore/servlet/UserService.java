package com.atguigu.bookstore.servlet;

import java.util.Iterator;
import java.util.Set;

import com.atguigu.bookstore.dao.BookDAO;
import com.atguigu.bookstore.dao.TradeDAO;
import com.atguigu.bookstore.dao.TradeItemDAO;
import com.atguigu.bookstore.dao.UserDAO;
import com.atguigu.bookstore.domain.Trade;
import com.atguigu.bookstore.domain.TradeItem;
import com.atguigu.bookstore.domain.User;
import com.atguigu.bookstore.impl.BookDaoImpl;
import com.atguigu.bookstore.impl.TradeDaoImpl;
import com.atguigu.bookstore.impl.TradeItemDaoImpl;
import com.atguigu.bookstore.impl.UserDaoImpl;

public class UserService {

	private UserDAO userDAO = new UserDaoImpl();
	
	public User getUserByUserName(String username){
		return userDAO.getUser(username);
	}
	
	private TradeDAO tradeDAO = new TradeDaoImpl();
	private TradeItemDAO tradeItemDAO = new TradeItemDaoImpl();
	private BookDAO bookDAO = new BookDaoImpl();
	
	public User getUserWithTrades(String username){
	
		//调用UserDao的方法获取User对象
		User user = userDAO.getUser(username);
		if(user == null){
			return null;
		}
		
		//调用TradeDao的方法获取Trade的集合.把其装配为User的属性
		int userId = user.getUserId();
		
		//调用TradeItemDao的方法获取每一个Trade中的TradeItem的集合. 并把其装配为Trade属性
		Set<Trade> trades = tradeDAO.getTradesWithUserId(userId);
		
		if(trades != null){
			Iterator<Trade> tradeIt = trades.iterator();
			
			while(tradeIt.hasNext()){
				Trade trade = tradeIt.next();
				
				int tradeId = trade.getTradeId();
				Set<TradeItem> items = tradeItemDAO.getTradeItemsWithTradeId(tradeId);
				
				if(items != null){
					for(TradeItem item: items){
						item.setBook(bookDAO.getBook(item.getBookId())); 
					}
					
					if(items != null && items.size() != 0){
						trade.setItems(items);						
					}
				}
				
				if(items == null || items.size() == 0){
					tradeIt.remove();	
				}
				
			}
		}
		
		if(trades != null && trades.size() != 0){
			user.setTrades(trades);			
		}
		
		return user;
	}
	
}
