package Service;

import DAO.MessageDAO;

import java.util.List;


import Model.Message;


public class MessageService {
    private MessageDAO messageDAO;
    

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message addMessage(Message message){
        if(message.getMessage_text().length() > 0 && message.getMessage_text().length() < 255 && message.getPosted_by() >=0){
            return messageDAO.insertMessage(message);
        }
            return null;
        
         
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int id) {
        return messageDAO.getMessageById(id);
    }

    public Message deleteMessageById(int id){
        
            Message message = messageDAO.getMessageById(id);
            messageDAO.deleteMessage(id);
        return message;
        }

    public Message patchMessage(Message message, int id){
         if(message.getMessage_text().length() > 0 && message.getMessage_text().length() < 255 && messageDAO.getMessageById(id) != null){
           messageDAO.updateMessage(message, id);
           return messageDAO.getMessageById(id);
         }
         return null;
    }

    public List<Message> getAllByAccountId(int id){

           return messageDAO.getAllMessagesByAccount(id);
       
    }
}
