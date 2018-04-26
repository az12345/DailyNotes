package com.carpediemsolution.dailynotes.model;

import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="notes")
public class Note extends AbstractItem {

    /* @DatabaseField(columnName="ala")
      protected String ala;

      public String getAla() {
              return ala;
      }

      public void setAla(String ala) {
              this.ala = ala;
      }

      public Atividade(String name, Date date, String type, String ala) {
              this.setName(name);
              this.setDate(date);
              this.setTipo(type);
              this.setAla(ala);
      }

      Atividade(){
      }

      @Override
      public void setName(String name) {
              this.name = name;
      }

      @Override
      public void setDate(Date date) {
              this.date = date;
      }

      @Override
      public void setType(String type) {
              this.type = type;
      } */

}
