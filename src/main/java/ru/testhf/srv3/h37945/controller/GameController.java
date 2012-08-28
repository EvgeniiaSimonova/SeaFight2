package ru.testhf.srv3.h37945.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.testhf.srv3.h37945.domain.Field;
import ru.testhf.srv3.h37945.domain.Game;
import ru.testhf.srv3.h37945.domain.lists.GameList;
import ru.testhf.srv3.h37945.forms.LoginForm;
import ru.testhf.srv3.h37945.forms.TableForm;
import ru.testhf.srv3.h37945.service.dao.FieldService;
import ru.testhf.srv3.h37945.service.dao.GameService;

import java.sql.SQLException;
import java.util.List;

@Controller
public class GameController {

    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    @Autowired
    private GameService gameService;

    @Autowired
    private FieldService fieldService;

    @ModelAttribute(value = "completedGameList")
    public GameList getCompletedGames() {
        List<Game> games = gameService.
                completedGameListForUser(SecurityContextHolder.getContext().getAuthentication().getName());
        GameList gameList = new GameList(games);
        return gameList;
    }

    @ModelAttribute(value = "continuedGameList")
    public GameList getContinuedGames() {
        List<Game> games = gameService.
                gameListForUser(SecurityContextHolder.getContext().getAuthentication().getName());
        GameList gameList = new GameList(games);
        return gameList;
    }

    @RequestMapping(value = "/game/mygames/continued/", method = RequestMethod.GET)
    public String getContinuedGamesPage(ModelMap model) {
        LoginForm loginForm = new LoginForm();
        model.put("loginForm", loginForm);
        return "gamePages/continuedGames";
    }

    @RequestMapping(value = "/game/mygames/continued/", method = RequestMethod.POST)
    public String checkContinuedGamesPage(LoginForm loginForm, ModelMap model) {
        String login = loginForm.getLogin();
        if (!login.equals("")) {
            int id = 0;
            try {
                id = Integer.parseInt(login);
                if (!getContinuedGames().isIdIncludeList(id)) {
                    model.put("Error", "Wrong id");
                    loginForm.setLogin("");
                    throw new SQLException("Wrong id of game");
                }
                Game game = gameService.getGameById(id);
                Field field1 = fieldService.getFieldById(game.getIdFirstField());
                Field field2 = fieldService.getFieldById(game.getIdSecondField());
                if (!field1.getShips().equals("") && !field2.getShips().equals("")) {

                    if (game.getFirstLogin().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
                        if (game.getMove() == 1) {
                            model.put("move", 1);
                        } else {
                            model.put("move", 0);
                        }
                    } else {
                        if (game.getMove() == 2) {
                            model.put("move", 1);
                        } else {
                            model.put("move", 0);
                        }
                    }
                    model.put("id", id);
                    return "gamePages/fieldsForGame/game";
                } else {
                    if (game.getFirstLogin().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
                        if (field1.getShips().equals("")) {
                            // send fill field
                            TableForm tableForm = new TableForm();
                            model.put("tableForm", tableForm);
                            model.put("id", id);
                            return "gamePages/fieldsForGame/field";
                        } else {
                            // show message
                            return "gamePages/fieldsForGame/opponentError";
                        }
                    } else {
                        if (field2.getShips().equals("")) {
                            // send fill field
                            TableForm tableForm = new TableForm();
                            model.put("tableForm", tableForm);
                            model.put("id", id);
                            return "gamePages/fieldsForGame/field";
                        } else {
                            // show message
                            return "gamePages/fieldsForGame/opponentError";
                        }
                    }
                }
            } catch (SQLException e) {
            }
        }
        return "gamePages/continuedGames";
    }

    @RequestMapping(value = "/game/mygames/continued/{idGame}", method = RequestMethod.POST)
    public String fillFieldForGame(@PathVariable int idGame, TableForm tableForm, ModelMap model) {
        try {
            Game game = gameService.getGameById(idGame);
            if (game.getFirstLogin().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
                int idField = game.getIdFirstField();
                fieldService.setShips(idField, tableForm.toString());
            } else {
                if (game.getSecondLogin().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
                    int idField = game.getIdSecondField();
                    fieldService.setShips(idField, tableForm.toString());
                } else {
                    throw new Exception("Can't find user");
                }
            }
        } catch (Exception e) {
            logger.info("Error: " + getClass().getName() + " " + e.getMessage());
            return "error";
        }
        return "gamePages/fieldsForGame/successfulFillingField";
    }

    @RequestMapping(value = "/game/mygames/completed/", method = RequestMethod.GET)
    public String getCompletedGamesPage() {
        return "gamePages/completedGames";
    }
}