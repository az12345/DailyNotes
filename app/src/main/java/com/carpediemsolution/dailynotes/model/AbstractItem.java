package com.carpediemsolution.dailynotes.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

public abstract class AbstractItem {

    private final static String TYPE = "type";
    private final static String TASK_NAME_FIELD_NAME = "task";

    @DatabaseField(generatedId = true)
    private int Id;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = TASK_NAME_FIELD_NAME)
    private String task;

    @DatabaseField(canBeNull = true, dataType = DataType.INTEGER, columnName = TYPE)
    private int type;


    @DatabaseField(canBeNull = false,dataType = DataType.DATE)
    private Date taskDate;

    @DatabaseField(dataType = DataType.BOOLEAN)
    private boolean done;

    @DatabaseField(canBeNull = true, dataType = DataType.STRING)
    private String imageUri;

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


    public int getType() {
        return type;
    }


    public void setType(int type) {
        this.type = type;
    }




}
