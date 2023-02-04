package Service;

import DAO.MessageDAO;

import java.util.List;

import DAO.AccountDAO;
import Model.Message;


public class MessageService {
    private MessageDAO messageDAO;
    private AccountDAO accountDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message addMessage(Message message){
        // if(message.getMessage_text() == "" || message.getMessage_text().length() >= 255 || accountDAO.getByUserId(message.getPosted_by()) == null){
        //     return null;
        // }
        
        //500 error, 3 things tests reporting 500 and not getting filtered here.
     
        return messageDAO.insertMessage(message);
       
        
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int id) {
        return messageDAO.getMessageById(id);
    }
    
}
