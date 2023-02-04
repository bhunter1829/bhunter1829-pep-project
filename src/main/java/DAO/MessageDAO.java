package DAO;

import java.sql.*;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {

        public Message insertMessage(Message message){
            Connection connection = ConnectionUtil.getConnection();
            
            try {
                String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                preparedStatement.setInt(1, message.getPosted_by());
                preparedStatement.setString(2, message.getMessage_text());
                preparedStatement.setFloat(3, message.getTime_posted_epoch());

                preparedStatement.executeUpdate();
                ResultSet primarykeyRSet = preparedStatement.getGeneratedKeys();
                if(primarykeyRSet.next()){
                    int generated_message_id = (int) primarykeyRSet.getLong(1);
                    return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
                }

            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            return null;
        }
    
}
