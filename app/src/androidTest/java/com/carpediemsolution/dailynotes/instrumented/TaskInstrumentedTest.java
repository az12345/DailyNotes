package com.carpediemsolution.dailynotes.instrumented;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.carpediemsolution.dailynotes.dao.HelperFactory;
import com.carpediemsolution.dailynotes.model.Task;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;


/**
 * Instrumentation test, which will execute on an Android device.
 *
 * создание и запись экземпляра класса Task в базу данных
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TaskInstrumentedTest {

    private Context appContext;

    private Task task;
    private Task savedTask;
    private int idSavedTask;

    @Before
    public void setUp() throws Exception {
        appContext = InstrumentationRegistry.getTargetContext();
        task = new Task();
    }

    @Test
    public void saveAndReadValues() throws Exception {
        HelperFactory.setHelper(appContext);

        task.setData("");
        task.setData("12345");

        idSavedTask = new HelperFactory().saveTask(task);
        savedTask = new HelperFactory().getTaskById(idSavedTask);

        assertNotNull(savedTask);
        assertTrue(equalsTo());
    }

    private boolean equalsTo(){
        return idSavedTask == savedTask.getId();
    }

}
