package com.example.lazymessages.createMail;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
//import androidx.work.OneTimeWorkRequest;
//import androidx.work.WorkManager;
//import androidx.work.WorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.lazymessages.DataStore;
import com.example.lazymessages.MailDao;
import com.example.lazymessages.R;

import java.util.List;
//import java.util.Calendar;
//import java.util.concurrent.TimeUnit;

public class RemindCollecteWorker extends Worker {
    private final Context context;
    private static final int CHANNEL_ID = 1023;
    private List<MailEntity> mailEntityList ;

    public RemindCollecteWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        createNotification();
        return Result.success();
    }

   private void createNotification(){
       CharSequence name = "Un nouveau mail en attente";
       String description = "Cliquez pour envoyer votre mail";
       NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

       if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
           int importance = NotificationManager.IMPORTANCE_DEFAULT;
           NotificationChannel channel = new NotificationChannel(String.valueOf(CHANNEL_ID), name, importance);
           channel.setDescription(description);
           notificationManager.createNotificationChannel(channel);
       }

       //Renvoi vers l'application Gmail
       Intent i = new Intent(Intent.ACTION_SEND);
       i.setType("message/rfc822");
       i.putExtra(Intent.EXTRA_EMAIL, "destinataire@gmail.com");
       i.putExtra(Intent.EXTRA_SUBJECT, "subject");
       i.putExtra(Intent.EXTRA_TEXT, "nBinding.editTextTextContent.getText().toString()");

       try {
//           //INIT DATASTORE
//           DataStore db = DataStore.getDatabase(getApplicationContext());
//           //RECUP TOUTE LA LISTE MAIL
//           MailDao mailDao = db.mailDao();
//           mailEntityList = mailDao.get;

           PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, 0);
           Notification notification = new NotificationCompat.Builder(context, String.valueOf(CHANNEL_ID))
                   .setSmallIcon(R.drawable.logo)
                   .setContentTitle("Un nouveau mail en attente")
                   .setContentText("Cliquez pour envoyer votre mail")
                   .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                   // Set the intent that will fire when the user taps the notification
                   .setContentIntent(pendingIntent)
                   .setAutoCancel(true)
                   .build();
           // Issue the notification.
           notificationManager.notify(CHANNEL_ID , notification);
       } catch (android.content.ActivityNotFoundException ex) {
           Context context = getApplicationContext();
           Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
       }

       //create a vibration
       //try {
           Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
           Ringtone toque = RingtoneManager.getRingtone(context, som);
           toque.play();
       //} catch (Exception e) {

       //}
    }

    //Récurrence
//    private void enqueueNextExecution(int hour, int minute, int second){
//        Calendar currentDate = Calendar.getInstance();
//        Calendar dueDate = Calendar.getInstance();
//
//        dueDate.set(Calendar.HOUR_OF_DAY , hour );
//        dueDate.set(Calendar.MINUTE , minute);
//        dueDate.set(Calendar.SECOND , second);
//
//        if (dueDate.before(currentDate)){
//            dueDate.add(Calendar.HOUR_OF_DAY , 24);
//        }
//        long timeDiff = dueDate.getTimeInMillis() - currentDate.getTimeInMillis();
//        WorkRequest dailyRemindCollectRequest = new OneTimeWorkRequest.Builder(RemindCollecteWorker.class)
//                .setInitialDelay(timeDiff , TimeUnit.MILLISECONDS)
//                .build();
//        WorkManager.getInstance(context).enqueue(dailyRemindCollectRequest);
//    }
}
