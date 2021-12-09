package com.example.lazymessages.mailList;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.lazymessages.detailMail.DetailMailActivity;
import com.example.lazymessages.createMail.Mails;
import com.example.lazymessages.databinding.DetailMailBinding;
import com.example.lazymessages.databinding.MailListBinding;
import com.google.gson.Gson;
import java.util.ArrayList;

/**
 * Activité création de la liste de messages et affichage de la liste
 */
public class MailListActivity extends AppCompatActivity implements OnMailClickListener {
    private MailListBinding nBinding;
    private MailListAdapter mailListAdapter;
    private DetailMailBinding detailMailBinding;

    ArrayList<Mails> mailList = new ArrayList<>();

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

        // Configuration de la liste de message
        //Messages A = new Messages("Bon anniversaire!", "+33620254149", "Je te souhaites un joyeux anniversaire !", "01/12/2021");
        //Messages B = new Messages("Texte généré", "+33620254151", "Nihil morati post haec militares avidi saepe turbarum adorti sunt Montium primum, qui divertebat in proximo, levi corpore senem atque morbosum, et hirsutis resticulis cruribus eius innexis divaricaturn sine spiramento ullo ad usque praetorium traxere praefecti.", "01/12/2021");
        //messagelist.add(A);
        //messagelist.add(B);
        //messagelist.addAll(DataStore.getInstance().getMessagesList());
        //messagelist.clear();

        nBinding.rvSms.setLayoutManager(new LinearLayoutManager(this , RecyclerView.VERTICAL,false));
        nBinding.rvSms.setAdapter(mailListAdapter);
        //messageListAdapter.FillArray(messagelist);
        setContentView(v);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //messagelist.clear();
        //mailList.addAll(DataStore.getInstance().getMailsList());
        mailListAdapter.FillArray(mailList);
    }

    /**
     * onClick sur le bouton 'info", renvoi vers la vue DetailMailActivity
     * @param m un mail
     */
    @Override
    public void onMailClick(Mails m) {
        Intent DetailMailIntent = new Intent(MailListActivity.this, DetailMailActivity.class);
        DetailMailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String mailStringified = new Gson().toJson(m);
        DetailMailIntent.putExtra("mail_clicked", mailStringified);
        startActivity(DetailMailIntent);
        //Toast.makeText(this, "my message : " + m , Toast.LENGTH_LONG).show();
    }

    /**
     * onClick sur le bouton 'supprimer", supprime le mail selectionné de la base de données et de la liste
     * @param m un mail
     */
    @Override
    public void deleteMailClicked(Mails m) {
        Toast.makeText(this, "Mail supprimé : " + m.objet, Toast.LENGTH_LONG).show();
        mailList.remove(m);
        mailListAdapter.FillArray(mailList);
        //L'enlever du DataStore
        //DataStore.getInstance().getMailsList().remove(m);
    }
}