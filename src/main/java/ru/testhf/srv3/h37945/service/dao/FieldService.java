package ru.testhf.srv3.h37945.service.dao;

import ru.testhf.srv3.h37945.domain.Field;

import java.sql.SQLException;
import java.util.List;

public interface FieldService {
    public int addField(Field field) throws SQLException;

    public Field getFieldById(int id) throws SQLException;

    public List<Field> fieldList();

    public void setShips(int id, String ships);

    public void addShot(int id, int cell);

    public boolean isKilled(int id);
}
