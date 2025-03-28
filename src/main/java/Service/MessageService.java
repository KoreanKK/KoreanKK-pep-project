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

    public List<Message> retrieveAllMessageByMessageID (int messageID) {
        return messageDAO.RetrieveAllMessagesByMessageIDDAO(messageID);
    }
    

}
