package com.example.lazymessages;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lazymessages.databinding.ActivityMainBinding;
import com.example.lazymessages.databinding.CreateMessageBinding;


public class CreateMessage extends AppCompatActivity {

    private CreateMessageBinding nBinding;
    private Messages messages;
    private MessageList messageList;
    private static final int MY_PERMISSION_REQUEST_CODE_SEND_SMS = 1;
    private static final String LOG_TAG = "AndroidExample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nBinding = CreateMessageBinding.inflate(getLayoutInflater());
        View v = nBinding.getRoot();


        // Configuration de la ToolBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Créer un nouveau message");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);



        Messages m = new Messages(nBinding.editTextTextTitle.toString(), nBinding.editTextPhone.toString(), nBinding.editTextTextContent.toString(), nBinding.editTextDate.toString());

        nBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Messages m = new Messages(nBinding.editTextTextTitle.getText().toString(), nBinding.editTextPhone.getText().toString(), nBinding.editTextTextContent.getText().toString(), nBinding.editTextDate.getText().toString());

                askPermissionAndSendSMS(nBinding.editTextPhone.getText().toString(),nBinding.editTextTextContent.getText().toString());

                //MessageList messageList1 = new MessageList();

               // messageList1.messagelist.add(m);
                DataStore.getInstance().getMessagesList().add(m);


                Context context = getApplicationContext();
                CharSequence text = "Message créé !";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();


                Intent intent2 = new Intent(CreateMessage.this, MessageList.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent2);


            }
        });


        setContentView(v);


    }


    private void askPermissionAndSendSMS(String phoneNum, String msg) {

        // With Android Level >= 23, you have to ask the user
        // for permission to send SMS.
        if (android.os.Build.VERSION.SDK_INT >=  android.os.Build.VERSION_CODES.M) { // 23

            // Check if we have send SMS permission
            int sendSmsPermisson = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.SEND_SMS);

            if (sendSmsPermisson != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSION_REQUEST_CODE_SEND_SMS
                );
                return;
            }
        }
        this.sendSMS_by_smsManager(phoneNum,msg);
    }

    private void sendSMS_by_smsManager(String phoneNumber, String message)  {

        try {
            // Get the default instance of the SmsManager
            SmsManager smsManager = SmsManager.getDefault();
            // Send Message
            smsManager.sendTextMessage(phoneNumber,
                    null,
                    message,
                    null,
                    null);

            Log.i( LOG_TAG,"Your sms has successfully sent!");
            Toast.makeText(getApplicationContext(),"Your sms has successfully sent!",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Log.e( LOG_TAG,"Your sms has failed...", ex);
            Toast.makeText(getApplicationContext(),"Your sms has failed... " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }



//    // When you have the request results
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        //
//        switch (requestCode) {
//            case MY_PERMISSION_REQUEST_CODE_SEND_SMS: {
//
//                // Note: If request is cancelled, the result arrays are empty.
//                // Permissions granted (SEND_SMS).
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    Log.i( LOG_TAG,"Permission granted!");
//                    Toast.makeText(this, "Permission granted!", Toast.LENGTH_LONG).show();
//
//                    this.sendSMS_by_smsManager();
//                }
//                // Cancelled or denied.
//                else {
//                    Log.i( LOG_TAG,"Permission denied!");
//                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_LONG).show();
//                }
//                break;
//            }
//        }
//    }

    // When results returned
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_PERMISSION_REQUEST_CODE_SEND_SMS) {
            if (resultCode == RESULT_OK) {
                // Do something with data (Result returned).
                Toast.makeText(this, "Action OK", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Action canceled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Action Failed", Toast.LENGTH_LONG).show();
            }
        }
    }


}