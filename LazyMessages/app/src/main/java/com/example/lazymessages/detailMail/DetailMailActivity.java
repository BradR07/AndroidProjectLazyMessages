package com.example.lazymessages.detailMail;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lazymessages.DataStore;
import com.example.lazymessages.MailDao;
import com.example.lazymessages.createMail.MailEntity;
import com.example.lazymessages.databinding.DetailMailBinding;
import com.google.gson.Gson;

/**
 * Activité 'Détail' d'un mail
 */
public class DetailMailActivity extends AppCompatActivity {
    private DetailMailBinding nBinding;
    private MailEntity mailEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nBinding = DetailMailBinding.inflate(getLayoutInflater());
        View v = nBinding.getRoot();
        mailEntity = new Gson().fromJson(getIntent().getStringExtra("mail_clicked"), MailEntity.class);

        // Configuration de la ToolBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Détail du mail");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                //Initialise le 'DataStore'
                DataStore db = DataStore.getDatabase(getApplicationContext());
                //Récupère toute la liste 'MAIL'
                MailDao mailDao = db.mailDao();
            }
        });
        nBinding.textView2.setText(mailEntity.objet);
        nBinding.destinataire.setText(mailEntity.destinataire);
        nBinding.message.setText(mailEntity.contenu);
        nBinding.date.setText(mailEntity.date);
        setContentView(v);
    }
}