package com.example.lazymessages.createMail;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MailEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "objet")
    public String objet;

    @ColumnInfo(name = "destinataire")
    public String destinataire;

    @ColumnInfo(name = "contenu")
    public String contenu;

    @ColumnInfo(name = "date")
    public String date;

    public String toString(){
        return this.id+this.objet;
    }
}
