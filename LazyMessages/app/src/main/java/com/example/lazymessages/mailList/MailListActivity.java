package com.example.lazymessages.mailList;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.lazymessages.DataStore;
import com.example.lazymessages.MailDao;
import com.example.lazymessages.Singleton;
import com.example.lazymessages.createMail.MailEntity;
import com.example.lazymessages.detailMail.DetailMailActivity;
import com.example.lazymessages.createMail.Mails;
import com.example.lazymessages.databinding.DetailMailBinding;
import com.example.lazymessages.databinding.MailListBinding;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

/**
 * Activité création de la liste de messages et affichage de la liste
 */
public class MailListActivity extends AppCompatActivity implements OnMailClickListener {
    private MailListBinding nBinding;
    private MailListAdapter mailListAdapter;
    private DetailMailBinding detailMailBinding;
    private List<MailEntity> mailEntityList;

    //ArrayList<MailEntity> mailList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nBinding = MailListBinding.inflate(getLayoutInflater());
        View v = nBinding.getRoot();
        mailListAdapter = new MailListAdapter(this);
        detailMailBinding = DetailMailBinding.inflate(getLayoutInflater());

        // Configuration de la ToolBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Mails programmés");
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
                mailEntityList = mailDao.getAll();
            }
        });


        //GIVE TO ADAPTER

        nBinding.rvSms.setLayoutManager(new LinearLayoutManager(this , RecyclerView.VERTICAL,false));
        nBinding.rvSms.setAdapter(mailListAdapter);

        setContentView(v);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //messagelist.clear();
        DataStore db = DataStore.getDatabase(getApplicationContext());
        //RECUP TOUTE LA LISTE MAIL
        MailDao mailDao = db.mailDao();
        mailEntityList = mailDao.getAll();
        this.mailListAdapter.FillArray(mailEntityList);
    }

    /**
     * onClick sur le bouton 'info", renvoi vers la vue DetailMailActivity
     * @param m un mail
     */
    @Override
    public void onMailClick(MailEntity m) {
        Intent DetailMailIntent = new Intent(MailListActivity.this, DetailMailActivity.class);
        DetailMailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String mailStringified = new Gson().toJson(m);
        DetailMailIntent.putExtra("mail_clicked", mailStringified);
        startActivity(DetailMailIntent);
        //Toast.makeText(this, "my message : " + m , Toast.LENGTH_LONG).show();


        //Singleton.getInstanceSingleton(m);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                //INSERT HERE YOUR CODE TO WRITE IN DB
                //INIT DATASTORE
                DataStore db = DataStore.getDatabase(getApplicationContext());
                //RECUP TOUTE LA LISTE MAIL
                MailDao mailDao = db.mailDao();
                //Delete le mail selectionné
                //Singleton.getInstanceSingleton(mailDao.getOneById(m.id));
            }
        });

    }

    /**
     * onClick sur le bouton 'supprimer", supprime le mail selectionné de la base de données et de la liste
     * @param m un mail
     */
    @Override
    public void deleteMailClicked(MailEntity m) {
        Toast.makeText(this, "Mail supprimé : " + m.objet, Toast.LENGTH_LONG).show();
        //Creer nouvelle liste

        //mettre liste courrante dans la nouvelle
        DataStore db = DataStore.getDatabase(getApplicationContext());
        //RECUP TOUTE LA LISTE MAIL
        MailDao mailDao = db.mailDao();
        List<MailEntity> mailEntityList = mailDao.getAll();

        List<MailEntity> newMailEntityList = mailEntityList;

        //Remove de la nouvelle liste
        //ajouter la nouvelle liste
        newMailEntityList.remove(m);
        mailListAdapter.FillArray(newMailEntityList);

        //CA RAFRAICHIS PAS -> ESSAYER DE RAFRAICHIR APRES LA SUPPR

        //Suppr de la BDD

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                //INSERT HERE YOUR CODE TO WRITE IN DB
                //INIT DATASTORE
                DataStore db = DataStore.getDatabase(getApplicationContext());
                //RECUP TOUTE LA LISTE MAIL
                MailDao mailDao = db.mailDao();
                //Delete le mail selectionné
                mailDao.deleteById(m.id);
            }
        });

    }


}