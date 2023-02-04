package DAO;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import Model.Account;
import Util.ConnectionUtil;



public class AccountDAO {

    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();
            ResultSet primarykeyRSet = preparedStatement.getGeneratedKeys();
            if(primarykeyRSet.next()){
                int generated_account_id = (int) primarykeyRSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;

    }



    public Account getByUserId(int id){          //Temp method, not in use. Used for testing/possible implementation. Ignore.
                                                            
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);


            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password"));
                return account;
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }



    
    public Account loginConfirm(Account loginAccount){          
        
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);


            preparedStatement.setString(1, loginAccount.getUsername());
            preparedStatement.setString(2, loginAccount.getPassword());
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password"));
                return account;
                }

            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            return null;
        }
    
}
