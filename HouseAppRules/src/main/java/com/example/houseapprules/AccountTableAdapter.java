package com.example.houseapprules;

import java.sql.*;
import java.util.List;

public class AccountTableAdapter implements DataStore{
    private Connection connection;
    private String db_url = "jdbc:derby:HouseRules";

    public AccountTableAdapter(boolean reset) throws SQLException {
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(db_url);
            statement = connection.createStatement();
            if (reset) {
                try {
                    statement.execute("DROP TABLE Accounts");
                } catch (SQLException e) {
                }
            }

            statement.execute("CREATE TABLE Accounts ("
                    + "username VARCHAR(20) NOT NULL PRIMARY KEY, "
                    + "password VARCHAR(20), "
                    + "accountType VARCHAR(20), "
                    + ")");

        } catch (SQLException e) {
        }
    }
    @Override
    public void addNewRecord(Object data) throws SQLException{
        Account account = (Account) data;
        connection = DriverManager.getConnection(db_url);
        Statement statement = connection.createStatement();
        try{
            String command = "INSERT INTO Accounts(username, password, accountType) "
                    + "VALUES ('"
                    + account.getUsername() + "', '"
                    + account.getPassword() + "', '"
                    + account.getAccountType() + "')";
            statement.execute(command);
            connection.close();
        }catch (SQLException e){
        }
    }
    @Override
    public void updateRecord(Object data){

    }
    @Override
    public Object findRecord(String key) throws SQLException{
        Account account = new Account();
        ResultSet rs;
        connection = DriverManager.getConnection(db_url);
        Statement statement = connection.createStatement();

        String command = "SELECT * FROM Accounts WHERE username = '" + key + "' ";
        rs = statement.executeQuery(command);

        while (rs.next()){
            account.setUsername(rs.getString(1));
            account.setPassword(rs.getString(2));
            account.setAccountType(rs.getString(3));
        }
        connection.close();
        return account;
    }

    @Override
    public Object findRecord(Object referencedObject) throws SQLException {
        // Implement this method
        return null;
    }

    @Override
    public void deleteOneRecord(String key) throws SQLException {
        // Implement this method
    }

    @Override
    public void deleteRecords(Object referencedObject) throws SQLException {
        // Implement this method
    }

    @Override
    public List<String> getKeys() throws SQLException {
        // Implement this method
        return null;
    }

    @Override
    public List<Object> getAllRecords() throws SQLException {
        // Implement this method
        return null;
    }

    @Override
    public List<Object> getAllRecords(Object referencedObject) throws SQLException {
        // Implement this method
        return null;
    }



}
