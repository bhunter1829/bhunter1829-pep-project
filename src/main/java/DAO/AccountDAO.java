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
    
}
