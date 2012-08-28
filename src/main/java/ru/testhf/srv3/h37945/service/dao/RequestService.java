package ru.testhf.srv3.h37945.service.dao;

import ru.testhf.srv3.h37945.domain.Request;

import java.sql.SQLException;
import java.util.List;

public interface RequestService {
    public int addRequest(Request request) throws SQLException;

    public List<Request> requestList();

    public Request getRequestById(int id) throws SQLException;

    public void updateRequest(int id, int state, int idGame);

    public List<Request> requestsForUser(String login);
}
