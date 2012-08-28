package ru.testhf.srv3.h37945.forms;

import junit.framework.TestCase;
import ru.testhf.srv3.h37945.forms.TableForm;

public class TestTableForm extends TestCase {
    public void testToString() {
        TableForm tableForm = new TableForm(new int[]{1,2,3,4,5});
        String expected = "1,2,3,4,5";
        String actual = tableForm.toString();
        assertEquals(expected, actual);
    } // if array is empty, a test should take a error
}
