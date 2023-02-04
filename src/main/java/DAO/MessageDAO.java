package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
                preparedStatement.setLong(3, message.getTime_posted_epoch());

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

        public List<Message> getAllMessages(){
            Connection connection = ConnectionUtil.getConnection();
            List<Message>messages = new ArrayList<>();

            try{
                String sql = "Select * FROM message";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                ResultSet rs = preparedStatement.executeQuery();
                while(rs.next()){
                    Message MessageList = new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));  //getLong
                    messages.add(MessageList);

                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            return messages;
        }

        public Message getMessageById(int id){
            Connection connection = ConnectionUtil.getConnection();
        
            
            try {
                String sql = "SELECT * FROM message WHERE message_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setInt(1, id);
                

                ResultSet rs = preparedStatement.executeQuery();
                while(rs.next()){
                    Message message = new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));  //getLong
                    return message;
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            return null;

        }
        
}
