package Service;

import DAO.MessageDAO;
import java.util.List;
import Model.Message;

// * The purpose of a Service class is to contain "business logic" that sits between the web layer (controller) and
// * persistence layer (DAO). That means that the Service class performs tasks that aren't done through the web or
// * SQL: programming tasks like checking that the input is valid, conducting additional security checks, or saving the
// * actions undertaken by the API to a logging file.



public class MessageService {
    private MessageDAO messageDAO;
    

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message addMessage(Message message){
        //Checks that length is between 1 - 254 and that the account Id exists
        if(message.getMessage_text() != "" && message.getMessage_text().length() <= 254 && message.getPosted_by() != 0){
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

    public Message deleteMessageById(int id){       //Return message that was deleted, then delete. Null will be turned into empty reponse.
        
            Message message = messageDAO.getMessageById(id);
            messageDAO.deleteMessage(id);
        return message;
        }

    public Message patchMessage(Message message, int id){
        //checks to make sure the patch update of the message follows the rules of posting, and that the message does exist.
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
