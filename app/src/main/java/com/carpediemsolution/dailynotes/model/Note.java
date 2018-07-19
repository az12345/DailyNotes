package com.carpediemsolution.dailynotes.model;

import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName="notes")
public class Note extends AbstractItem {

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public String getData() {
        return super.getData();
    }

    @Override
    public void setData(String data) {
        super.setData(data);
    }

    @Override
    public Date getDate() {
        return super.getDate();
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
    public void setDate(Date date) {
        super.setDate(date);
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
}
