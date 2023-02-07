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
                //Generates key, assigns values and returns the new message information.
                preparedStatement.setInt(1, message.getPosted_by());
                preparedStatement.setString(2, message.getMessage_text());
                preparedStatement.setLong(3, message.getTime_posted_epoch());

                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if(rs.next()){
                    int generated_message_id = (int) rs.getLong(1);
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
                        rs.getLong("time_posted_epoch"));  
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
                        rs.getLong("time_posted_epoch"));  
                    return message;
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            return null;

        }

        public void deleteMessage(int id){
            Connection connection = ConnectionUtil.getConnection();

            try{
                String sql = "DELETE FROM message WHERE message_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setInt(1, id);

                preparedStatement.executeUpdate();
           
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            
        }


        public void  updateMessage(Message message, int id){
            Connection connection = ConnectionUtil.getConnection();
        
            
            try {
                String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, message.getMessage_text());
                preparedStatement.setInt(2, id);
                
                preparedStatement.executeUpdate();

                
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }

        public List <Message> getAllMessagesByAccount(int id){
            Connection connection = ConnectionUtil.getConnection();
            List<Message>messages = new ArrayList<>();

            try{
                String sql = "Select * FROM message WHERE posted_by = ?";       //We use postedby since it's a foriegn key to accountid
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setInt(1, id);
                
                ResultSet rs = preparedStatement.executeQuery();
                while(rs.next()){
                    Message MessageList = new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));  
                    messages.add(MessageList);

                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            return messages;
        }
        
}
