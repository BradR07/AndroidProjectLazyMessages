package com.example.lazymessages;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.lazymessages.createMail.MailEntity;

import java.util.List;

@Dao
public interface MailDao {
    @Query("SELECT * FROM mailentity")
    List<MailEntity> getAll();

//    @Query("SELECT * FROM mail WHERE id IN (:mailIds)")
//    List<MailEntity> loadAllByIds(int[] mailIds);

//    @Query("SELECT * FROM mail WHERE objet LIKE :objet LIMIT 1")
//    MailEntity findByObjet(String objet);

    @Insert
    void insertAll(MailEntity... mail);

    @Delete
    void delete(MailEntity mail);
}