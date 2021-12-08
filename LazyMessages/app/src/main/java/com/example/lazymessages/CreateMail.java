package com.example.lazymessages;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.Manifest;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import com.example.lazymessages.databinding.CreateMailBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


/**
 * Activité de création et envoi de mail
 */
public class CreateMail extends AppCompatActivity {


    private static final int CHANNEL_ID = 1023;
    private CreateMailBinding nBinding;
    private static final int MY_PERMISSION_REQUEST_CODE_SEND_SMS = 1;
    private static final String LOG_TAG = "AndroidExample";
    private BackgroundMail backgroundMail;
    private boolean mailIsSend = false;
    private RemindCollecteWorker remindCollecteWorker;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nBinding = CreateMailBinding.inflate(getLayoutInflater());
        View v = nBinding.getRoot();

        // Configuration de la ToolBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Créer un nouveau mail");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Mails m = new Mails(nBinding.editTextTextObject.toString(), nBinding.editTextMail.toString(), nBinding.editTextTextContent.toString(), nBinding.editTextDate.toString());
        nBinding.buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment frag = new DatePickerFragment();
                frag.show(getSupportFragmentManager(), "datePicker");
            }
        });
        nBinding.button.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick sur le bouton 'créer' du layout create_mail, création du mail puis renvoi sur la vue MessageList
             * @param v une View
             */
            @Override
            public void onClick(View v) {

                // Check if email is valid or not
                if (!isEmailValid(nBinding.editTextMail.getText().toString())){
                    Context context = getApplicationContext();
                    CharSequence text = "Email invalide";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }else{
                    //sendNotif();








                    //Parse date
                    Context context = getApplicationContext();
                    createNotification(nBinding.editTextTextObject.getText().toString(), context);
                    setRecurringAlarm2(context, nBinding.editTextDate.getText().toString());

                    Mails m = new Mails(nBinding.editTextTextObject.getText().toString(), nBinding.editTextMail.getText().toString(), nBinding.editTextTextContent.getText().toString(), nBinding.editTextDate.getText().toString());

                    remindCollecteWorker.doWork();

                    DataStore.getInstance().getMailsList().add(m);

                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL  , new String[]{nBinding.editTextMail.getText().toString()});
                    i.putExtra(Intent.EXTRA_SUBJECT, nBinding.editTextTextObject.getText().toString());
                    i.putExtra(Intent.EXTRA_TEXT   , nBinding.editTextTextContent.getText().toString());
                    try {
                        //mailIsSend=true;
                        startActivity(Intent.createChooser(i, "Mail en cours d'envoi..."));

                        if(mailIsSend==true){
                            Intent MailListAfterCreationIntent = new Intent(CreateMail.this, MailList.class);
                            MailListAfterCreationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(MailListAfterCreationIntent);
                        }
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(CreateMail.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }
                    //Context context = getApplicationContext();
                    CharSequence text = "Nouveau mail programmé !";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

//                    Intent MailListAfterCreationIntent = new Intent(CreateMail.this, MailList.class);
//                    MailListAfterCreationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(MailListAfterCreationIntent);
                }
            }
        });
        setContentView(v);
    }

    //->Presenter
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //->Presenter
    private void createNotification(String text, Context context){

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


    //->Presenter
    private void setRecurringAlarm2(Context context, String date) {

        Calendar currentDate = Calendar.getInstance();
        Calendar dueDate = Calendar.getInstance();

        dueDate.set(Calendar.DAY_OF_MONTH, 8);
        dueDate.set(Calendar.MONTH, 12);
        dueDate.set(Calendar.YEAR, 2021);
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








//    public void sendNotif(){
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setSmallIcon(R.drawable.logo)
//                .setContentTitle("Un message en attente")
//                .setContentText(nBinding.editTextTextObject.getText().toString())
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//    }

//    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
//    private final static String default_notification_channel_id = "default" ;
//    EditText btnDate ;
//    final Calendar myCalendar = Calendar. getInstance () ;
//
//    private void scheduleNotification (Notification notification , long delay) {
//        Intent notificationIntent = new Intent( this, MyNotificationPublisher. class ) ;
//        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION_ID , 1 ) ;
//        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION , notification) ;
//        PendingIntent pendingIntent = PendingIntent. getBroadcast ( this, 0 , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;
//        assert alarmManager != null;
//        alarmManager.set(AlarmManager. ELAPSED_REALTIME_WAKEUP , delay , pendingIntent) ;
//    }
//    private Notification getNotification (String content) {
//        NotificationCompat.Builder builder = new NotificationCompat.Builder( this, default_notification_channel_id ) ;
//        builder.setContentTitle( "Scheduled Notification" ) ;
//        builder.setContentText(content) ;
//        builder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
//        builder.setAutoCancel( true ) ;
//        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
//        return builder.build() ;
//    }
//    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//        @Override
//        public void onDateSet (DatePicker view , int year , int monthOfYear , int dayOfMonth) {
//            myCalendar .set(Calendar. YEAR , year) ;
//            myCalendar .set(Calendar. MONTH , monthOfYear) ;
//            myCalendar .set(Calendar. DAY_OF_MONTH , dayOfMonth) ;
//            updateLabel() ;
//        }
//    } ;
//    public void setDate (View view) {
//        new DatePickerDialog(
//                CreateMail.this, date ,
//                myCalendar .get(Calendar.YEAR ) ,
//                myCalendar .get(Calendar.MONTH ) ,
//                myCalendar .get(Calendar.DAY_OF_MONTH )
//        ).show() ;
//    }
//    private void updateLabel () {
//        String myFormat = "dd/MM/yyyy hh:mm" ; //In which you need put here
//        SimpleDateFormat sdf = new SimpleDateFormat(myFormat , Locale. getDefault ()) ;
//        Date date = myCalendar.getTime() ;
//        btnDate.setText(sdf.format(date)) ;
//        scheduleNotification(getNotification(btnDate.getText().toString()) , date.getTime()) ;
//    }
//
//


//
//
//
//
//    private void createNotification(){
//
//        CharSequence name = "Un mail en attente";
//        String description = nBinding.editTextTextObject.getText().toString();
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            // Create the NotificationChannel, but only on API 26+ because
//            // the NotificationChannel class is new and not in the support library
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//
//            NotificationChannel channel = new NotificationChannel(String.valueOf(CHANNEL_ID), name, importance);
//            channel.setDescription(description);
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        // Create an explicit intent for an Activity in your app
////       Intent intent = new Intent(this, AlertDetails.class);
////       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
////       PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//
//
//
//        Intent intent = new Intent(this, MainActivity.class);
//
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//
//        Notification notification = new NotificationCompat.Builder(this, String.valueOf(CHANNEL_ID))
//                .setSmallIcon(R.drawable.logo)
//                .setContentTitle("Un mail en attente")
//                .setContentText(nBinding.editTextTextObject.getText().toString())
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                // Set the intent that will fire when the user taps the notification
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true)
//                .build();
//
//        // Issue the notification.
//        notificationManager.notify(CHANNEL_ID , notification);
//
//        //create a vibration
//        try {
//            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//            Ringtone toque = RingtoneManager.getRingtone(this, som);
//            toque.play();
//
//        } catch (Exception e) {
//        }
//
//    }
//
//    private void setRecurringAlarm2(Context context) {
//
//
//        Calendar currentDate = Calendar.getInstance();
//        Calendar dueDate = Calendar.getInstance();
//
//        //Set execution around 19:00
//        dueDate.set(Calendar.HOUR_OF_DAY, 19);
//        dueDate.set(Calendar.MINUTE, 0);
//        dueDate.set(Calendar.SECOND, 0);
//
//        if (dueDate.before(currentDate)) {
//            dueDate.add(Calendar.HOUR_OF_DAY, 24);
//
//        }
//
//        long timeDiff = dueDate.getTimeInMillis() - currentDate.getTimeInMillis();
//
//        WorkRequest dailyRemindCollectRequest = new OneTimeWorkRequest.Builder(RemindCollecteWorker.class)
//                .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
//                .build();
//
//
//        WorkManager.getInstance(context).enqueue(dailyRemindCollectRequest);
//    }
}

