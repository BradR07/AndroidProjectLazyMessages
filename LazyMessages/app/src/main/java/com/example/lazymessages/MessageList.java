package com.example.lazymessages;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lazymessages.databinding.CreateMessageBinding;
import com.example.lazymessages.databinding.DetailMessageBinding;
import com.example.lazymessages.databinding.MessageListBinding;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MessageList extends AppCompatActivity implements OnSmsClickLstener {

    private MessageListBinding nBinding;
    private MessageListAdapter messageListAdapter ;
    private DetailMessageBinding detailMessageBinding;
    ArrayList<Messages> messagelist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nBinding = MessageListBinding.inflate(getLayoutInflater());
        View v = nBinding.getRoot();
        messageListAdapter = new MessageListAdapter(this);

        detailMessageBinding = DetailMessageBinding.inflate(getLayoutInflater());

        // Configuration de la ToolBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Messages programmés");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Configuration de la liste de message

        Messages A = new Messages("Bon aniv!", "+33620254149", "Je te souhaites un joyeux anniversaire !", "01/12/2021");
        Messages B = new Messages("Bon Noel!", "+33620254150", "Je te souhaites un joyeux Noel !", "01/12/2021");
        Messages C = new Messages("Bon enterrement!", "+33620254151", "Je te souhaites un joyeux enterrement !", "01/12/2021");
        messagelist.add(A);
        messagelist.add(B);
        messagelist.add(C);
        messagelist.addAll(DataStore.getInstance().getMessagesList());

        nBinding.rvSms.setLayoutManager(new LinearLayoutManager(this , RecyclerView.VERTICAL,false));
        nBinding.rvSms.setAdapter(messageListAdapter);

        messageListAdapter.FillArray(messagelist);

        setContentView(v);


    }

    @Override
    protected void onResume() {
        super.onResume();
        messagelist.addAll(DataStore.getInstance().getMessagesList());
        messageListAdapter.FillArray(messagelist);

    }

    @Override
    public void onSmsClick(Messages m) {

        Intent intent = new Intent(MessageList.this, DetailMessage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String messageStringified = new Gson().toJson(m);
        intent.putExtra("mesage_clicked", messageStringified);
        startActivity(intent);
        //Toast.makeText(this, "my message : " + m , Toast.LENGTH_LONG).show();
    }
}