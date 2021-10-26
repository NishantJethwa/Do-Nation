package com.donation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Money extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{

    FirebaseDatabase database=FirebaseDatabase.getInstance();
    Button button;
    TextView textView;
    TextView textView1;
    Calendar mCurrentDate;
    int day, month, year;
    DatabaseReference mRef;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    String fdate;
    String ftime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);

        FirebaseApp.initializeApp( Money.this );
        mAuth = FirebaseAuth.getInstance();

        textView=(TextView) findViewById(R.id.textView);
        textView1=(TextView) findViewById(R.id.textView1);

        mCurrentDate = Calendar.getInstance();

        day = mCurrentDate.get( Calendar.DAY_OF_MONTH );
        month = mCurrentDate.get( Calendar.MONTH );
        year = mCurrentDate.get( Calendar.YEAR );

        mRef = FirebaseDatabase.getInstance().getReference("users");

        button=(Button)findViewById(R.id.bt2);

        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checking();
                Intent intent = new Intent( Money.this, ChooseMoney.class );
                startActivity( intent );
            }});

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    Intent intent = getIntent();
                    String name1 = intent.getStringExtra("username");
                    intent.putExtra("username", name1);
                }
            }
        };

        textView.setText( day + "/" + month + "/" + year );

        textView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerDialog datePickerDialog = new DatePickerDialog( Money.this, R.style.DateMoney, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear + 1;
                        textView.setText( dayOfMonth + "/" + monthOfYear + "/" + year );
                        fdate = (dayOfMonth + "-" + monthOfYear + "-" + year);
                        //Toast.makeText(Food.this, fdate, Toast.LENGTH_SHORT).show();
                    }

                }, year, month, day );
                datePickerDialog.show();
            }
        } );



        textView1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker= new TimePickerFragment3();
                timePicker.show(getSupportFragmentManager(),"time picker");
            }
        } );

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener( authStateListener );
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time;
        if(hourOfDay>12)
        {
            hourOfDay -=12;
            time="PM";
        }
        else if(hourOfDay == 0)
        {
            hourOfDay += 12;
            time="AM";
        }
        else if(hourOfDay == 12)
        {
            time="PM";
        }
        else
        {
            time="AM";

        }        textView1.setText(hourOfDay + " : " +minute+" "+time);
        ftime = hourOfDay + " : " +minute+" "+time;
    }

}