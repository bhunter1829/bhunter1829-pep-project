package Service;

import DAO.AccountDAO;
import Model.Account;


public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }



    public Account addAccount(Account account){
       if(account.getUsername() =="" || account.getPassword().length() <= 3){   //check that username isn't empty, and that password meets length requirements.        
        return null;                                                            //if either is met return null.
       }

        return accountDAO.insertAccount(account);                               //create user being passed in

       
    }
    
    public Account login(Account account){
       
        return accountDAO.loginConfirm(account);                                //checks that username + password are correct, returns both + account id. else returns null.
        
    }
}
