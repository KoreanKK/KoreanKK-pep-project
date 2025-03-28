package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import java.sql.*;
import java.util.*;

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
    AccountService accountService;
    MessageService messageService;
    public SocialMediaController() {
        accountService = new AccountService();
        messageService = new MessageService();
    }

    public Javalin startAPI() {
        /* Post to update server, get to gain information from the server */
        Javalin app = Javalin.create();
        ObjectMapper om = new ObjectMapper();

        app.post("/register", this::registerHandler); //1: user registration
        app.post("/login", this::loginHandler); //2: user login
        app.post("/messages", this::createMessageHandler); //3: create message
        app.get("/messages", this::retrieveAllMessageHandler); //4: retrieve all messages
        app.get("/messages/{message_id}", this::retrieveAllMessageByMessageIDHandler); //5: retrieve message by message_id
        app.delete("/messages/{message_id}", this::deleteMessageHandler); //6: delete Message by ID

        return app;
    }

    private void registerHandler(Context context) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(context.body(), Account.class);
        Account newAccount = accountService.register(account);
        if (newAccount == null) {
            //response status to 400?
            context.status(400);
        } else {
            context.json(newAccount);
        }
    }

    private void loginHandler(Context context) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(context.body(), Account.class);
        Account newAccount = accountService.login(account);
        if (newAccount == null) {
            //response status to 401?
            //System.out.println("TRIGGERED HERE ");
            context.status(401);
            //HTTP status?
        } else {
            context.json(newAccount);
        }
    }

    // 3. create message
    private void createMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(context.body(), Message.class);
        Message newMessage = messageService.createMessage(message);
        if (newMessage == null) {
            //response status to 400
            context.status(400);
        } else {
            context.json(newMessage);
        }
    }

    private void retrieveAllMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();

        List<Message> newMessage = messageService.retrieveAllMessage();

        context.json(om.writeValueAsString(newMessage));
    }

    private void retrieveAllMessageByMessageIDHandler(Context context) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();

        String messageId = context.pathParam("message_id");
        int a = Integer.parseInt(messageId);
        Message newMessage = messageService.retrieveAllMessageByMessageID(a);
        if (newMessage == null) {
            context.status(200);
        } else {
            context.json(om.writeValueAsString(newMessage));
        }
        
    }

    private void deleteMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        String messageId = context.pathParam("message_id");
        int a = Integer.parseInt(messageId);
        Message deletedMessage = messageService.deleteMessage(a);
        if (deletedMessage == null) {
            context.status(200);
        } else {
            context.json(om.writeValueAsString(deletedMessage));
        }
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

}