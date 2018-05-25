package com.carpediemsolution.dailynotes;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.carpediemsolution.dailynotes.dao.HelperFactory;
import com.carpediemsolution.dailynotes.model.Task;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.List;

import static junit.framework.Assert.assertTrue;


/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TaskInstrumentedTest {

    private Context appContext;

    private Task task;
    //private Date date;

    private Task savedTask;
    private int idSavedTask;

    @Before
    public void setUp() throws Exception {
        appContext = InstrumentationRegistry.getTargetContext();
        task = new Task();
      //  date = new Date();
    }

   /* @Test
    public void useAppContext() throws Exception {
        //  assertEquals("com.carpediemsolution.testfragment",
             ///   appContext.getPackageName());
    }*/

  /*  @Test
    public void nullStringTest() throws Exception {

        List<Task> taskList = HelperFactory.getHelper()
                .getTaskDAO().getAllTasksByDate();
        for(Task task : taskList) {
            String str = task.getData();
            assertTrue(!str.isEmpty());
        }
    }*/

    @Test
    public void saveAndReadValues() throws Exception {
        HelperFactory.setHelper(appContext);

        task.setData("");
        task.setData("12345");

        idSavedTask = new HelperFactory().saveTask(task);
        savedTask = new HelperFactory().getTaskById(idSavedTask);


        assertTrue(equalsTo());
    }

    private boolean equalsTo(){
        return idSavedTask == savedTask.getId();
    }

}
