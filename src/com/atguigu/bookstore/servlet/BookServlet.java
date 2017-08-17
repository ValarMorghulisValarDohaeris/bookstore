package com.atguigu.bookstore.servlet;

import com.atguigu.bookstore.domain.*;
import com.atguigu.bookstore.web.BookStoreWebUtils;
import com.atguigu.bookstore.web.CriteriaBook;
import com.atguigu.bookstore.web.Page;
import com.google.gson.Gson;
import service.AccountService;
import service.BookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.CacheRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2017/8/9.
 */
@WebServlet(name = "BookServlet")
public class BookServlet extends HttpServlet {


    protected void cash(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.简单验证：验证表单域的值是否符合基本的规范：是否为空，是否可以转为int类型，是否是一个email. 不需要进行
        //查询数据库或调用任何的业务方法
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("username");
        String accountId = request.getParameter("accountId");

        StringBuffer errors = validateFormFiled(username, accountId);
        if(errors.toString().equals("")){
            errors = validateUser(username, accountId);

            if(errors.toString().equals("")){
                errors = validateBookStoreNumber(request);

                if(errors.toString().equals("")){
                    errors = validateBalance(request,accountId);
                }
            }
        }

        if(!errors.toString().equals("")){
            request.setAttribute("errors",errors);
            request.getRequestDispatcher("/WEB-INF/pages/cash.jsp").forward(request,response);
            return;
        }

        //验证通过执行具体的逻辑操作
        bookService.cash(BookStoreWebUtils.getShoppingCart(request),username,accountId);
        response.sendRedirect(request.getContextPath() + "/success.jsp");
    }

    private AccountService accountService = new AccountService();

    //验证余额是否充足
    public StringBuffer validateBalance(HttpServletRequest request,String accountId){
        ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
        StringBuffer errors = new StringBuffer();

        Account account = accountService.getAccount(Integer.parseInt(accountId));
        if(sc.getTotalMoney() > account.getBalance()){
            errors.append("余额不足!");
        }

        return errors;
    }

    //验证库存是否充足
    public StringBuffer validateBookStoreNumber(HttpServletRequest request){
        ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
        StringBuffer errors = new StringBuffer();

        for(ShoppingCartItem sci : sc.getItems()){
            int quantity = sci.getQuantity();
            int storeNumber = bookService.getBook(sci.getBook().getId()).getStoreNumber();

            if(quantity > storeNumber){
                errors.append(sci.getBook().getTitle() + "库存不足<br>");
            }
        }

        return errors;
    }

    //验证用户名和账号是否匹配
    private StringBuffer validateUser(String username, String accountId) {
        boolean flag = false;
        User user = userService.getUserByUserName(username);
        if(user != null){
            int accountId2 = user.getAccountId();
            if(accountId.trim().equals("" + accountId2)){
                flag = true;
            }
        }

        StringBuffer errors2 = new StringBuffer();
        if(!flag){
            errors2.append("用户名和账号不匹配");
        }
        return errors2;
    }

    //验证表单域是否符合基本的规则：是否为空
    private StringBuffer validateFormFiled(String username, String accountId) {
        StringBuffer errors = new StringBuffer();

        if(username == null || username.trim().equals("")){
            errors.append("用户名不能为空<br>");
        }

        if(accountId == null || accountId.trim().equals("")){
            errors.append("账号不能为空");
        }
        return errors;
    }

    protected void updateItemQuantity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //4. 在 updateItemQuantity 方法中, 获取 quanity, id, 再获取购物车对象, 调用 service 的方法做修改
        String idStr = request.getParameter("id");
        String quantityStr = request.getParameter("quantity");

        ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
        int id = -1;
        int quantity = -1;

        try{
            id = Integer.parseInt(idStr);
            quantity = Integer.parseInt(quantityStr);
        } catch(Exception e){}

        if(id > 0 && quantity > 0)
            bookService.updateItemQuantity(sc,id,quantity);

        //5. 传回 JSON 数据: bookNumber:xx, totalMoney
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("bookNumber",sc.getBookNumber());
        result.put("totalMoney",sc.getTotalMoney());

        Gson gson  = new Gson();
        String jsonStr = gson.toJson(result);
        response.setContentType("text/javascript");
        response.getWriter().print(jsonStr);
    }

    protected void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
        bookService.clearShoppingCart(sc);

        request.getRequestDispatcher("/WEB-INF/pages/emptycart.jsp").forward(request,response);
    }

    protected void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        int id = -1;
        boolean flag = false;

        try{
            id = Integer.parseInt(idStr);
        } catch(Exception e){}

//        HttpSession session = request.getSession();
//        ShoppingCart sc = (ShoppingCart) session.getAttribute("ShoppingCart");
        ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
        bookService.remove(sc,id);

        if(sc.isEmpty()){
            request.getRequestDispatcher("/WEB-INF/pages/emptycart.jsp").forward(request,response);
            return;
        }

        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request,response);
    }

    protected void forwardPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = request.getParameter("page");
        request.getRequestDispatcher("/WEB-INF/pages/" + page + ".jsp").forward(request,response);
    }


    protected void addToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取商品的id
        String idStr = request.getParameter("id");
        int id = -1;
        boolean flag = false;

        try{
            id = Integer.parseInt(idStr);
        } catch(Exception e){}

        if(id > 0){
            //2.获取购物车对象
            ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);

            //3.调用BookService的addToCart()方法把商品放到购物车中
            flag = bookService.addToCart(id,sc);
        }

        if(flag){
            //4.直接调用getBooks()方法
            getBooks(request,response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/error-1.jsp");
    }

    protected void getBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        int id = -1;

        Book book = null;
        try{
            id = Integer.parseInt(idStr);
        } catch(NumberFormatException e){}

        if(id > 0)
            book = bookService.getBook(id);

        if(book == null){
            response.sendRedirect(request.getContextPath() + "/error-1.jsp");
            return;
        }
        request.setAttribute("book",book);
        request.getRequestDispatcher("/WEB-INF/pages/book.jsp").forward(request,response);
    }

    protected void getBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageNoStr = request.getParameter("pageNo");
        String minPriceStr = request.getParameter("minPrice");
        String maxPriceStr = request.getParameter("maxPrice");

        int pageNo = 1;
        int minPrice = 0;
        int maxPrice = Integer.MAX_VALUE;

        try{
            pageNo = Integer.parseInt(pageNoStr);
        }catch(NumberFormatException e){}

        try{
            minPrice = Integer.parseInt(minPriceStr);
        }catch(NumberFormatException e){}

        try{
            maxPrice = Integer.parseInt(maxPriceStr);
        }catch(NumberFormatException e){}

        CriteriaBook criteriaBook = new CriteriaBook(minPrice,maxPrice,pageNo);
        Page<Book> page = bookService.getPage(criteriaBook);

        request.setAttribute("bookpage",page);

        request.getRequestDispatcher("/WEB-INF/pages/books.jsp").forward(request,response);
    }

    private BookService bookService = new BookService();

    private UserService userService = new UserService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String methodName = request.getParameter("method");
        Method method = null;
        try {
            method = getClass().getDeclaredMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
            method.setAccessible(true);
            method.invoke(this,request,response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
