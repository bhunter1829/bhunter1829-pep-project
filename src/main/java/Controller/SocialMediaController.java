package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

     public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
     }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
       // app.get("example-endpoint", this::exampleHandler);
        //test
      
        // As a user, I should be able to create a new Account on the endpoint POST localhost:8080/register. 
        app.post("register", this::postCreateAccount);
        //As a user, I should be able to verify my login on the endpoint POST localhost:8080/login.
        app.post("login", this::postLogin);
        //As a user, I should be able to submit a new post on the endpoint POST localhost:8080/messages.
        app.post("messages", this::postMessage);
        //As a user, I should be able to submit a GET request on the endpoint GET localhost:8080/messages.

        //As a user, I should be able to submit a GET request on the endpoint GET localhost:8080/messages/{message_id}.

        //As a User, I should be able to submit a DELETE request on the endpoint DELETE localhost:8080/messages/{message_id}.

        //As a user, I should be able to submit a PATCH request on the endpoint PATCH localhost:8080/messages/{message_id}.

        //As a user, I should be able to submit a GET request on the endpoint GET localhost:8080/accounts/{account_id}/messages.
        
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    // private void exampleHandler(Context context) {
    //     context.json("sample text");
    // }
    ObjectMapper mapper = new ObjectMapper();

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
            ctx.status(401);        //401 indicates improper authentication. Should return 401 instead of 400 in login endpoint if returned null
        }
    }

    private void postMessage(Context ctx) throws JsonProcessingException {
        
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addMessage = messageService.addMessage(message);
        
        if(message != null){
            ctx.json(addMessage);
        }else{
            ctx.status(400);
        }
    }


}