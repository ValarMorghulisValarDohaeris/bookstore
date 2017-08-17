package com.atguigu.bookstore.web;

import com.atguigu.bookstore.domain.ShoppingCart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by lenovo on 2017/8/10.
 */
public class BookStoreWebUtils {

    /**
     * 获取购物车对象：从session中获取，若session中没有该对象
     * 则创建一个新的购物车对象，放入到session中
     * 若有，则直接返回
     * @return
     */
    public static ShoppingCart getShoppingCart(HttpServletRequest request){
        HttpSession session = request.getSession();

        ShoppingCart sc = (ShoppingCart) session.getAttribute("ShoppingCart");
        if(sc == null){
            sc = new ShoppingCart();
            session.setAttribute("ShoppingCart",sc);
        }
        return sc;
    }
}
