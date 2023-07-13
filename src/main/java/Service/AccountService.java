package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    
    private AccountDAO accountDAO;

    public AccountService(){
        this.accountDAO = new AccountDAO();
    }


    public Account createAccount(Account account){
        return accountDAO.insertAccount(account);
    }

    public Account readAccount(Account account){
        return accountDAO.getAccountByUsername(account);
    }

}
