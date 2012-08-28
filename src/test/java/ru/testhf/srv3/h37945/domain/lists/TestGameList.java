package ru.testhf.srv3.h37945.domain.lists;

import junit.framework.TestCase;
import ru.testhf.srv3.h37945.domain.Game;
import ru.testhf.srv3.h37945.domain.lists.GameList;

import java.util.ArrayList;
import java.util.List;

public class TestGameList extends TestCase{
    public void testIsIdIncludeList() {
        List<Game> list = new ArrayList<Game>();
        list.add(new Game(1,"","",false,"",0,0,0));
        list.add(new Game(7,"","",false,"",0,0,0));
        list.add(new Game(45,"","",false,"",0,0,0));
        list.add(new Game(3,"","",false,"",0,0,0));

        GameList gameList = new GameList(list);
        assertTrue(gameList.isIdIncludeList(7));
        assertFalse(gameList.isIdIncludeList(5));
    }
}
