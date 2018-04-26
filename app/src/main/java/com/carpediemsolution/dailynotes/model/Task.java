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

