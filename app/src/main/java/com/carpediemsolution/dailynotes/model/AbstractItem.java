package com.carpediemsolution.dailynotes.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;
import java.util.Objects;

public abstract class AbstractItem {

    private final static String TYPE = "type";
    public final static String FIELD_DATA = "data";
    public final static String FIELD_DATE= "date";

    @DatabaseField(generatedId = true)
    private int Id;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = FIELD_DATA)
    private String data;

    @DatabaseField(canBeNull = true, dataType = DataType.INTEGER, columnName = TYPE)
    private int type;


    @DatabaseField(canBeNull = false,dataType = DataType.DATE, columnName = FIELD_DATE)
    private Date date;

    @DatabaseField(dataType = DataType.BOOLEAN)
    private boolean done;

    @DatabaseField(canBeNull = true, dataType = DataType.STRING)
    private String imageUri;

    public int getId() {
        return Id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getDate() {
        return date;
    }

    public boolean isDone() {
        return done;
    }

    public String getImageUri() {return imageUri;}

    public void setId(int id) {
        Id = id;
    }

    public void setDate(Date date) {
        this.date = date;
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
