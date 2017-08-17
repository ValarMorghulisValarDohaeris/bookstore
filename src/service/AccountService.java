package service;

import com.atguigu.bookstore.dao.AccountDAO;
import com.atguigu.bookstore.domain.Account;
import com.atguigu.bookstore.impl.AcountDaoImpl;

/**
 * Created by lenovo on 2017/8/10.
 */
public class AccountService {

    private AccountDAO accountDAO = new AcountDaoImpl();

    public Account getAccount(int accountId){
        return accountDAO.get(accountId);
    }
}
