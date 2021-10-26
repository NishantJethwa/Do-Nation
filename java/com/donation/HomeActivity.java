package com.donation;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {
    Button bt1, bt2, bt3, bt4;
    FloatingActionButton logout;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    int height = Resources.getSystem().getDisplayMetrics().heightPixels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home);

        bt1 = findViewById( R.id.button1 );
        bt1.setHeight(height/4);

        bt2 = findViewById( R.id.button2 );
        bt2.setHeight(height/4);

        bt3 = findViewById( R.id.button3 );
        bt3.setHeight(height/4);

        bt4 = findViewById( R.id.button4 );
        bt4.setHeight(height/4);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(HomeActivity.this).setTitle("Logout")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                                startActivity(i);
                            }

                        }).setNegativeButton("No", null).show();
                moveTaskToBack(false);
            }
        });

        bt1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent( HomeActivity.this, Food.class );
                startActivity( a );
            }
        } );

        bt2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( HomeActivity.this, Books.class );
                startActivity( i );
            }
        } );

        bt3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( HomeActivity.this, Money.class );
                startActivity( i );
            }
        } );

        bt4.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( HomeActivity.this, Clothes.class );
                startActivity( i );
            }
        } );

    }

//    @Override
//    public void onBackPressed() {
//        new AlertDialog.Builder(this).setTitle("Exit")
//                .setMessage("Are you sure you want to exit?")
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        finishAffinity();
//                    }
//
//                }).setNegativeButton("No", null).show();
//        moveTaskToBack(false);
//    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please Click Back Again to Exit App", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}