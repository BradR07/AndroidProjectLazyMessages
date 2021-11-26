package com.example.lazymessages;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lazymessages.databinding.DetailMessageBinding;
import com.google.gson.Gson;


public class DetailMessage extends AppCompatActivity {

    private DetailMessageBinding nBinding;
    private Messages messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nBinding = DetailMessageBinding.inflate(getLayoutInflater());
        View v = nBinding.getRoot();

        messages = new Gson().fromJson(getIntent().getStringExtra("mesage_clicked"), Messages.class);
        // Configuration de la ToolBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Détail du message :");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        nBinding.textView2.setText(messages.contenu);

        setContentView(v);
    }
}
