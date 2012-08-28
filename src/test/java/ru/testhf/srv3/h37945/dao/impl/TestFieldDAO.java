package ru.testhf.srv3.h37945.dao.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.testhf.srv3.h37945.dao.FieldDAO;
import ru.testhf.srv3.h37945.domain.Field;

import javax.annotation.Resource;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-context.xml", "classpath:root-context.xml"})
public class TestFieldDAO {
    @Resource
    private FieldDAO fieldDAO;

    private static int length;
    private static int id;

    @Before
    public void testLength() throws SQLException{
        length = fieldDAO.fieldList().size();
        Field field = new Field("", "", false);
        id = fieldDAO.addField(field);
        org.junit.Assert.assertEquals(length+1, fieldDAO.fieldList().size());
    }

    @After
    public void testDeleteField() throws SQLException{
        fieldDAO.deleteField(id);
        org.junit.Assert.assertEquals(length, fieldDAO.fieldList().size());
    }

    @Test
    public void testGetFieldById() throws SQLException{
        fieldDAO.getFieldById(id);
    }

    @Test
    public void testSetShips() throws SQLException{
        String s = "1,2,3,4,5";
        fieldDAO.setShips(id, s);
        org.junit.Assert.assertEquals(s, fieldDAO.getFieldById(id).getShips());
    }

    @Test
    public void testAddShot() throws SQLException{
        int cell = 1;
        String s = "1,";
        fieldDAO.addShot(id, cell);
        org.junit.Assert.assertEquals(s, fieldDAO.getFieldById(id).getShots());
    }
}