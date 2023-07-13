package Controller;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();

        //routes for Account
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::postLoginHandler);

        //routes for Message
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getMessageHandler);
        app.get("/messages/{message_id}", this::getMessageByIDHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIDHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIDHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessageByAccountIDHandler);

        return app;
    }

    ////////////////////////////////ACCOUNT/////////////////////////////////////
    private void postAccountHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);

        Account createAccount = accountService.createAccount(account);
        if(createAccount != null){
            ctx.json(mapper.writeValueAsString(createAccount));
        } else {
            ctx.status(400);
        }
    }

    private void postLoginHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);

        Account readAccount = accountService.readAccount(account);
        if(readAccount != null)
        {
            ctx.json(mapper.writeValueAsString(readAccount));  
        } else {
            ctx.status(401);
        }

    }

    ////////////////////////////////MESSAGE//////////////////////////////////////
    private void postMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);

        Message createMessage = messageService.createMessage(message);
        if(createMessage != null){
            ctx.json(mapper.writeValueAsString(createMessage));
        } else {
            ctx.status(400);
        }
    }

    private void getMessageHandler(Context ctx) throws JsonProcessingException{
        ctx.json(messageService.readAllMessage());
    }

    private void getMessageByIDHandler(Context ctx) throws JsonProcessingException{
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.readMessageByID(message_id);
        if(message != null){
            ctx.json(message);
        } else {
            ctx.status(200);
        }
        
    }

    public void deleteMessageByIDHandler(Context ctx) throws JsonProcessingException{
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.deleteMessageByID(message_id);
        if(message != null){
            ctx.json(message);
        } else {

            ctx.result();
        }
    }


    public void updateMessageByIDHandler(Context ctx) throws JsonProcessingException{

        ObjectMapper mapper = new ObjectMapper();
        
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = mapper.readValue(ctx.body(), Message.class);
        String message_text = message.getMessage_text();

        Message updateMessage = messageService.updateMessageByID(message_id, message_text);

        if(updateMessage != null){
            ctx.json(updateMessage);
        } else {
            ctx.status(400);
        }
    }

    public void getAllMessageByAccountIDHandler(Context ctx) throws JsonProcessingException{

        int account_id = Integer.parseInt(ctx.pathParam("account_id"));

        ctx.json(messageService.readAllMessageByAccountID(account_id));
    }

}