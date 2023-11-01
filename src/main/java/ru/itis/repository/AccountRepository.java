package ru.itis.repository;

import ru.itis.model.User;

import java.sql.SQLException;
import java.util.UUID;

public interface AccountRepository {

    void save (User user) throws SQLException;
    boolean login(String username, String password, User user) throws SQLException;

    boolean findUUID(UUID uuid) throws SQLException;

    UUID getUUID(String username, String password, User user) throws SQLException;

}
