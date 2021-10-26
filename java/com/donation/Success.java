package com.donation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class Success extends AppCompatActivity {
    GifImageView mGigImageView;
    Button back;
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        mGigImageView = (GifImageView) findViewById(R.id.mgif);
        back = (Button)findViewById(R.id.button2);
        imageButton=(ImageButton)findViewById(R.id.imageButton5);


        GifDrawable gifDrawable = null;
        try {
            gifDrawable = new GifDrawable(getResources(), R.drawable.success);
            gifDrawable.setLoopCount(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mGigImageView.setImageDrawable(gifDrawable);

        back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Success.this, HomeActivity.class);
                startActivity(intent);
            }
        } );

        imageButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(Intent.ACTION_CALL); //ACTION_DIAL WILL OPEN THE PHONE (CONTACT) BUT WILL NOT CALL ;;;; TO MAKE AN MAKE A CALL ME CHANGE IT TO CALL(ACTION_CALL)
                i.setData( Uri.parse("tel:1234567890")); //WE HAVE GIVEN THE PERMISSION TO THE APP TO ALLOW TO MAKE PHONE CALL FROM MAINFEST BUT FOR VERSIONS MARSHMELLO AND ABOVE WE HAVE TO ALSO TAKE USERS PERMISSION
                //startActivity(i); AS WE ARE TAKING PERMISSION INSIDE SO WE HAVE COMMENTED IT
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){       // M IS FOR MARSHMELLOW
                    if (ContextCompat.checkSelfPermission(Success.this , Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
                    {
                        ActivityCompat.requestPermissions(Success.this, new String[] //WE ARE CHANGING THE ARRAY KA THING
                                {Manifest.permission.CALL_PHONE}, 1);//REQUEST CODE IS '0' BY DEFALUT WHICH MEANS ACCESS ID DENIED FROM THE USER, SO WE HAVE TO SET IT TO 1 TO GAIN ACCESS.
                    }
                    else
                    {
                        startActivity(i);
                    }


                }
                else
                {
                    startActivity(i);
                }

            }
        } );

    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }

}
