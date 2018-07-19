package com.carpediemsolution.dailynotes.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;
import java.util.Objects;

/**
 * Created by Юлия on 29.05.2017.
 */
@DatabaseTable(tableName = "tasks")
public class Task extends AbstractItem {

    private final static String ID_PARENT = "id_parent";
    private final static String LEVEL = "level";

    @DatabaseField(canBeNull = true, dataType = DataType.INTEGER, columnName = ID_PARENT)
    private int idParent;

    @DatabaseField(canBeNull = true, dataType = DataType.INTEGER, columnName = LEVEL)
    private int level;

    public Task() {
        setDate(new Date());
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return getData().equals(((Task) o).getData()) &&
                getDate().equals(((Task) o).getDate()) && idParent == task.idParent &&
                level == task.level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idParent, level);
    }

  /*   String
     public int hashCode() {
        int var1 = this.hash;
        if (var1 == 0 && this.value.length > 0) {
            char[] var2 = this.value;

            for(int var3 = 0; var3 < this.value.length; ++var3) {
                var1 = 31 * var1 + var2[var3];
            }

            this.hash = var1;
        }

        return var1;
    }*/
    /*   String
    public boolean equals(Object var1) {
        if (this == var1) {
            return true;
        } else {
            if (var1 instanceof String) {
                String var2 = (String)var1;
                int var3 = this.value.length;
                if (var3 == var2.value.length) {
                    char[] var4 = this.value;
                    char[] var5 = var2.value;

                    for(int var6 = 0; var3-- != 0; ++var6) {
                        if (var4[var6] != var5[var6]) {
                            return false;
                        }
                    }

                    return true;
                }
            }

            return false;
        }
    }*/

    @Override
    public String toString() {
        return super.toString();
    }
}

