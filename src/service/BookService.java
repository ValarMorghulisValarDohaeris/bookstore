package service;

import com.atguigu.bookstore.dao.*;
import com.atguigu.bookstore.domain.*;
import com.atguigu.bookstore.impl.*;
import com.atguigu.bookstore.web.CriteriaBook;
import com.atguigu.bookstore.web.Page;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

public class BookService {

    private BookDAO bookDAO = new BookDaoImpl();

    public Page<Book> getPage(CriteriaBook criteriaBook){
        return bookDAO.getPage(criteriaBook);
    }

    public Book getBook(int id) {
        return bookDAO.getBook(id);
    }

    public boolean addToCart(int id, ShoppingCart sc) {
        Book book = bookDAO.getBook(id);

        if(book != null){
            sc.addBook(book);
            return true;
        }
        return false;
    }

    public void remove(ShoppingCart sc, int id) {
        sc.removeItem(id);
    }

    public void clearShoppingCart(ShoppingCart sc) {
        sc.clear();
    }

    public void updateItemQuantity(ShoppingCart sc, int id, int quantity) {
        sc.updateItemQuantity(id,quantity);
    }

    private AccountDAO accountDAO = new AcountDaoImpl();
    private TradeDAO tradeDAO = new TradeDaoImpl();
    private UserDAO userDAO = new UserDaoImpl();
    private TradeItemDAO tradeItemDAO = new TradeItemDaoImpl();

    //业务方法
    public void cash(ShoppingCart shoppingCart, String username, String accountId) {
        //1.更新mybooks数据表相关记录的salesamount和storenumber
        bookDAO.batchUpdateStoreNumberAndSalesAmount(shoppingCart.getItems());

        //2.更新account数据表的balance
        accountDAO.updateBalance(Integer.parseInt(accountId),shoppingCart.getTotalMoney());

        //3.向trade数据表插入一条记录
        Trade trade = new Trade();
        trade.setTradeTime(new Date(new java.util.Date().getTime()));
        trade.setUserId(userDAO.getUser(username).getUserId());
        tradeDAO.insert(trade);

        //4.向tradeitem数据表插入n条记录
        Collection<TradeItem> items = new ArrayList<>();
        for(ShoppingCartItem sci : shoppingCart.getItems()){
            TradeItem tradeItem = new TradeItem();
            tradeItem.setBookId(sci.getBook().getId());
            tradeItem.setQuantity(sci.getQuantity());
            tradeItem.setTradeId(trade.getTradeId());
            items.add(tradeItem);
        }
        tradeItemDAO.batchSave(items);

        //5.清空购物车
        shoppingCart.clear();
    }
}
