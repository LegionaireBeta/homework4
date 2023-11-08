package ru.itis.repository;

import ru.itis.model.User;

import java.sql.*;
import java.util.UUID;

public class AccountRepositoryJdbcImpl implements AccountRepository{

    private final Connection connection;

    private static final String SQL_INSERT = "INSERT INTO users(first_name, last_name, username, age, password_hash) VALUES ";

    public AccountRepositoryJdbcImpl(Connection connection){this.connection = connection;}

    @Override
    public void save(User user) throws SQLException {

        String sql = SQL_INSERT + "(?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getNameOfUser());
        preparedStatement.setString(2, user.getSurnameOfUser());
        preparedStatement.setString(3, user.getUsernameOfUser());
        preparedStatement.setInt(4, user.getAgeOfUser());
        preparedStatement.setString(5, user.getPasswordOfUser());

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
        String sql = "SELECT COUNT(*) FROM uuid_user WHERE uuid = ?";
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
    public UUID addUUID(String username, User user) throws SQLException {
        String sqlUser = "SELECT id FROM users WHERE username = ?";
        String insertSqlUuid = "INSERT INTO uuid_user(id, uuid) VALUES(?, ?)";

        UUID uuid = UUID.randomUUID();

        PreparedStatement preparedStatement1 = connection.prepareStatement(sqlUser);
        PreparedStatement preparedStatement2 = connection.prepareStatement(insertSqlUuid);

        preparedStatement1.setString(1, user.getUsernameOfUser());
        int id = 0;
        ResultSet resultSet = preparedStatement1.executeQuery();
        while (resultSet.next()){
            id = resultSet.getInt("id");
        }

        preparedStatement2.setInt(1,id);
        preparedStatement2.setObject(2, uuid);

        preparedStatement2.executeUpdate();
        return uuid;
    }
}
