package ru.testhf.srv3.h37945.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;
import ru.testhf.srv3.h37945.dao.RequestDAO;
import ru.testhf.srv3.h37945.domain.Request;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RequestDAOImpl implements RequestDAO {

    @Autowired
    private DriverManagerDataSource driverManagerDataSource;

    @Override
    public int addRequest(Request request) throws SQLException {
        int id = -1;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = driverManagerDataSource.getConnection();
            statement = connection.prepareStatement("insert into Requests (firstLogin, secondLogin, state, idGame)" +
                    "values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, request.getFirstLogin());
            statement.setString(2, request.getSecondLogin());
            statement.setInt(3, request.getState());
            statement.setInt(4, request.getIdGame());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new SQLException("Can't create this request");
        } finally {
            closeAll(connection, statement, resultSet);
            return id;
        }
    }

    @Override
    public List<Request> requestList() {
        List<Request> requests = new ArrayList<Request>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = driverManagerDataSource.getConnection();
            statement = connection.prepareStatement("select * from Requests");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Request request = new Request(resultSet.getInt("id"),
                        resultSet.getString("firstLogin"),
                        resultSet.getString("secondLogin"),
                        resultSet.getInt("state"),
                        resultSet.getInt("idGame"));
                requests.add(request);
            }
        } catch (SQLException e) {
        } finally {
            closeAll(connection, statement, resultSet);
            return requests;
        }
    }

    @Override
    public Request getRequestById(int id) throws SQLException {
        Request request = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = driverManagerDataSource.getConnection();
            statement = connection.prepareStatement("select * from Requests where id = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                request = new Request(resultSet.getInt("id"),
                        resultSet.getString("firstLogin"),
                        resultSet.getString("secondLogin"),
                        resultSet.getInt("state"),
                        resultSet.getInt("idGame"));
            }
        } catch (SQLException e) {
        } finally {
            closeAll(connection, statement, resultSet);
            if (request!=null) {
                return request;
            } else {
                throw new SQLException("Can't find request with id = " + id);
            }
        }
    }

    @Override
    public void updateRequest(int id, int state, int idGame) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = driverManagerDataSource.getConnection();
            statement = connection.prepareStatement("update Requests set state = ? where id = ?");
            statement.setInt(1, state);
            statement.setInt(2, id);
            statement.executeUpdate();

            statement = connection.prepareStatement("update Requests set idGame = ? where id = ?");
            statement.setInt(1, idGame);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {

        }finally {
            closeResourceConnection(connection);
            closeResourceStatement(statement);
        }
    }

    @Override
    public List<Request> requestsForUser(String login) {
        List<Request> requests = new ArrayList<Request>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = driverManagerDataSource.getConnection();
            statement = connection.prepareStatement("select * from Requests where secondLogin = ? AND state = 0");
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Request request = new Request(resultSet.getInt("id"),
                        resultSet.getString("firstLogin"),
                        resultSet.getString("secondLogin"),
                        resultSet.getInt("state"),
                        resultSet.getInt("idGame"));
                requests.add(request);
            }
        } catch (SQLException e) {
        } finally {
            closeAll(connection, statement, resultSet);
            return requests;
        }
    }

    @Override
    public void deleteRequest(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = driverManagerDataSource.getConnection();
            statement = connection.prepareStatement("delete from Requests where id = ?");
            statement.setInt(1, id);
            int result = statement.executeUpdate();
            if (result == 0) {
                throw new SQLException("There are not request with id = " + id);
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            closeResourceConnection(connection);
            closeResourceStatement(statement);
        }
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