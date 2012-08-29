package ru.testhf.srv3.h37945.service.dao.impl;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.testhf.srv3.h37945.dao.RequestDAO;
import ru.testhf.srv3.h37945.domain.Request;

import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-context.xml", "classpath:root-context.xml"})
public class TestRequestService {

    private Mockery context = new Mockery();

    @Test
    public void testAddRequest() throws SQLException{
        final RequestDAO requestDAO = context.mock(RequestDAO.class);
        RequestServiceImpl requestService = new RequestServiceImpl();
        requestService.setRequestDAO(requestDAO);

        final Request request = new Request("user1", "user2", 0, -1);

        context.checking(new Expectations() {{
            oneOf(requestDAO).addRequest(request);
        }});

        requestService.addRequest(request);

        context.assertIsSatisfied();
    }

    @Test
    public void testRequestList() {
        final RequestDAO requestDAO = context.mock(RequestDAO.class);
        RequestServiceImpl requestService = new RequestServiceImpl();
        requestService.setRequestDAO(requestDAO);

        context.checking(new Expectations() {{
            oneOf(requestDAO).requestList();
        }});

        requestService.requestList();

        context.assertIsSatisfied();
    }

    @Test
    public void testGetRequestById() throws SQLException{
        final RequestDAO requestDAO = context.mock(RequestDAO.class);
        RequestServiceImpl requestService = new RequestServiceImpl();
        requestService.setRequestDAO(requestDAO);

        final int id = 10;

        context.checking(new Expectations() {{
            oneOf(requestDAO).getRequestById(id);
        }});

        requestService.getRequestById(id);

        context.assertIsSatisfied();
    }

    @Test
    public void testUpdateRequest() {
        final RequestDAO requestDAO = context.mock(RequestDAO.class);
        RequestServiceImpl requestService = new RequestServiceImpl();
        requestService.setRequestDAO(requestDAO);

        final int id = 11;
        final int state = 1;
        final int idGame = 1;

        context.checking(new Expectations() {{
            oneOf(requestDAO).updateRequest(id, state, idGame);
        }});

        requestService.updateRequest(id, state, idGame);

        context.assertIsSatisfied();
    }

    @Test
    public void testRequestsForUser() {
        final RequestDAO requestDAO = context.mock(RequestDAO.class);
        RequestServiceImpl requestService = new RequestServiceImpl();
        requestService.setRequestDAO(requestDAO);

        final String login = "user";

        context.checking(new Expectations() {{
            oneOf(requestDAO).requestsForUser(login);
        }});

        requestService.requestsForUser(login);

        context.assertIsSatisfied();
    }
}
