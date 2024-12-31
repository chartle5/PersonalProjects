package com.example.houseapprules;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfileTableAdapter implements DataStore{
    private static Connection connection;

    private final String db_url = "jdbc:derby:HouseRules";

    public ProfileTableAdapter(Boolean reset) throws SQLException {
            Statement statement = null;
            try{
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
                connection = DriverManager.getConnection(db_url);
                statement = connection.createStatement();
                if(reset){
                    try{
                        statement.execute("DROP TABLE Profiles");
                    } catch (SQLException e){}


                }
                statement.execute("CREATE TABLE Profiles ("
                        + "studentNumber VARCHAR(9) NOT NULL PRIMARY KEY, "
                        + "firstName VARCHAR(20), "
                        + "lastName VARCHAR(20), "
                        + "phoneNumber VARCHAR(20), "
                        + "DOB DATE, "
                        + "deductions DOUBLE NULL, "
                        + "account VARCHAR(20) NULL, "
                        + "FOREIGN KEY (account) REFERENCES Accounts(username)"
                        +")");


            } catch (SQLException | ClassNotFoundException e){
                e.printStackTrace();
            }

    }
    @Override
    public void addNewRecord(Object data) throws SQLException{
        Profile profile = (Profile) data;
        connection = DriverManager.getConnection(db_url);
        Statement statement = connection.createStatement();
        try{
            String command = "INSERT INTO Profiles(studentNumber, firstName, lastName, phoneNumber, DOB) "
                    + "VALUES ('"
                    + profile.getStudentNumber() + "', '"
                    + profile.getFirstName() + "', '"
                    + profile.getLastName() + "', '"
                    + profile.getPhoneNumber() + "', '"
                    + profile.getDOB() + " ')";
            statement.execute(command);
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();


        }

    }
    @Override
    public void updateRecord(Object data) throws SQLException{
        Profile profile = (Profile) data;
        connection = DriverManager.getConnection(db_url);
        Statement statement = connection.createStatement();
        String query = "UPDATE Profiles SET firstName = '" + profile.getFirstName() + "',"
                + "lastName = '" + profile.getLastName() + "',"
                + "phoneNumber = '" + profile.getPhoneNumber() + "',"
                + "DOB = '" + profile.getDOB() + "',"
                + "deductions = '" + profile.getDeductions() + "',"
                + "account = '" + profile.getAccount().getUsername() + "'" + "WHERE studentNumber = '" + profile.getStudentNumber() + "'";
        System.out.println("Executing update query: " + query);
        System.out.println(" Account username: " + profile.getAccount().getUsername());
        statement.executeUpdate(query);
        connection.close();

    }
    @Override
    public Object findRecord(String key) throws SQLException{
        Profile profile = new Profile();
        ResultSet rs;
        connection = DriverManager.getConnection(db_url);

        Statement statement = connection.createStatement();

        String command = "SELECT * FROM Profiles WHERE studentNumber = '" + key + "'";
        rs = statement.executeQuery(command);

        while(rs.next()){
            profile.setStudentNumber(rs.getString("studentNumber"));
            profile.setFirstName(rs.getString("firstName"));
            profile.setLastName(rs.getString("lastName"));
            profile.setPhoneNumber(rs.getString("phoneNumber"));
            profile.setDOB(rs.getDate("DOB"));
            String accountUsername = rs.getString("account");
            if (accountUsername != null){
                Account account = new Account();
                account.setUsername(accountUsername);
                profile.setAccount(account);

            }
        }
        connection.close();
        return profile;

    }

    @Override
    public Object findRecord(Object referencedObject) throws SQLException {
        return null;
    }

    @Override
    public void deleteOneRecord(String key) throws SQLException {
    }

    @Override
    public void deleteRecords(Object referencedObject) throws SQLException {

    }

    @Override
    public List<String> getKeys() throws SQLException {
        List<String> list = new ArrayList<>();
        ResultSet rs;
        connection = DriverManager.getConnection(db_url);
        Statement statement = connection.createStatement();
        String query = "SELECT studentNumber FROM Profiles";
        rs = statement.executeQuery(query);

        while (rs.next()){
            list.add(rs.getString(1));
        }
        connection.close();
        return list;
    }

    @Override
    public List<Object> getAllRecords() throws SQLException {
        return null;
    }

    @Override
    public List<Object> getAllRecords(Object referencedObject) throws SQLException {
        return null;
    }

}
