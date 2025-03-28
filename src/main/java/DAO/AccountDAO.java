package DAO;

import Util.ConnectionUtil;
import Model.Account;
import Model.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    
    public Account registerDAO(Account account) { //finished
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                int generated_account_id = (int)rs.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account loginDAO(Account account) { //revisit
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getUsername());
            //preparedStatement.executeUpdate();
            //ResultSet rs = preparedStatement.getGeneratedKeys();
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                //int generated_account_id = rs.getInt(1);
                if (rs.getString("username").equals(account.getUsername()) && rs.getString("password").equals(account.getPassword())) {
                    int generated_account_id = (int)rs.getLong(1);
                    return new Account(generated_account_id, account.getUsername(), account.getPassword());
                }
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


}
