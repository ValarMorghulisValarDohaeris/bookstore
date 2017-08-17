package com.atguigu.bookstore.filter;


import com.atguigu.bookstore.db.JDBCUtils;
import com.atguigu.bookstore.web.ConnectionContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by lenovo on 2017/8/14.
 */
public class TransactionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        Connection connection = null;

        try{
            System.out.println("55555555555555");
            //1.获取连接
            connection = JDBCUtils.getConnection();

            //2.开启事务
            connection.setAutoCommit(false);

            //3.利用ThreadLocal把连接和当前线程绑定
            ConnectionContext.getInstance().bind(connection);

            //4.把请求转给目标Servlet
            filterChain.doFilter(servletRequest,servletResponse);

            System.out.println("222222222222222");
            //5.提交事务
            connection.commit();
        } catch (Exception e){
            System.out.println("1111111111111111111");
            e.printStackTrace();

            //6.回滚事务
            try {
                System.out.println("3333333333333333333");
                connection.rollback();
                System.out.println("3333333333333333333");
            } catch (SQLException e1) {
                System.out.println("99999999999999999");
                e1.printStackTrace();
            }

            HttpServletResponse resp = (HttpServletResponse) servletResponse;
            HttpServletRequest req = (HttpServletRequest) servletRequest;
            resp.sendRedirect(req.getContextPath() + "/error-1.jsp");

        }finally{
            //7.解除绑定
            ConnectionContext.getInstance().remove();

            //7.关闭连接
            JDBCUtils.release(connection);

        }
    }

    @Override
    public void destroy() {

    }
}
