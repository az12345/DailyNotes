package com.carpediemsolution.dailynotes.dao;

import com.carpediemsolution.dailynotes.model.Task;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Юлия on 30.05.2017.
 */

public class TaskDAO extends BaseDaoImpl<Task, Integer> {

    public TaskDAO(ConnectionSource connectionSource,
                      Class<Task> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public Task getTaskById(int id) throws SQLException {
        return this.queryForId(id);
    }

    public List<Task>getAllTasks()throws SQLException{
       return this.queryForAll();
    }

    public List<Task> getAllTasksByDate() throws SQLException{
        //метод без сортировки по дате
        return this.queryBuilder().orderBy("taskDate", false).query();
    }

    public List<Task> getAllTasksByChecked() throws SQLException{
        //метод без сортировки по отмеченной заметке
        return this.queryBuilder().orderBy("done", true).query();
    }

    public List<Task> getAllTasksBySearchString(String string)throws SQLException{
        PreparedQuery preparedQuery = this.queryBuilder().where().like("task", "%" +  string +  "%").prepare();

        return this.query(preparedQuery);
    }

    public List<Task> getAllTasksBySearchStringOrderedByDate(String string)throws SQLException{
        PreparedQuery preparedQuery = this.queryBuilder().orderBy("taskDate", false).where().like("task", "%" +  string +  "%").prepare();

        return this.query(preparedQuery);
    }

    public List<Task> getAllTasksBySearchStringOrderedByChecked(String string)throws SQLException{
        PreparedQuery preparedQuery = this.queryBuilder().orderBy("done", true).where().like("task", "%" +  string +  "%").prepare();

        return this.query(preparedQuery);
    }
}

