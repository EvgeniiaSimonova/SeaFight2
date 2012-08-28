package ru.testhf.srv3.h37945.service.dao;

import ru.testhf.srv3.h37945.domain.Game;

import java.sql.SQLException;
import java.util.List;

public interface GameService {
    public int addGame(Game game) throws SQLException;

    public List<Game> gamesList();

    public Game getGameById(int id) throws SQLException;

    public List<Game> gameListForUser(String login);

    public List<Game> completedGameListForUser(String login);

    public void changeMove(int id);

    public void setWinner(int id, String login);
}
