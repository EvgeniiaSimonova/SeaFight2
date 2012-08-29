package ru.testhf.srv3.h37945.service.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.testhf.srv3.h37945.dao.RequestDAO;
import ru.testhf.srv3.h37945.domain.Request;
import ru.testhf.srv3.h37945.service.dao.RequestService;

import java.sql.SQLException;
import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestDAO requestDAO;

    public void setRequestDAO(RequestDAO requestDAO) {
        this.requestDAO = requestDAO;
    }

    public int addRequest(Request request) throws SQLException {
        return requestDAO.addRequest(request);
    }

    public List<Request> requestList() {
        return requestDAO.requestList();
    }

    public Request getRequestById(int id) throws SQLException {
        return requestDAO.getRequestById(id);
    }

    public void updateRequest(int id, int state, int idGame) {
        requestDAO.updateRequest(id, state, idGame);
    }

    public List<Request> requestsForUser(String login) {
        return requestDAO.requestsForUser(login);
    }
}
