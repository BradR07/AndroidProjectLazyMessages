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

    @Query("SELECT * FROM mailentity WHERE id LIKE :id LIMIT 1")
    MailEntity getOneById(int id);

    @Query("SELECT * FROM mailentity WHERE objet LIKE :objet LIMIT 1")
    MailEntity findByObjet(String objet);

    @Insert
    void insertAll(List<MailEntity> mail);

    @Delete
    void delete(MailEntity mail);

    @Query("DELETE FROM mailentity")
    void deleteTable() ;

    @Query("DELETE FROM mailentity WHERE id LIKE :id")
    void deleteById(int id);
}