package Service;

import Model.Account;
import Model.Message;
import DAO.SocialDAO;
import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;

public class MessageService {
    
    MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    //3. creation of new message
    public Message createMessage (Message message) {
        if (message.getMessage_text() == "") {
            return null;
        } else if (message.getMessage_text().length() > 255) {
            return null;
        }
        //check if posted_by refers to a real existing user?
        return messageDAO.createMessageDAO(message); //
    }

    public List<Message> retrieveAllMessage () {
        return messageDAO.RetrieveAllMessagesDAO();
    }

    public Message retrieveAllMessageByMessageID (int messageID) {
        return messageDAO.RetrieveAllMessagesByMessageIDDAO(messageID);
    }

    public Message deleteMessage(int messageID) {
        return messageDAO.deleteMessageDAO(messageID);
    }

    public Message updateMessage(int id, String text) {
        /* 
        int messageLength = text.length();
        if (messageLength > 255 || messageLength == 0) {
            return null;
        }
        */
        Message message = messageDAO.RetrieveAllMessagesByMessageIDDAO(id);

        if (message == null || text.isBlank() || text.length() > 255) {
            return null;
        }
        message.setMessage_text(text);
        return messageDAO.updateMessageDAO(message);
    }

    public List<Message> retrieveAllMessageByAccountID (int accountID) {
        return messageDAO.RetrieveAllMessagesByAccountIDDAO(accountID);
    }
    

}
