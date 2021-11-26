package com.example.lazymessages;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.lazymessages.databinding.ActivityMainBinding;
import com.example.lazymessages.databinding.CreateMessageBinding;


public class CreateMessage extends AppCompatActivity {

    private CreateMessageBinding nBinding;
    private Messages messages;
    private MessageList messageList;

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

                Messages m = new Messages(nBinding.editTextTextTitle.toString(), nBinding.editTextPhone.toString(), nBinding.editTextTextContent.toString(), nBinding.editTextDate.toString());
                MessageList messageList1 = new MessageList();

               // messageList1.messagelist.add(m);
                DataStore.getInstance().getMessagesList().add(m);


                Intent intent2 = new Intent(CreateMessage.this, MessageList.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);


            }
        });


        setContentView(v);


    }
}