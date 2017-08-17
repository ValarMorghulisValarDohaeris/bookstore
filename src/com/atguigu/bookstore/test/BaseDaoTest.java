package com.atguigu.bookstore.test;

import com.atguigu.bookstore.domain.Book;
import com.atguigu.bookstore.impl.BaseDao;
import com.atguigu.bookstore.impl.BookDaoImpl;

import java.sql.Date;
import java.util.List;


public class BaseDaoTest {

    private BookDaoImpl bookDaoImpl = new BookDaoImpl();

    @org.junit.Test
    public void insert() throws Exception {
        String sql = "INSERT INTO trade (userid,tradetime) VALUES(?,?)";
        long id = bookDaoImpl.insert(sql,1,new Date(new java.util.Date().getTime()));

        System.out.println(id);
    }

    @org.junit.Test
    public void update() throws Exception {
        String sql = "UPDATE mybooks SET salesamount = ? WHERE id = ?";
        bookDaoImpl.update(sql,10,4);
    }

    @org.junit.Test
    public void query() throws Exception {
        String sql = "SELECT id,author,title,price,publishingDate,salesAmount,storeNumber,remark " +
                "FROM mybooks WHERE id = ?";

        Book book = bookDaoImpl.query(sql,4);
        System.out.println(book);
    }

    @org.junit.Test
    public void queryForList() throws Exception {
        String sql = "SELECT id,author,title,price,publishingDate,salesAmount,storeNumber,remark " +
                "FROM mybooks WHERE id < ?";

        List<Book> books = bookDaoImpl.queryForList(sql,4);
        System.out.println(books);
    }

    @org.junit.Test
    public void getSingleVal() throws Exception {
        String sql = " SELECT count(id) FROM mybooks";
        long count = bookDaoImpl.getSingleVal(sql);

        System.out.println(count);
    }

    @org.junit.Test
    public void batch() throws Exception {
        String sql = "UPDATE mybooks SET salesAmount = ?,storeNumber = ? " +
                "WHERE id = ?";

        bookDaoImpl.batch(sql,new Object[]{1,1,1},new Object[]{2,2,2},new Object[]{3,3,3});
    }
}
