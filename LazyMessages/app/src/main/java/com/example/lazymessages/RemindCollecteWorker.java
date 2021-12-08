package com.example.lazymessages;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by charlesmatrand on Wed 11 August 2021.
 */


public class RemindCollecteWorker extends Worker {
    private Context context;
    private static final int CHANNEL_ID = 1023;


    public RemindCollecteWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {


        //createNotification(text);


        //setRecurringAlarm2();

        return Result.success();
    }


   private void createNotification(String text){

       CharSequence name = "Un nouveau mail en attente";
       String description = text;
       NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

       if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
           // the NotificationChannel class is new and not in the support library
           int importance = NotificationManager.IMPORTANCE_DEFAULT;

           NotificationChannel channel = new NotificationChannel(String.valueOf(CHANNEL_ID), name, importance);
           channel.setDescription(description);
           notificationManager.createNotificationChannel(channel);
       }

       // Create an explicit intent for an Activity in your app
//       Intent intent = new Intent(this, AlertDetails.class);
//       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//       PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);



       Intent intent = new Intent(context, MainActivity.class);

       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

       PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

       Notification notification = new NotificationCompat.Builder(context, String.valueOf(CHANNEL_ID))
               .setSmallIcon(R.drawable.logo)
               .setContentTitle("Un nouveau mail en attente")
               .setContentText(text)
               .setPriority(NotificationCompat.PRIORITY_DEFAULT)
               // Set the intent that will fire when the user taps the notification
               .setContentIntent(pendingIntent)
               .setAutoCancel(true)
               .build();

       // Issue the notification.
       notificationManager.notify(CHANNEL_ID , notification);

       //create a vibration
       try {
           Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
           Ringtone toque = RingtoneManager.getRingtone(context, som);
           toque.play();

       } catch (Exception e) {
       }

    }

//    private void enqueueNextExecution(int hour, int minute, int second){
//
//
//        Calendar currentDate = Calendar.getInstance();
//        Calendar dueDate = Calendar.getInstance();
//
//        //Set execution around 19:00
//        dueDate.set(Calendar.HOUR_OF_DAY , hour );
//        dueDate.set(Calendar.MINUTE , minute);
//        dueDate.set(Calendar.SECOND , second);
//
//        if (dueDate.before(currentDate)){
//            dueDate.add(Calendar.HOUR_OF_DAY , 24);
//        }
//
//        long timeDiff = dueDate.getTimeInMillis() - currentDate.getTimeInMillis();
//
//        WorkRequest dailyRemindCollectRequest = new OneTimeWorkRequest.Builder(RemindCollecteWorker.class)
//                .setInitialDelay(timeDiff , TimeUnit.MILLISECONDS)
//
//                .build();
//
//
//        WorkManager.getInstance(context).enqueue(dailyRemindCollectRequest);
//    }


    private void setRecurringAlarm2(Context context) {


        Calendar currentDate = Calendar.getInstance();
        Calendar dueDate = Calendar.getInstance();

        //Set execution around 19:00
        dueDate.set(Calendar.HOUR_OF_DAY, 19);
        dueDate.set(Calendar.MINUTE, 0);
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



}
