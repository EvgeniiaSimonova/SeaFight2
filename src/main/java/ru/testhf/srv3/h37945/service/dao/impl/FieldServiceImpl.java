package ru.testhf.srv3.h37945.service.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.testhf.srv3.h37945.dao.FieldDAO;
import ru.testhf.srv3.h37945.domain.Field;
import ru.testhf.srv3.h37945.service.dao.FieldService;

import java.sql.SQLException;
import java.util.List;

@Service
public class FieldServiceImpl implements FieldService {

    @Autowired
    private FieldDAO fieldDAO;

    public int addField(Field field) throws SQLException {
        return fieldDAO.addField(field);
    }

    public Field getFieldById(int id) throws SQLException {
        return fieldDAO.getFieldById(id);
    }

    public List<Field> fieldList() {
        return fieldDAO.fieldList();
    }

    public void setShips(int id, String ships) {
        fieldDAO.setShips(id, ships);
    }

    public void addShot(int id, int cell) {
        fieldDAO.addShot(id, cell);
    }

    @Override
    public boolean isKilled(int id) {
        return fieldDAO.isKilled(id);
    }
}
