package ru.testhf.srv3.h37945.dao.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.testhf.srv3.h37945.dao.UserDAO;
import ru.testhf.srv3.h37945.domain.User;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-context.xml", "classpath:root-context.xml"})
public class TestUserDAO {

    private int length = 0;
    private final String login = "user";

    @Resource
    private UserDAO userDAO;

    @Before
    public void testLength() {
        List<User> users = userDAO.userList();
        length = users.size();
    }

    @Test
    public void testSaveUser() throws SQLException {
        User user = new User("user", "password", "role");
        userDAO.saveUser(user);
        org.junit.Assert.assertEquals(length+1, userDAO.userList().size());
    }

    @Test
    public void testUpdateUser() throws SQLException {
        userDAO.updateUser(login);
    }

    @Test
    public void testGetUserByLogin() throws SQLException {
        userDAO.getUserByLogin(login);
    }

    @Test
    public void testDeleteUser() throws SQLException {
        userDAO.deleteUser(login);
        org.junit.Assert.assertEquals(length-1, userDAO.userList().size());
    }
}
