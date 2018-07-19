package com.carpediemsolution.dailynotes.dao;

import com.carpediemsolution.dailynotes.model.AbstractItem;
import com.carpediemsolution.dailynotes.model.Task;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import static com.carpediemsolution.dailynotes.model.AbstractItem.FIELD_DATA;
import static com.carpediemsolution.dailynotes.model.AbstractItem.FIELD_DATE;

/**
 * Created by Юлия on 30.05.2017.
 */

public class TaskDao extends BaseDaoImpl<Task, Integer> {

    public TaskDao(ConnectionSource connectionSource,
                   Class<Task> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public AbstractItem getTaskById(int id) throws SQLException {
        return this.queryForId(id);
    }


    public List<Task> getAllTasks() throws SQLException {
        return this.queryForAll();
    }

    public List<Task> getAllTasksByDate() throws SQLException {
        //метод без сортировки по дате
        return this.queryBuilder().orderBy(FIELD_DATE, false).query();
    }

    public List<Task> getAllTasksByChecked() throws SQLException {
        //метод без сортировки по отмеченной заметке
        return this.queryBuilder().orderBy("done", true).query();
    }

    public List<Task> getAllTasksBySearchString(String string) throws SQLException {
        PreparedQuery preparedQuery = this.queryBuilder().where()
                .like(FIELD_DATA, "%" + string + "%").prepare();

        return this.query(preparedQuery);
    }

    public List<Task> getAllTasksBySearchStringOrderedByDate(String string)
            throws SQLException {
        PreparedQuery preparedQuery = this.queryBuilder().orderBy(FIELD_DATE, false)
                .where().like(FIELD_DATA, "%" + string + "%").prepare();

        return this.query(preparedQuery);
    }

    public List<Task> getAllTasksBySearchStringOrderedByChecked(String string) throws SQLException {
        PreparedQuery preparedQuery = this.queryBuilder()
                .orderBy("done", true).where()
                .like(FIELD_DATA, "%" + string + "%").prepare();

        return this.query(preparedQuery);
    }
}

