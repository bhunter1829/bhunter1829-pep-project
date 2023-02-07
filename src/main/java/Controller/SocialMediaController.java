//Social Media Blog API Project by Brenden Hunter
//Following REST architecture. All Tests passed, documentation where needed

package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;



public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;
   

     public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
     }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
      
      
        // As a user, I should be able to create a new Account on the endpoint POST localhost:8080/register. 
        app.post("register", this::postCreateAccount);

        //As a user, I should be able to verify my login on the endpoint POST localhost:8080/login.
        app.post("login", this::postLogin);

        //As a user, I should be able to submit a new post on the endpoint POST localhost:8080/messages.
        app.post("messages", this::postMessage);

        //As a user, I should be able to submit a GET request on the endpoint GET localhost:8080/messages.
        app.get("messages", this::getMessage);

        //As a user, I should be able to submit a GET request on the endpoint GET localhost:8080/messages/{message_id}.
        app.get("messages/{message_id}", this::getMessageById);

        //As a User, I should be able to submit a DELETE request on the endpoint DELETE localhost:8080/messages/{message_id}.
        app.delete("messages/{message_id}", this::deleteMessageById);

        //As a user, I should be able to submit a PATCH request on the endpoint PATCH localhost:8080/messages/{message_id}.
        app.patch("messages/{message_id}", this::patchMessage);

        //As a user, I should be able to submit a GET request on the endpoint GET localhost:8080/accounts/{account_id}/messages.
        app.get("accounts/{account_id}/messages", this::getByAccountId);

        return app;
    }


    ObjectMapper mapper = new ObjectMapper();
    //The purpose of every endpoint is explained above. All of these will run properly unless the Service class or DAO returns null.
    //Most if not all adjusting should be done in the service class. Making it easier for adding/changing things in the future.

    private void postCreateAccount(Context ctx) throws JsonProcessingException {        
        
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount!=null){
            ctx.json(addedAccount);
        }else{
            ctx.status(400);
        }
    }

    private void postLogin(Context ctx) throws JsonProcessingException { 
     
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account login = accountService.login(account);
        if(login!=null){
            ctx.json(login);
        }else{
            ctx.status(401);        //401 indicates improper authentication. Should return 401 instead of 400 if login endpoint is returned null.
        }
    }

    private void postMessage(Context ctx) throws JsonProcessingException {
        
        Message message = mapper.readValue(ctx.body(),Message.class);
        Message addMessage = messageService.addMessage(message);
        
        if(addMessage != null ){
            ctx.json(addMessage);
        }else{
            ctx.status(400);
        }
    }

    private void getMessage(Context ctx) throws JsonProcessingException {
        ctx.json(messageService.getAllMessages());
    }

    private void getMessageById(Context ctx) throws JsonProcessingException {
        
        int id = Integer.parseInt(ctx.pathParam("message_id")); 
        Message getMessage = messageService.getMessageById(id);
        if(getMessage !=null){
            ctx.json(getMessage);
        }else{
            ctx.status(400);
        }
    }

    private void deleteMessageById(Context ctx) throws JsonProcessingException {
        
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message deleteMessage = messageService.deleteMessageById(id);
        if(deleteMessage != null){
            ctx.json(deleteMessage);
        }else{
            ctx.json("");   //return empty response on null.
        }
    }

    private void patchMessage(Context ctx) throws JsonProcessingException {
        
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message update = messageService.patchMessage(message, id);
        if(update!=null){
            ctx.json(update);
        }else{
            ctx.status(400);        
        }

    }

    private void getByAccountId(Context ctx) throws JsonProcessingException {
        
        int id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> message = messageService.getAllByAccountId(id);
        if(message != null){
            ctx.json(message);
        }else {
            ctx.status(400);
        }

    }




}