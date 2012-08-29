package ru.testhf.srv3.h37945.service.dao.impl;

import org.jmock.Mockery;
import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.testhf.srv3.h37945.dao.UserDAO;
import ru.testhf.srv3.h37945.domain.User;
import ru.testhf.srv3.h37945.service.dao.UserService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-context.xml", "classpath:root-context.xml"})
public class TestUserService {

    private Mockery context = new Mockery();

    @Test
    public void testSaveUser() throws SQLException {
        final UserDAO userDAO = context.mock(UserDAO.class);
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDAO(userDAO);

        final User user = new User("user", "password", "ROLE_USER");
        final User user2 = new User("user", "password", "role");  // experiment

        context.checking(new Expectations() {{
            oneOf (userDAO).saveUser(user);
        }});

        userService.saveUser(user);

        context.assertIsSatisfied();
    }

    @Test
    public void testUserList() {
        final UserDAO userDAO = context.mock(UserDAO.class);
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDAO(userDAO);

        context.checking(new Expectations() {{
            oneOf(userDAO).userList();
        }});

        userService.userList();

        context.assertIsSatisfied();
    }

    @Test
    public void testGetUserByLogin() throws SQLException{
        final UserDAO userDAO = context.mock(UserDAO.class);
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDAO(userDAO);

        final String login = "user";

        context.checking(new Expectations() {{
            oneOf(userDAO).getUserByLogin(login);
        }});

        userService.getUserByLogin(login);

        context.assertIsSatisfied();
    }

    @Test
    public void testDeleteUser() throws SQLException{
        final UserDAO userDAO = context.mock(UserDAO.class);
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDAO(userDAO);

        final String login = "user";

        context.checking(new Expectations() {{
            oneOf(userDAO).deleteUser(login);
        }});

        userService.deleteUser(login);

        context.assertIsSatisfied();
    }


    @Test
    public void testUpdateUser() throws SQLException{
        final UserDAO userDAO = context.mock(UserDAO.class);
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDAO(userDAO);

        final String login = "user";

        context.checking(new Expectations() {{
            oneOf(userDAO).updateUser(login);
        }});

        userService.updateUser(login);

        context.assertIsSatisfied();
    }
}
