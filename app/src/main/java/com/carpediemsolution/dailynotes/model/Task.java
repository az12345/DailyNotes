package com.carpediemsolution.dailynotes.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by Юлия on 29.05.2017.
 */
@DatabaseTable(tableName = "tasks")

public class Task {

    private final static String TASK_NAME_FIELD_NAME = "task";

    @DatabaseField(generatedId = true)
    private int Id;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = TASK_NAME_FIELD_NAME)
    private String task;

    @DatabaseField(canBeNull = false,dataType = DataType.DATE)
    private Date taskDate;

    @DatabaseField(dataType = DataType.BOOLEAN)
    private boolean done;

    @DatabaseField(canBeNull = true, dataType = DataType.STRING)
    private String imageUri;

    public Task(){}

    public int getId() {
        return Id;
    }

    public String getTask() {
        return task;
    }

    public Date getTaskDate() {
        return taskDate;
    }

    public boolean isDone() {
        return done;
    }

    public String getImageUri() {return imageUri;}

    public void setId(int id) {
        Id = id;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = taskDate;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setImageUri(String imageUri) {this.imageUri = imageUri;}
}

