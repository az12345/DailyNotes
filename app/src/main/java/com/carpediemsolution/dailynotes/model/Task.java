package com.carpediemsolution.dailynotes.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by Юлия on 29.05.2017.
 */
@DatabaseTable(tableName="tasks")
public class Task extends AbstractItem{

    private final static String ID_PARENT = "id_parent";
    private final static String LEVEL = "level";

    @DatabaseField(canBeNull = true, dataType = DataType.INTEGER, columnName = ID_PARENT)
    private int idParent;

    @DatabaseField(canBeNull = true, dataType = DataType.INTEGER, columnName = LEVEL)
    private int level;

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public String getTask() {
        return super.getTask();
    }

    @Override
    public Date getTaskDate() {
        return super.getTaskDate();
    }

    @Override
    public boolean isDone() {
        return super.isDone();
    }

    @Override
    public String getImageUri() {
        return super.getImageUri();
    }

    @Override
    public void setId(int id) {
        super.setId(id);
    }

    @Override
    public void setTask(String task) {
        super.setTask(task);
    }

    @Override
    public void setTaskDate(Date taskDate) {
        super.setTaskDate(taskDate);
    }

    @Override
    public void setDone(boolean done) {
        super.setDone(done);
    }

    @Override
    public void setImageUri(String imageUri) {
        super.setImageUri(imageUri);
    }

    @Override
    public int getType() {
        return super.getType();
    }

    @Override
    public void setType(int type) {
        super.setType(type);
    }

    /**/
    public int getIdParent() {
        return idParent;
    }

    public int getLevel() {
        return level;
    }

    public void setIdParent(int idParent) {
        this.idParent = idParent;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}

