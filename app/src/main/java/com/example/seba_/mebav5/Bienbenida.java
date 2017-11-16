package com.example.seba_.mebav5;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class Bienbenida extends AppCompatActivity {
    private final int DURACION_SPLASH = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bienbenida);

        new Handler().postDelayed(new Runnable(){
            public void run(){
                Intent intent = new Intent(Bienbenida.this, Login.class);
                startActivity(intent);
                finish();
            }
        }, DURACION_SPLASH);

    }
}
