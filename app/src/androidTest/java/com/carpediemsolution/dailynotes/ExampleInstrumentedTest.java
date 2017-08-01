package com.carpediemsolution.dailynotes;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.carpediemsolution.dailynotes.dao.HelperFactory;
import com.carpediemsolution.dailynotes.model.Task;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.carpediemsolution.testfragment", appContext.getPackageName());
    }

    @Test
    public void nullStringTest() throws Exception {

        List<Task> taskList = HelperFactory.getHelper().getTaskDAO().getAllTasksByDate();
        for(Task task : taskList) {
            String str = task.getTask();
            assertTrue(!str.isEmpty());
        }
    }

}
