package com.example.lazymessages.createMail;

import android.content.Context;
import android.os.AsyncTask;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.example.lazymessages.DataStore;
import com.example.lazymessages.MailDao;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CreateMailPresenter {
    private String day;
    private String month;
    private String year;
    private String hour;
    private String minute;
    private Context context ;
    private CreateMailPresenterCallback mCallBack;


    public CreateMailPresenter(Context context, CreateMailPresenterCallback callback) {
        this.mCallBack = callback;
        this.context = context;
    }

    public void InsertInDB(MailEntity mailEntity){
        //RECUPERATION DU MAIL CREE ET INSERTION BDD

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                //INSERT HERE YOUR CODE TO WRITE IN DB
                DataStore db = DataStore.getDatabase(context);
                //RECUP TOUTE LA LISTE MAIL
                MailDao mailDao = db.mailDao();
                List<MailEntity> mailEntityList = mailDao.getAll();
                //ADD CURRENT MAIL
                mailEntityList.add(mailEntity);
                //DELETE ALL MAIL TABLE
                db.mailDao().deleteTable();
                db.mailDao().insertAll(mailEntityList);
                mCallBack.onMailInserted();
            }
        });

    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    protected void setRecurringAlarm2(Context context, String day, String month, String year, String hour, String minute) {
        Calendar currentDate = Calendar.getInstance();
        Calendar dueDate = Calendar.getInstance();

        dueDate.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
        dueDate.set(Calendar.MONTH, Integer.parseInt(month));
        dueDate.set(Calendar.YEAR, Integer.parseInt(year));
        dueDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
        dueDate.set(Calendar.MINUTE, Integer.parseInt(minute));
        dueDate.set(Calendar.SECOND, 0);

        if (dueDate.before(currentDate)) {
            dueDate.add(Calendar.HOUR_OF_DAY, 24);
        }
        long timeDiff = dueDate.getTimeInMillis() - currentDate.getTimeInMillis();
        WorkRequest dailyRemindCollectRequest = new OneTimeWorkRequest.Builder(RemindCollecteWorker.class)
                .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
                .build();
        WorkManager.getInstance(context).enqueue(dailyRemindCollectRequest);
    }

     public void setDateTime(String day, String month, String year, String hour, String minute){
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
    }

    public String getDay(){
        return day;
    }

    public String getMonth(){
        return month;
    }

    public String getYear(){
        return year;
    }

    public String getHour(){
        return hour;
    }

    public String getMinute(){
        return minute;
    }

}

