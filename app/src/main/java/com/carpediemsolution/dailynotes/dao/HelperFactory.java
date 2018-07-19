package com.carpediemsolution.dailynotes.dao;

import android.content.Context;
import com.carpediemsolution.dailynotes.model.Task;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Юлия on 30.05.2017.
 */

public class HelperFactory {

    private static DatabaseHelper databaseHelper;

    public static DatabaseHelper getHelper() {
        return databaseHelper;
    }

    public static void setHelper(Context context) {
        databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }

    public static void releaseHelper() {
        OpenHelperManager.releaseHelper();
        databaseHelper = null;
    }

    public static List<Task> getAllTasks() throws SQLException {
        return databaseHelper.getTaskDAO().getAllTasks();
    }

    public int saveTask(Task task)throws SQLException{
        return HelperFactory.getHelper().getTaskDAO().create(task);
    }

    public Task getTaskById(int id) throws SQLException {

        //getTasks();
        return HelperFactory.getHelper().getTaskDAO().queryForId(id);
    }


}
