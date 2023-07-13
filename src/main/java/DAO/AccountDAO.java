package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;

public class AccountDAO {
    

    // Insert an account into the Account table.
    public Account insertAccount(Account account){
        //1. get connection
        Connection connection = ConnectionUtil.getConnection();
        try{

            //2. prepare SQL statment
            String sql = "INSERT INTO Account(username, password) VALUES(?,?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            //3. execute SQL
            ps.executeUpdate();

            //4. process ResultSet
            ResultSet rs = ps.getGeneratedKeys();
            while(rs.next()){
                int generated_id = (int) rs.getLong(1);
                return new Account(generated_id, account.getUsername(), account.getPassword());
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountByUsername(Account account){
        //1. get connection
        Connection connection = ConnectionUtil.getConnection();
        try{
            //2. prepare SQL
            String sql = "SELECT * FROM Account WHERE username = ? AND password = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            //3. execute SQL
            ResultSet rs = ps.executeQuery();

            //4. process ResultSet
            while(rs.next()){
                return new Account(rs.getInt("account_id"), 
                                    rs.getString("username"), 
                                    rs.getString("password"));
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }
}
