package ru.testhf.srv3.h37945.service.dao.impl;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.testhf.srv3.h37945.dao.GameDAO;
import ru.testhf.srv3.h37945.domain.Game;

import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-context.xml", "classpath:root-context.xml"})
public class TestGameService {

    private Mockery context = new Mockery();

    @Test
    public void testAddGame() throws SQLException{
        final GameDAO gameDAO = context.mock(GameDAO.class);
        GameServiceImpl gameService = new GameServiceImpl();
        gameService.setGameDAO(gameDAO);

        final Game game = new Game("user1", "user2", false, "", 1, 2);

        context.checking(new Expectations() {{
            oneOf(gameDAO).addGame(game);
        }});

        gameService.addGame(game);

        context.assertIsSatisfied();
    }

    @Test
    public void testGameList() {
        final GameDAO gameDAO = context.mock(GameDAO.class);
        GameServiceImpl gameService = new GameServiceImpl();
        gameService.setGameDAO(gameDAO);

        context.checking(new Expectations() {{
            oneOf(gameDAO).gamesList();
        }});

        gameService.gamesList();

        context.assertIsSatisfied();
    }

    @Test
    public void testGetGameById() throws SQLException{
        final GameDAO gameDAO = context.mock(GameDAO.class);
        GameServiceImpl gameService = new GameServiceImpl();
        gameService.setGameDAO(gameDAO);

        final int id = 1;

        context.checking(new Expectations() {{
            oneOf(gameDAO).getGameById(id);
        }});

        gameService.getGameById(id);

        context.assertIsSatisfied();
    }

    @Test
    public void testGameListForUser() {
        final GameDAO gameDAO = context.mock(GameDAO.class);
        GameServiceImpl gameService = new GameServiceImpl();
        gameService.setGameDAO(gameDAO);

        final String login = "user";

        context.checking(new Expectations() {{
            oneOf(gameDAO).gameListForUser(login);
        }});

        gameService.gameListForUser(login);

        context.assertIsSatisfied();
    }

    @Test
    public void testCompletedGameListForUser() {
        final GameDAO gameDAO = context.mock(GameDAO.class);
        GameServiceImpl gameService = new GameServiceImpl();
        gameService.setGameDAO(gameDAO);

        final String login = "user";

        context.checking(new Expectations() {{
            oneOf(gameDAO).completedGameListForUser(login);
        }});

        gameService.completedGameListForUser(login);

        context.assertIsSatisfied();
    }

    @Test
    public void testChangeMove() {
        final GameDAO gameDAO = context.mock(GameDAO.class);
        GameServiceImpl gameService = new GameServiceImpl();
        gameService.setGameDAO(gameDAO);

        final int id = 1;

        context.checking(new Expectations() {{
            oneOf(gameDAO).changeMove(id);
        }});

        gameService.changeMove(id);

        context.assertIsSatisfied();
    }

    @Test
    public void testSetWinner() {
        final GameDAO gameDAO = context.mock(GameDAO.class);
        GameServiceImpl gameService = new GameServiceImpl();
        gameService.setGameDAO(gameDAO);

        final int id = 1;
        final String login = "user";

        context.checking(new Expectations() {{
            oneOf(gameDAO).setWinner(id, login);
        }});

        gameService.setWinner(id, login);

        context.assertIsSatisfied();
    }
}
