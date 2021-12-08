package com.example.lazymessages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.lazymessages.databinding.ActivityMainBinding;

/**
 * Activité principale acceuil
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding nBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View v = nBinding.getRoot();

        nBinding.createmail.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick sur le bouton 'créer un nouveau message", renvoi vers la vue CreateMessages
             * @param v une vue
             */
            @Override
            public void onClick(View v) {
                Intent CreateMailIntent = new Intent(MainActivity.this, CreateMail.class);
                CreateMailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(CreateMailIntent);
            }
        });

        nBinding.maillist.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick sur le bouton 'mes mails programmés", renvoi vers la vue MailList
             * @param v une vue
             */
            @Override
            public void onClick(View v) {
                Intent MailListIntent = new Intent(MainActivity.this, MailList.class);
                MailListIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(MailListIntent);
            }
        });

        setContentView(v);
        getSupportActionBar().hide();
    }
}