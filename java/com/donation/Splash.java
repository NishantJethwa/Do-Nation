package com.donation;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );

        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(2500);
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    Intent intent = new Intent(Splash.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        };
        timer.start();
    }
    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
    }
