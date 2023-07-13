package DAO;

import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.*;

public class MessageDAO {
    
    public Message insertMessage(Message message){

        //1. get connection
        Connection connection = ConnectionUtil.getConnection();

        try{
            //2.prepare SQL
            String sql = "INSERT INTO Message(posted_by, message_text, time_posted_epoch) VALUES(?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());

            //3.execute SQL
            ps.executeUpdate();

            //4.process result
            ResultSet rs = ps.getGeneratedKeys();
            while(rs.next()){
                int generated_id = (int) rs.getLong(1);
                return new Message(generated_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }


    public List<Message> getAllMessage(){

        //1.get connection
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try{
            //2. prepare SQL
            String sql = "SELECT * FROM Message;";
            PreparedStatement ps = connection.prepareStatement(sql);

            //3.execute SQL
            ResultSet rs = ps.executeQuery();

            //4. process ResultSet
            while(rs.next()){
                messages.add(new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }


    public Message getMessageByID(int message_id){
        //1. get connection
        Connection connection = ConnectionUtil.getConnection();

        try{
            //2.prepare SQL
            String sql = "SELECT * FROM Message WHERE message_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, message_id);


            //3.execute SQL
            ResultSet rs = ps.executeQuery();

            //4.process result
            while(rs.next()){
                return new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch"));
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    public void deleteMessageByID(int message_id){

        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "DELETE FROM Message WHERE message_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, message_id);

            ps.executeUpdate();

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public Message updateMessageByID(int message_id, String message_text){

        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "UPDATE Message SET message_text = ? WHERE message_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, message_text);
            ps.setInt(2, message_id);

            ps.executeUpdate();

            //return result after message is updated
            return this.getMessageByID(message_id);

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<Message> getAllMessageByAccountID(int account_id){
         //1.get connection
         Connection connection = ConnectionUtil.getConnection();
         List<Message> messages = new ArrayList<>();
 
         try{
             //2. prepare SQL
             String sql = "SELECT * FROM Message WHERE posted_by = ?;";
             PreparedStatement ps = connection.prepareStatement(sql);
             ps.setInt(1, account_id);
 
             //3.execute SQL
             ResultSet rs = ps.executeQuery();
 
             //4. process ResultSet
             while(rs.next()){
                 messages.add(new Message(
                     rs.getInt("message_id"),
                     rs.getInt("posted_by"),
                     rs.getString("message_text"),
                     rs.getLong("time_posted_epoch")));
             }
         }catch(SQLException e){
             System.out.println(e.getMessage());
         }
         return messages;


    }
}