package Service;

import DAO.MessageDAO;
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
        if(message.getMessage_text().length() > 255 || message.getMessage_text().isEmpty()){
            return null;
        }
        return messageDAO.insertMessage(message);
    }
    
}
