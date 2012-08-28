package ru.testhf.srv3.h37945.dao;

import ru.testhf.srv3.h37945.domain.Game;

import java.sql.SQLException;
import java.util.List;

public interface GameDAO {
    public int addGame(Game game) throws SQLException;

    public List<Game> gamesList();

    public Game getGameById(int id) throws SQLException;

    public List<Game> gameListForUser(String login);

    public List<Game> completedGameListForUser(String login);

    public void changeMove(int id);

    public void setWinner(int id, String login);

    public void deleteGame(int id) throws SQLException;
}
