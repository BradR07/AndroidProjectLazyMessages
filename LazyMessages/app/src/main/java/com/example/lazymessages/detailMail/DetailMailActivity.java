package com.example.lazymessages.detailMail;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lazymessages.DataStore;
import com.example.lazymessages.MailDao;
import com.example.lazymessages.Singleton;
import com.example.lazymessages.createMail.MailEntity;
import com.example.lazymessages.createMail.Mails;
import com.example.lazymessages.databinding.DetailMailBinding;
import com.google.gson.Gson;


/**
 * Activité detail d'un message
 */
public class DetailMailActivity extends AppCompatActivity {

    private DetailMailBinding nBinding;
    private Mails mails;
    private MailEntity mailEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nBinding = DetailMailBinding.inflate(getLayoutInflater());
        View v = nBinding.getRoot();
        mailEntity = new Gson().fromJson(getIntent().getStringExtra("mail_clicked"), MailEntity.class);

        // Configuration de la ToolBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Détail du mail :");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                //INSERT HERE YOUR CODE TO WRITE IN DB
                //INIT DATASTORE
                DataStore db = DataStore.getDatabase(getApplicationContext());
                //RECUP TOUTE LA LISTE MAIL
                MailDao mailDao = db.mailDao();
                //Get le mail selectionné

                //Singleton monSingeton = Singleton.getInstanceSingleton();



                //mailEntity=Singleton.getInstanceSingleton();


               //mailEntity = mailDao.getOneById(monSingeton.getMonMail().id);

                //System.out.println(mailEntity.contenu);

                //System.out.println(mailEntity);
            }
        });

        nBinding.textView2.setText(mailEntity.objet);
        nBinding.destinataire.setText(mailEntity.destinataire);
        nBinding.message.setText(mailEntity.contenu);
        nBinding.date.setText(mailEntity.date);

        setContentView(v);
    }
}
