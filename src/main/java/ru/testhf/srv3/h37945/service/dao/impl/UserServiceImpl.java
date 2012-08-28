package ru.testhf.srv3.h37945.service.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.testhf.srv3.h37945.dao.UserDAO;
import ru.testhf.srv3.h37945.domain.User;
import ru.testhf.srv3.h37945.service.dao.UserService;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    public void saveUser(User user) throws SQLException {
        userDAO.saveUser(user);
    }

    public List<User> userList() {
        return userDAO.userList();
    }

    public User getUserByLogin(String login) throws SQLException{
        return userDAO.getUserByLogin(login);
    }

    public void deleteUser(String login) throws SQLException {
        userDAO.deleteUser(login);
    }

    public void updateUser(String login) throws SQLException {
        userDAO.updateUser(login);
    }

    public boolean isUserExist(String login) {
        return userDAO.isUserExist(login);
    }
}
