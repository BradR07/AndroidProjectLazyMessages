package com.example.lazymessages;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton base de données des mails enregistrés
 */
public class DataStore {

    private static  DataStore instance;
    private List<Mails> mailsList = new ArrayList<>();

    public static DataStore getInstance() {
        if (instance == null){
            instance = new DataStore();
        }
        return instance;
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
