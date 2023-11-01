package ru.itis.repository;

import ru.itis.model.User;

import java.sql.*;
import java.util.UUID;

public class AccountRepositoryJdbcImpl implements AccountRepository{

    private final Connection connection;

    private static final String SQL_INSERT = "INSERT INTO users(uuid, first_name, last_name, username, age, password_hash) VALUES ";


    public AccountRepositoryJdbcImpl(Connection connection){this.connection = connection;}

    @Override
    public void save(User user) throws SQLException {

        String sql = SQL_INSERT + "(?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1, user.getUuidOfUser());;
        preparedStatement.setString(2, user.getNameOfUser());
        preparedStatement.setString(3, user.getSurnameOfUser());
        preparedStatement.setString(4, user.getUsernameOfUser());
        preparedStatement.setInt(5, user.getAgeOfUser());
        preparedStatement.setString(6, user.getPasswordOfUser());

        preparedStatement.executeUpdate();
        System.out.println("Done");
    }

    public boolean login(String username, String password, User user) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getUsernameOfUser());
        ResultSet resultSet = preparedStatement.executeQuery();
        String userAcc = "";
        String passAcc = "";

        while (resultSet.next()){
            userAcc = resultSet.getString("username");
            passAcc = resultSet.getString("password_hash");
        }
        if(userAcc.equals(username) && passAcc.equals(password)){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public boolean findUUID(UUID uuid) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE uuid = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1, uuid);
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public UUID getUUID(String username, String password, User user) throws SQLException {

        String sql = "SELECT uuid FROM users WHERE username = ? AND password_hash = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getUsernameOfUser());
        preparedStatement.setString(2, user.getPasswordOfUser());

        ResultSet resultSet = preparedStatement.executeQuery();

        UUID uuidUser = null;

        while (resultSet.next()){
            uuidUser = (UUID) resultSet.getObject("uuid");
        }

        return uuidUser;
    }

}
