package com.carpediemsolution.dailynotes;



import com.carpediemsolution.dailynotes.model.Task;


import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/* локальный тест */
public class TaskUnitTest {

    private Task task;
    private Date date;


    @Before
    public void setUp() throws Exception {
        task = new Task();
        date = new Date();
    }

    @Test
    public void initTaskObject() throws Exception {
        assertEquals(date, task.getDate());
        assertNotEquals(null, task.getDate());
    }
}
