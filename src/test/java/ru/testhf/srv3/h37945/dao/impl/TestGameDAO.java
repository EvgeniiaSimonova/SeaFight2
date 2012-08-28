package ru.testhf.srv3.h37945.dao.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.testhf.srv3.h37945.dao.GameDAO;
import ru.testhf.srv3.h37945.domain.Game;

import javax.annotation.Resource;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-context.xml", "classpath:root-context.xml"})
public class TestGameDAO {
    @Resource
    private GameDAO gameDAO;

    private static int length;
    private static int id;

    @Before
    public void testLength() throws SQLException{
        length = gameDAO.gamesList().size();
        Game game = new Game("user1","user2",false,"",0,0);
        id = gameDAO.addGame(game);
        org.junit.Assert.assertEquals(length+1, gameDAO.gamesList().size());
    }

    @After
    public void deleteGame() throws SQLException{
        gameDAO.deleteGame(id);
        org.junit.Assert.assertEquals(length, gameDAO.gamesList().size());
    }

    @Test
    public void testGetGameById() throws SQLException {
        gameDAO.getGameById(id);
    }

    @Test
    public void testGameListForUser() throws SQLException {
        org.junit.Assert.assertEquals(1, gameDAO.gameListForUser("user1").size());
    }

    @Test
    public void testSetWinner() throws SQLException{
        gameDAO.setWinner(id, "user1");
        org.junit.Assert.assertTrue(gameDAO.getGameById(id).isCompleted());
    }

    @Test
    public void testCompletedGameListForUser() throws SQLException {
        gameDAO.setWinner(id, "user1");
        org.junit.Assert.assertEquals(1, gameDAO.completedGameListForUser("user1").size());
    }
}
