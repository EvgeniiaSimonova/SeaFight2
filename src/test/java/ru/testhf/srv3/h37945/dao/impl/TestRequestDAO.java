package ru.testhf.srv3.h37945.dao.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.testhf.srv3.h37945.dao.RequestDAO;
import ru.testhf.srv3.h37945.domain.Request;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-context.xml", "classpath:root-context.xml"})
public class TestRequestDAO {

    private static int id;
    private int length;

    @Resource
    private RequestDAO requestDAO;

    @Before
    public void addRequest() throws SQLException{
        length = requestDAO.requestList().size();
        Request request = new Request("user1", "user2", 0, -1);
        id = requestDAO.addRequest(request);
    }

    @After
    public void deleteRequest() throws SQLException{
        requestDAO.deleteRequest(id);
    }

    @Test
    public void testRequestById() throws SQLException {
        org.junit.Assert.assertEquals("user1", requestDAO.getRequestById(id).getFirstLogin());
    }

   @Test
    public void testRequestForUser() {
        List<Request> requests = requestDAO.requestsForUser("user2");
        org.junit.Assert.assertEquals(1, requests.size());
    }

    @Test
    public void testUpdateRequest() throws SQLException {
        requestDAO.updateRequest(id, 1, -1);
        org.junit.Assert.assertEquals(1, requestDAO.getRequestById(id).getState());
    }
}
