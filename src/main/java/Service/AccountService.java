package Service;

import DAO.AccountDAO;
import Model.Account;

// * The purpose of a Service class is to contain "business logic" that sits between the web layer (controller) and
// * persistence layer (DAO). That means that the Service class performs tasks that aren't done through the web or
// * SQL: programming tasks like checking that the input is valid, conducting additional security checks, or saving the
// * actions undertaken by the API to a logging file.

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

        return accountDAO.insertAccount(account);                               

       
    }
    
    public Account login(Account account){
       
        return accountDAO.loginConfirm(account);                                //checks that username + password are correct, returns both + account id. else returns null.
        
    }
}
