package com.atguigu.bookstore.impl;

import com.atguigu.bookstore.dao.BookDAO;
import com.atguigu.bookstore.domain.Book;
import com.atguigu.bookstore.domain.ShoppingCart;
import com.atguigu.bookstore.domain.ShoppingCartItem;
import com.atguigu.bookstore.web.CriteriaBook;
import com.atguigu.bookstore.web.Page;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

public class BookDaoImplTest {

    private BookDAO bookDAO = new BookDaoImpl();

    @Test
    public void getBook() throws Exception {
        Book book = bookDAO.getBook(4);
        System.out.println(book);
    }

    @Test
    public void getPage() throws Exception {
        CriteriaBook cb = new CriteriaBook(0,Integer.MAX_VALUE,3);
        Page<Book> page = bookDAO.getPage(cb);

        System.out.println("pageNo: " + page.getPageNo());
        System.out.println("totalPageNumber: " + page.getTotalPageNumber());
        System.out.println("list: " + page.getList());
        System.out.println("prevPage: " + page.getPrevPage());
        System.out.println("nextPage: " + page.getNextPage());
    }

    @Test
    public void getStoreNumber() throws Exception {
        int storeNumber = bookDAO.getStoreNumber(5);
        System.out.println(storeNumber);
    }

    @Test
    public void batchUpdateStoreNumberAndSalesAmount() throws Exception {
        Collection<ShoppingCartItem> items = new ArrayList<>();

        Book book = bookDAO.getBook(1);
        ShoppingCartItem sci = new ShoppingCartItem(book);
        sci.setQuantity(10);
        items.add(sci);

        book = bookDAO.getBook(2);
        sci = new ShoppingCartItem(book);
        sci.setQuantity(11);
        items.add(sci);

        book = bookDAO.getBook(3);
        sci = new ShoppingCartItem(book);
        sci.setQuantity(12);
        items.add(sci);

        book = bookDAO.getBook(4);
        sci = new ShoppingCartItem(book);
        sci.setQuantity(13);
        items.add(sci);

        bookDAO.batchUpdateStoreNumberAndSalesAmount(items);
    }

}