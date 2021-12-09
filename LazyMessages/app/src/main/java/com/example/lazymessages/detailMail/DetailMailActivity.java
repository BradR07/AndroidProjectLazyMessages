package com.example.lazymessages.detailMail;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lazymessages.createMail.Mails;
import com.example.lazymessages.databinding.DetailMailBinding;
import com.google.gson.Gson;


/**
 * Activité detail d'un message
 */
public class DetailMailActivity extends AppCompatActivity {

    private DetailMailBinding nBinding;
    private Mails mails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nBinding = DetailMailBinding.inflate(getLayoutInflater());
        View v = nBinding.getRoot();
        mails = new Gson().fromJson(getIntent().getStringExtra("mail_clicked"), Mails.class);

        // Configuration de la ToolBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Détail du mail :");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        nBinding.textView2.setText(mails.objet);
        nBinding.destinataire.setText(mails.destinataire);
        nBinding.message.setText(mails.contenu);
        nBinding.date.setText(mails.date);

        setContentView(v);
    }
}
