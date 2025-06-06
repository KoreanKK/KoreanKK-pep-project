package DAO;

import Util.ConnectionUtil;
import Model.Account;
import Model.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    
    public boolean userExists(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account INNER JOIN message ON account.account_id = message.posted_by WHERE message.posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, message.getPosted_by());
            //preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Message createMessageDAO(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        if (userExists(message) == false) {
            return null;
        }
        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                int generated_message_id = (int)rs.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<Message> RetrieveAllMessagesDAO() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()) {
                Message foundMessage = new Message(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getLong(4));
                messages.add(foundMessage);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message RetrieveAllMessagesByMessageIDDAO(int messageID) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, messageID);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                Message foundMessage = new Message(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getLong(4));
                //messages.add(foundMessage);
                return foundMessage;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessageDAO(int messageID) {
        Connection connection = ConnectionUtil.getConnection();
        //Message foundMessage = null;
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, messageID);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                //Message foundMessage = new Message(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getLong(4));
                //messages.add(foundMessage);
                //return foundMessage;
                String sql2 = "DELETE FROM message WHERE message_id = ?";
                PreparedStatement ps2 = connection.prepareStatement(sql2);
                ps2.setInt(1, messageID);

                Message foundMessage = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                ps2.executeUpdate();
                return foundMessage;
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message updateMessageDAO(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        //Message foundMessage = null;
        int messageID = message.getMessage_id();
        String messageText = message.getMessage_text();
        Long messageTime = message.getTime_posted_epoch();
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, messageID);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                String sql2 = "UPDATE message SET message_text = ?, time_posted_epoch = ? WHERE message_id = ?";
                PreparedStatement ps2 = connection.prepareStatement(sql2);
                ps2.setString(1, messageText);
                ps2.setLong(2, messageTime);
                ps2.setInt(3, messageID);

                Message foundMessage = new Message(messageID, rs.getInt("posted_by"), messageText, messageTime);

                ps2.executeUpdate();
                return foundMessage;
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> RetrieveAllMessagesByAccountIDDAO(int accountIn) {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountIn);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()) {
                Message foundMessage = new Message(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getLong(4));
                messages.add(foundMessage);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

}
