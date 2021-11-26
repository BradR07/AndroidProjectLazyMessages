package com.example.lazymessages;

import java.util.ArrayList;
import java.util.List;

public class DataStore {

    private static  DataStore instance;
    private List<Messages> messagesList = new ArrayList<>();

    public static DataStore getInstance() {
        if (instance == null){
            instance = new DataStore();
        }
        return instance;
    }

    public List<Messages> getMessagesList() {
        return messagesList;
    }

    public void setMessagesList(List<Messages> messagesList) {
        this.messagesList = messagesList;
    }
}
