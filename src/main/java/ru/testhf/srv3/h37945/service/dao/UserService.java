package ru.testhf.srv3.h37945.service.dao;

import ru.testhf.srv3.h37945.domain.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    public void saveUser(User user) throws SQLException;

    public List<User> userList();

    public User getUserByLogin(String login) throws SQLException;

    public void deleteUser(String login) throws SQLException;

    public void updateUser(String login) throws SQLException;

    public boolean isUserExist(String login);
}
