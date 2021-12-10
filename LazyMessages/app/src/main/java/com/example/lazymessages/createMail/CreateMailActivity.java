package com.example.lazymessages.createMail;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.example.lazymessages.DataStore;
import com.example.lazymessages.MailDao;
import com.example.lazymessages.mailList.MailListActivity;
import com.example.lazymessages.databinding.CreateMailBinding;

import java.util.List;

/**
 * Activité de création et envoi de mail
 */
public class CreateMailActivity extends AppCompatActivity implements OnReturnDateInformation, CreateMailPresenterCallback {
    private CreateMailBinding nBinding;
    private CreateMailPresenter createMailPresenter;
    private final MailEntity mail = new MailEntity();

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createMailPresenter = new CreateMailPresenter(this, this);
        nBinding = CreateMailBinding.inflate(getLayoutInflater());
        View v = nBinding.getRoot();
        Context context = getApplicationContext();

        initView();



        setContentView(v);
    }

    private void initView(){
        //CONFIG VIEW
        // Configuration de la ToolBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Créer un nouveau mail");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Creation fragment DatePicker
        DatePickerFragment frag = new DatePickerFragment(this);

        /**
         * onClick sur le textView date du layout create_mail, création de la date et de l'heure
         * @param v une View
         */
        nBinding.editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frag.show(getSupportFragmentManager(), "datePicker");
            }
        });

        /**
         * onClick sur le bouton 'créer' du layout create_mail, création du mail puis renvoi sur la vue MessageList
         * @param v une View
         */

        nBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Vérifie si l'adresse mail entrée est valide (***@***.com/.fr)
                if (!createMailPresenter.isEmailValid(nBinding.editTextMail.getText().toString())){
                    CharSequence text = "Email invalide";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                    toast.show();
                }else{
                    //Création du mail

                    mail.objet = nBinding.editTextTextObject.getText().toString();
                    mail.destinataire = nBinding.editTextMail.getText().toString();
                    mail.contenu = nBinding.editTextTextContent.getText().toString();

                    createMailPresenter.InsertInDB(mail);
                }
            }
        });

    }



    @Override
    public void onDateChosen(String day, String month, String year, String hour, String minute) {
        Context context = getApplicationContext();

        nBinding.editTextDate.setText(day+ "/" +month+ "/" +year+ " " +hour+ ":" +minute);
        createMailPresenter.setDateTime(day, month, year, hour, minute);
        mail.date = nBinding.editTextDate.getText().toString();

//        //Message de confirmation lorsque la nofication est créée
//        CharSequence text = "Notification programmée!";
//        int duration = Toast.LENGTH_SHORT;
//        Toast toast = Toast.makeText(context, text, duration);
//        toast.show();
    }

    @Override
    public void onMailInserted() {

        //Donner le DateTime a la notif
        createMailPresenter.setRecurringAlarm2(this, createMailPresenter.getDay(), createMailPresenter.getMonth(), createMailPresenter.getYear(), createMailPresenter.getHour(), createMailPresenter.getMinute());

        //Message de confirmation programmation du mail
        CharSequence text = "Nouveau mail programmé !";
        int duration = Toast.LENGTH_SHORT;

        this.runOnUiThread(new Runnable() {
            public void run() {
                Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.show();
            }
        });
        //Renvoi a la liste des mails
        Intent MailListAfterCreationIntent = new Intent(CreateMailActivity.this, MailListActivity.class);
        MailListAfterCreationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(MailListAfterCreationIntent);
    }
}


