package Controller;

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
    AccountService accountService;
    MessageService messageService;

    public Javalin startAPI() {
        /* Post to update server, get to gain information from the server */
        Javalin app = Javalin.create();
        ObjectMapper om = new ObjectMapper();



        app.post("/register", this::registerHandler); //1: user registration
        app.post("/login", this::loginHandler);
        app.post("/messages", this::createMessageHandler);
        
        return app;
    }


    private void registerHandler(Context context) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(context.body(), Account.class);
        if (accountService.register(account) == null) {
            //response status to 400?
            context.status(400);
        } else {
            context.json(account);
        }
    }

    private void loginHandler(Context context) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(context.body(), Account.class);
        if (accountService.login(account) == null) {
            //response status to 401?
            context.status(401);
            //HTTP status?
        } else {
            context.json(account);
        }
    }

    // 3. create message
    private void createMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(context.body(), Message.class);
        if (messageService.createMessage(message) == null) {
            //response status to 400
            context.status(400);
        } else {
            context.json(message);
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