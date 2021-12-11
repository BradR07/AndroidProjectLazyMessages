package com.example.lazymessages;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import com.example.lazymessages.databinding.SplashScreenBinding;

/**
 * Activité SplashScreen, écran de chargement de l'application
 */
public class SplashScreen extends AppCompatActivity {
    private SplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = SplashScreenBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(v);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            public void run(){
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        }, 2000);
    }
}