package ru.testhf.srv3.h37945.domain.lists;

import junit.framework.TestCase;
import ru.testhf.srv3.h37945.domain.Request;
import ru.testhf.srv3.h37945.domain.lists.RequestList;

import java.util.ArrayList;
import java.util.List;

public class TestRequestList extends TestCase{
    public void testIsIdIncludeList() {
        List<Request> list = new ArrayList<Request>();
        list.add(new Request(5,"","",0,0));
        list.add(new Request(30,"","",0,0));
        list.add(new Request(14,"","",0,0));
        list.add(new Request(55,"","",0,0));

        RequestList requestList = new RequestList(list);
        assertTrue(requestList.isIdIncludeList(5));
        assertFalse(requestList.isIdIncludeList(20));
    }
}
