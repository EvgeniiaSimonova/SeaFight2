package ru.testhf.srv3.h37945.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;
import ru.testhf.srv3.h37945.dao.FieldDAO;
import ru.testhf.srv3.h37945.domain.Field;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FieldDAOImpl implements FieldDAO {

    private static final Logger logger = LoggerFactory.getLogger(FieldDAOImpl.class);

    @Autowired
    private DriverManagerDataSource driverManagerDataSource;

    @Override
    public int addField(Field field) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int id = -1;
        try {
            connection = driverManagerDataSource.getConnection();
            statement = connection.prepareStatement("insert into Fields (ships, shots, isKilled) values (?, ?, false)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,field.getShips());
            statement.setString(2, field.getShots());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                id = resultSet.getInt(1);
                System.out.println("FieldDAOImpl id = " + id);
            }
        } catch (SQLException e) {
            throw new SQLException("Can't create new field");
        } finally {
            closeAll(connection, statement, resultSet);
            return id;
        }
    }

    @Override
    public Field getFieldById(int id) throws SQLException {
        Field field = new Field();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = driverManagerDataSource.getConnection();
            statement = connection.prepareStatement("select * from Fields where id = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                field = new Field(resultSet.getInt("id"),
                        resultSet.getString("ships"),
                        resultSet.getString("shots"),
                        resultSet.getBoolean("isKilled"));
            }
        } catch (SQLException e) {
        } finally {
            closeAll(connection, statement, resultSet);
            if (field != null) {
                return field;
            } else {
                throw new SQLException("Can't find field with id = " + id);
            }

        }
    }

    @Override
    public List<Field> fieldList() {
        List<Field> fields = new ArrayList<Field>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = driverManagerDataSource.getConnection();
            statement = connection.prepareStatement("select * from Fields");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Field field = new Field(resultSet.getInt("id"),
                        resultSet.getString("ships"),
                        resultSet.getString("shots"),
                        resultSet.getBoolean("isKilled"));
                fields.add(field);
            }
        } catch (SQLException e) {
        } finally {
            closeAll(connection, statement, resultSet);
            return fields;
        }
    }

    @Override
    public void setShips(int id, String ships) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = driverManagerDataSource.getConnection();
            statement = connection.prepareStatement("update Fields set ships = ? where id = ?");
            statement.setString(1, ships);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {

        }finally {
            closeResourceConnection(connection);
            closeResourceStatement(statement);
        }
    }

    @Override
    public void addShot(int id, int cell) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            Field field = getFieldById(id);
            String shots = field.getShots() + cell + ",";
            connection = driverManagerDataSource.getConnection();
            statement = connection.prepareStatement("update Fields set shots = ? where id = ?");
            statement.setString(1,shots);
            statement.setInt(2, id);
            statement.executeUpdate();
            if (isKilled(id)) {
                statement = connection.prepareStatement("update Fields set isKilled = true where id = ?");
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
        }finally {
            closeResourceConnection(connection);
            closeResourceStatement(statement);
        }
    }

    public boolean isKilled(int id) {
        List<String> list = new ArrayList<String>();

        try {
            Field field = getFieldById(id);
            String[] ships = field.getShips().split(",");
            String[] shots = field.getShots().split(",");
            for (int i = 0; i < ships.length; i++) {
                if (!ships[i].equals("")) {
                    list.add(ships[i]);
                }
            }

            for (int i = 0; i < shots.length; i++) {
                list.remove(shots[i]);
                logger.info("list "+ list.toString());
            }
        } catch (Exception e) {}
        if (list.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void deleteField(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = driverManagerDataSource.getConnection();
            statement = connection.prepareStatement("delete from Fields where id = ?");
            statement.setInt(1, id);
            int result = statement.executeUpdate();
            if (result == 0) {
                throw new SQLException("There are not field with id = " + id);
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
