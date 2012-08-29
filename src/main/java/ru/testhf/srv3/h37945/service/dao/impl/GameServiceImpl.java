package ru.testhf.srv3.h37945.service.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.testhf.srv3.h37945.dao.GameDAO;
import ru.testhf.srv3.h37945.domain.Game;
import ru.testhf.srv3.h37945.service.dao.GameService;

import java.sql.SQLException;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameDAO gameDAO;

    public void setGameDAO(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    public int addGame(Game game) throws SQLException {
        return gameDAO.addGame(game);
    }

    public List<Game> gamesList() {
        return gameDAO.gamesList();
    }

    public Game getGameById(int id) throws SQLException {
        return gameDAO.getGameById(id);
    }

    public List<Game> gameListForUser(String login) {
        return gameDAO.gameListForUser(login);
    }

    public List<Game> completedGameListForUser(String login) {
        return gameDAO.completedGameListForUser(login);
    }

    public void changeMove(int id) {
        gameDAO.changeMove(id);
    }

    public void setWinner(int id, String login) {
        gameDAO.setWinner(id, login);
    }
}
