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
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.example.lazymessages.DataStore;
import com.example.lazymessages.MailDao;
import com.example.lazymessages.R;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RemindCollecteWorker extends Worker {
    private final Context context;
    private static final int CHANNEL_ID = 1023;
    private List<MailEntity> mailEntityList ;
    private MailEntity mailEntity;

    public RemindCollecteWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        getMailList();
        return Result.success();
    }

    private void getMailList(){
        DataStore db = DataStore.getDatabase(context);
        //Récupère toute la liste MAIL
        MailDao mailDao = db.mailDao();
        List<MailEntity> mailEntityList = mailDao.getAll();
        String currentDate = Calendar.getInstance().toString();

        for (MailEntity i: mailEntityList) {
            if (currentDate.equals(i.date)) {
                System.out.println("Match Found " + i);
                break;
            }
            createNotification(i);
        }
    }

   private void createNotification(MailEntity m){
       CharSequence name = "Le mail '"+m.objet+"' est en attente d'envoi";
       String description = "Cliquez ici pour l'envoyer";
       NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

       if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
           int importance = NotificationManager.IMPORTANCE_DEFAULT;
           NotificationChannel channel = new NotificationChannel(String.valueOf(CHANNEL_ID), name, importance);
           channel.setDescription(description);
           notificationManager.createNotificationChannel(channel);
       }
       //Renvoi vers l'application Gmail avec les données du mail pré-remplies
       Intent i = new Intent(Intent.ACTION_SEND);
       i.setType("message/rfc822");
       i.putExtra(Intent.EXTRA_SUBJECT, m.objet);
       i.putExtra(Intent.EXTRA_EMAIL, new String[]{m.destinataire});
       i.putExtra(Intent.EXTRA_TEXT, m.contenu);

       try {
           PendingIntent pendingIntent = PendingIntent.getActivity(context, m.id, i, 0);
           Notification notification = new NotificationCompat.Builder(context, String.valueOf(CHANNEL_ID))
                   .setSmallIcon(R.drawable.logo)
                   .setContentTitle("Le mail '"+m.objet+"' est en attente d'envoi")
                   .setContentText("Cliquez ici pour l'envoyer")
                   .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                   // Set the intent that will fire when the user taps the notification
                   .setContentIntent(pendingIntent)
                   .setAutoCancel(true)
                   .build();
           // Issue the notification.
           notificationManager.notify(CHANNEL_ID , notification);
       } catch (android.content.ActivityNotFoundException ex) {
           Context context = getApplicationContext();
           Toast.makeText(context, "Aucun client de messagerie n'est installé.", Toast.LENGTH_SHORT).show();
       }
       //create a vibration
       Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
       Ringtone toque = RingtoneManager.getRingtone(context, som);
       toque.play();
    }

    //Récurrence (Elle n'est actuellement pas implementée par manque de temps)
    private void enqueueNextExecution(int hour, int minute, int second){
        Calendar currentDate = Calendar.getInstance();
        Calendar dueDate = Calendar.getInstance();

        dueDate.set(Calendar.HOUR_OF_DAY , hour );
        dueDate.set(Calendar.MINUTE , minute);
        dueDate.set(Calendar.SECOND , second);

        if (dueDate.before(currentDate)){
            dueDate.add(Calendar.HOUR_OF_DAY , 24);
        }
        long timeDiff = dueDate.getTimeInMillis() - currentDate.getTimeInMillis();
        WorkRequest dailyRemindCollectRequest = new OneTimeWorkRequest.Builder(RemindCollecteWorker.class)
                .setInitialDelay(timeDiff , TimeUnit.MILLISECONDS)
                .build();
        WorkManager.getInstance(context).enqueue(dailyRemindCollectRequest);
    }
}