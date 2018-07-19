package com.carpediemsolution.dailynotes.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.carpediemsolution.dailynotes.model.Note;
import com.carpediemsolution.dailynotes.model.Task;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;


/**
 * Created by Юлия on 30.05.2017.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    //имя файла базы данных который будет храниться в /data/data/APPNAME/DATABASE_NAME.db
    private static final String DATABASE_NAME ="myappname.db";

    //с каждым увеличением версии, при нахождении в устройстве БД с предыдущей версией будет выполнен метод onUpgrade();
    private static final int DATABASE_VERSION = 1;

    //ссылки на DAO соответсвующие сущностям, хранимым в БД
    private TaskDao taskDao = null;

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,
                null, DATABASE_VERSION);
    }

    //Выполняется, когда файл с БД не найден на устройстве
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource){
        try
        {
            TableUtils.createTable(connectionSource, Note.class);
            TableUtils.createTable(connectionSource, Task.class);
        }
        catch (SQLException e){
            Log.e(TAG, "error creating DB ".concat(DATABASE_NAME));
            throw new RuntimeException(e);
        }
    }

    //Выполняется, когда БД имеет версию отличную от текущей
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer,
                          int newVer){
        try{
            
            TableUtils.dropTable(connectionSource, Note.class, true);
            TableUtils.dropTable(connectionSource, Task.class, true);
            onCreate(db, connectionSource);
        }
        catch (SQLException e){
            Log.e(TAG,"error upgrading db ".concat(DATABASE_NAME+"from ver "+oldVer));
            throw new RuntimeException(e);
        }
    }

    //синглтон для TaskDAO
    public TaskDao getTaskDAO() throws SQLException {
        if(taskDao == null){
            taskDao = new TaskDao(getConnectionSource(), Task.class);
        }
        return taskDao;
    }



    //выполняется при закрытии приложения
    @Override
    public void close(){
        super.close();
        taskDao = null;
    }
}

