package com.await.bdpw10.da;

import com.await.bdpw10.da.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public void addUser(User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO users(name, email, password) VALUES (?, ?, ?)");
        statement.setString(1, user.getName());
        statement.setString(2, user.getEmail());
        statement.setString(3, user.getPassword());
        statement.executeUpdate();
    }

    public void updateUser(User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE users SET name=?, email=?, password=? WHERE id=?");
        statement.setString(1, user.getName());
        statement.setString(2, user.getEmail());
        statement.setString(3, user.getPassword());
        statement.setInt(4, user.getId());
        statement.executeUpdate();
    }

    public void deleteUser(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id=?");
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    public User getUserById(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id=?");
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            return new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"));
        }
        return null;
    }

    public List<User> getAllUsers() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
        ResultSet rs = statement.executeQuery();
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            users.add(new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("password")));
        }
        return users;
    }
}



