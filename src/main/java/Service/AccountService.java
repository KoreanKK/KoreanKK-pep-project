package Service;

import Model.Account;
import Model.Message;
import DAO.SocialDAO;
import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;

public class AccountService {

    AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    //method for register
    public Account register (Account account) {
        if (account.getUsername() == "" || account.getPassword().length() < 4) {
            return null;
        }
        return accountDAO.registerDAO(account); //
    }

    //method for login
    public Account login (Account account) {
        return accountDAO.loginDAO(account); //
    }



}
