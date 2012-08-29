package ru.testhf.srv3.h37945.service.dao.impl;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.testhf.srv3.h37945.dao.FieldDAO;
import ru.testhf.srv3.h37945.domain.Field;

import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-context.xml", "classpath:root-context.xml"})
public class TestFieldService {

    private Mockery context = new Mockery();

    @Test
    public void testAddField() throws SQLException{
        final FieldDAO fieldDAO = context.mock(FieldDAO.class);
        FieldServiceImpl fieldService = new FieldServiceImpl();
        fieldService.setFieldDAO(fieldDAO);

        final Field field = new Field("0,3,4,6", "", false);

        context.checking(new Expectations() {{
            oneOf(fieldDAO).addField(field);
        }});

        fieldService.addField(field);

        context.assertIsSatisfied();
    }

    @Test
    public void testFieldList() {
        final FieldDAO fieldDAO = context.mock(FieldDAO.class);
        FieldServiceImpl fieldService = new FieldServiceImpl();
        fieldService.setFieldDAO(fieldDAO);

        context.checking(new Expectations() {{
            oneOf(fieldDAO).fieldList();
        }});

        fieldService.fieldList();

        context.assertIsSatisfied();
    }

    @Test
    public void testGetFieldById() throws SQLException {
        final FieldDAO fieldDAO = context.mock(FieldDAO.class);
        FieldServiceImpl fieldService = new FieldServiceImpl();
        fieldService.setFieldDAO(fieldDAO);

        final int id = 5;

        context.checking(new Expectations() {{
            oneOf(fieldDAO).getFieldById(id);
        }});

        fieldService.getFieldById(id);

        context.assertIsSatisfied();
    }

    @Test
    public void testSetShips() {
        final FieldDAO fieldDAO = context.mock(FieldDAO.class);
        FieldServiceImpl fieldService = new FieldServiceImpl();
        fieldService.setFieldDAO(fieldDAO);

        final int id = 6;
        final String ships = "14,15,70";

        context.checking(new Expectations() {{
            oneOf(fieldDAO).setShips(id, ships);
        }});

        fieldService.setShips(id, ships);

        context.assertIsSatisfied();
    }

    @Test
    public void testAddShot() {
        final FieldDAO fieldDAO = context.mock(FieldDAO.class);
        FieldServiceImpl fieldService = new FieldServiceImpl();
        fieldService.setFieldDAO(fieldDAO);

        final int id = 7;
        final int cell = 20;

        context.checking(new Expectations() {{
            oneOf(fieldDAO).addShot(id, cell);
        }});

        fieldService.addShot(id, cell);

        context.assertIsSatisfied();
    }

    @Test
    public void testIsKilled() {
        final FieldDAO fieldDAO = context.mock(FieldDAO.class);
        FieldServiceImpl fieldService = new FieldServiceImpl();
        fieldService.setFieldDAO(fieldDAO);

        final int id = 10;

        context.checking(new Expectations() {{
            oneOf(fieldDAO).isKilled(id);
        }});

        fieldService.isKilled(id);

        context.assertIsSatisfied();
    }
}
