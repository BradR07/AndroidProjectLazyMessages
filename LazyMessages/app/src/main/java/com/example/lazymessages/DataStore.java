package com.example.lazymessages;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.lazymessages.createMail.MailEntity;
import com.example.lazymessages.createMail.Mails;
import java.util.ArrayList;
import java.util.List;

@Database(entities = {MailEntity.class}, version = 1)

/**
 * Singleton base de données des mails enregistrés
 */
public abstract class DataStore extends RoomDatabase {

    public abstract MailDao mailDao();

    public static DataStore INSTANCE ;

    private static  DataStore instance;

    private List<Mails> mailsList = new ArrayList<>();

//    public static DataStore getInstance() {
//        if (instance == null){
//            instance = Room.databaseBuilder(Context context);
//
//        }
//        return instance;
//    }
//

    public static DataStore getDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DataStore.class, "app_database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    /**
     * @return liste de mail
     */
    public List<Mails> getMailsList() {
        return mailsList;
    }

    public void setMailList(List<Mails> mailsList) {
        this.mailsList = mailsList;
    }
}