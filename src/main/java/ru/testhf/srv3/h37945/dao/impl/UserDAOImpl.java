package ru.testhf.srv3.h37945.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;
import ru.testhf.srv3.h37945.dao.UserDAO;
import ru.testhf.srv3.h37945.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {


    @Autowired
    private DriverManagerDataSource driverManagerDataSource;

    @Override
    public void saveUser(User user) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = driverManagerDataSource.getConnection();
            statement = connection.prepareStatement("insert into Users (login, password, role) values (?, ?, ?)");
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("You can't use this login.");
        } finally {
            closeResourceConnection(connection);
            closeResourceStatement(statement);
        }
    }

    @Override
    public List<User> userList() {
        List<User> users = new ArrayList<User>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = driverManagerDataSource.getConnection();
            statement = connection.prepareStatement("select * from Users");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User(resultSet.getString("login"),
                                     resultSet.getString("password"),
                                     resultSet.getString("role"));
                users.add(user);
            }
        } catch (SQLException e) {
        } finally {
            closeAll(connection, statement, resultSet);
            return users;
        }
    }

    @Override
    public User getUserByLogin(String login) throws SQLException{
        User user = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = driverManagerDataSource.getConnection();
            statement = connection.prepareStatement("select * from Users where login = ?");
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = new User(resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("role"));
            }
        } catch (SQLException e) {
        } finally {
            closeAll(connection, statement, resultSet);
            if (user!=null) {
                return user;
            } else {
                throw new SQLException("This user is absent in our database");
            }
        }
    }

    @Override
    public void deleteUser(String login) throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = driverManagerDataSource.getConnection();
            statement = connection.prepareStatement("delete from Users where login = ?");
            statement.setString(1, login);
            int result = statement.executeUpdate();
            if (result == 0) {
                throw new SQLException("There are not user with login = " + login);
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            closeResourceConnection(connection);
            closeResourceStatement(statement);
        }

    }

    @Override
    public void updateUser(String login) throws SQLException{
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = driverManagerDataSource.getConnection();
            statement = connection.prepareStatement("update Users set role = 'ROLE_USER, ROLE_ADMIN' where login = ?");
            statement.setString(1, login);
            int result = statement.executeUpdate();
            if (result == 0) {
                throw new SQLException("There are not user with login = " + login);
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }finally {
            closeResourceConnection(connection);
            closeResourceStatement(statement);
        }
    }

    @Override
    public boolean isUserExist(String login) {
        return false;
    }

    private void closeAll(Connection connection, Statement statement, ResultSet resultSet) {
        closeResourceResultSet(resultSet);
        closeResourceStatement(statement);
        closeResourceConnection(connection);
    }

    private void closeResourceResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (Exception e) {
            }
        }
    }

    private void closeResourceStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (Exception e) {
            }
        }
    }

    private void closeResourceConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
            }
        }
    }
}
