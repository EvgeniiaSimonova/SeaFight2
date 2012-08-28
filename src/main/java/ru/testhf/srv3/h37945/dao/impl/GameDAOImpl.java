package ru.testhf.srv3.h37945.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;
import ru.testhf.srv3.h37945.dao.GameDAO;
import ru.testhf.srv3.h37945.domain.Game;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GameDAOImpl implements GameDAO{

    @Autowired
    private DriverManagerDataSource driverManagerDataSource;

    @Override
    public int addGame(Game game) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int id = -1;
        try {
            connection = driverManagerDataSource.getConnection();
            statement = connection.prepareStatement("insert into Games (firstLogin, secondLogin, isCompleted, winner, idFirstField, idSecondField, move) " +
                    "values (?, ?, false, '', ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, game.getFirstLogin());
            statement.setString(2, game.getSecondLogin());
            statement.setInt(3, game.getIdFirstField());
            statement.setInt(4, game.getIdSecondField());
            statement.setInt(5, 1);
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new SQLException("Can't create this game");
        } finally {
            closeAll(connection, statement, resultSet);
            return id;
        }
    }

    @Override
    public List<Game> gamesList() {
        List<Game> games = new ArrayList<Game>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = driverManagerDataSource.getConnection();
            statement = connection.prepareStatement("select * from Games");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Game game = new Game(resultSet.getInt("id"),
                        resultSet.getString("firstLogin"),
                        resultSet.getString("secondLogin"),
                        resultSet.getBoolean("isCompleted"),
                        resultSet.getString("winner"),
                        resultSet.getInt("idFirstField"),
                        resultSet.getInt("idSecondField"),
                        resultSet.getInt("move"));
                games.add(game);
            }
        } catch (SQLException e) {
        } finally {
            closeAll(connection, statement, resultSet);
            return games;
        }
    }

    @Override
    public Game getGameById(int id) throws SQLException {
        Game game = new Game();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = driverManagerDataSource.getConnection();
            statement = connection.prepareStatement("select * from Games where id = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                game = new Game(resultSet.getInt("id"),
                        resultSet.getString("firstLogin"),
                        resultSet.getString("secondLogin"),
                        resultSet.getBoolean("isCompleted"),
                        resultSet.getString("winner"),
                        resultSet.getInt("idFirstField"),
                        resultSet.getInt("idSecondField"),
                        resultSet.getInt("move"));
            }
        } catch (SQLException e) {
        } finally {
            closeAll(connection, statement, resultSet);
            if (game!=null) {
                return game;
            } else {
                throw new SQLException("Can't find Game with id = " + id);
            }
        }
    }

    @Override
    public List<Game> gameListForUser(String login) {
        List<Game> games = new ArrayList<Game>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = driverManagerDataSource.getConnection();
            statement = connection.prepareStatement("select * from Games where (firstLogin = ? OR secondLogin = ?) " +
                    "AND isCompleted = false");
            statement.setString(1, login);
            statement.setString(2, login);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Game game = new Game(resultSet.getInt("id"),
                        resultSet.getString("firstLogin"),
                        resultSet.getString("secondLogin"),
                        resultSet.getBoolean("isCompleted"),
                        resultSet.getString("winner"),
                        resultSet.getInt("idFirstField"),
                        resultSet.getInt("idSecondField"),
                        resultSet.getInt("move"));
                games.add(game);
            }
        } catch (SQLException e) {
        } finally {
            closeAll(connection, statement, resultSet);
            return games;
        }
    }

    @Override
    public List<Game> completedGameListForUser(String login) {
        List<Game> games = new ArrayList<Game>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = driverManagerDataSource.getConnection();
            statement = connection.prepareStatement("select * from Games where (firstLogin = ? OR secondLogin = ?) " +
                    "AND isCompleted = true");
            statement.setString(1, login);
            statement.setString(2, login);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Game game = new Game(resultSet.getInt("id"),
                        resultSet.getString("firstLogin"),
                        resultSet.getString("secondLogin"),
                        resultSet.getBoolean("isCompleted"),
                        resultSet.getString("winner"),
                        resultSet.getInt("idFirstField"),
                        resultSet.getInt("idSecondField"),
                        resultSet.getInt("move"));
                games.add(game);
            }
        } catch (SQLException e) {
        } finally {
            closeAll(connection, statement, resultSet);
            return games;
        }
    }

    @Override
    public void changeMove(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            Game game = getGameById(id);
            int move;
            if (game.getMove() == 1) {
                move = 2;
            } else {
                move = 1;
            }
            connection = driverManagerDataSource.getConnection();
            statement = connection.prepareStatement("update Games set move = ? where id = ?");
            statement.setInt(1, move);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {

        }finally {
            closeResourceConnection(connection);
            closeResourceStatement(statement);
        }
    }

    @Override
    public void setWinner(int id, String login) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = driverManagerDataSource.getConnection();
            statement = connection.prepareStatement("update Games set isCompleted = true where id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
            statement = connection.prepareStatement("update Games set winner = ? where id = ?");
            statement.setString(1, login);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("may be error");
        }finally {
            closeResourceConnection(connection);
            closeResourceStatement(statement);
        }
    }

    @Override
    public void deleteGame(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = driverManagerDataSource.getConnection();
            statement = connection.prepareStatement("delete from Games where id = ?");
            statement.setInt(1, id);
            int result = statement.executeUpdate();
            if (result == 0) {
                throw new SQLException("There are not game with id = " + id);
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            closeResourceConnection(connection);
            closeResourceStatement(statement);
        }
    }

    private void closeAll(Connection connection, PreparedStatement statement, ResultSet resultSet) {
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

    private void closeResourceStatement(PreparedStatement statement) {
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
