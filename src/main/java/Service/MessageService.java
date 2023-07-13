package Service;

import DAO.MessageDAO;
import Model.Message;
import java.util.*;

public class MessageService {

    private MessageDAO messageDAO;

    public MessageService(){
        this.messageDAO = new MessageDAO();
    }

    public Message createMessage(Message message){
        return messageDAO.insertMessage(message);
    }

    public List<Message> readAllMessage(){
        return messageDAO.getAllMessage();
    }

    public Message readMessageByID(int message_id){
        return messageDAO.getMessageByID(message_id);
    }

    public Message deleteMessageByID(int message_id){
        Message message = messageDAO.getMessageByID(message_id);

        if(message != null){
            messageDAO.deleteMessageByID(message_id);
            return message;
        }
        return null;
    }

    public Message updateMessageByID(int message_id, String message_text) {
        Message message = messageDAO.getMessageByID(message_id);

        if(message != null){
            return messageDAO.updateMessageByID(message_id, message_text);
        }
        return null;
    }

    public List<Message> readAllMessageByAccountID(int account_id){
        return messageDAO.getAllMessageByAccountID(account_id);
    }
}
