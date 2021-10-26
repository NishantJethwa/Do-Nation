package com.donation;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Food extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    FirebaseDatabase database=FirebaseDatabase.getInstance();
    Button button, bt1;
    TextView textView;
    TextView tv;
    Calendar mCurrentDate;
    int day, month, year;
    RadioButton rb1;
    RadioButton rb2;
    RadioButton rb3;
    RadioButton rb4;
    RadioButton rb5;
    RadioButton rb6;
    RadioButton rb7;
    DatabaseReference mRef;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    String fdate;
    String ftime;
    int height = Resources.getSystem().getDisplayMetrics().heightPixels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_food );

        FirebaseApp.initializeApp( Food.this );
        mAuth = FirebaseAuth.getInstance();

        textView=(TextView)findViewById(R.id.textView16);

        tv = (TextView) findViewById( R.id.textView4 );
        mCurrentDate = Calendar.getInstance();

        day = mCurrentDate.get( Calendar.DAY_OF_MONTH );
        month = mCurrentDate.get( Calendar.MONTH );
        year = mCurrentDate.get( Calendar.YEAR );

        rb1 = findViewById(R.id.radioButton1);
        rb2 = findViewById(R.id.radioButton2);
        rb3 = findViewById(R.id.radioButton3);
        rb4 = findViewById(R.id.radioButton4);
        rb5 = findViewById(R.id.radioButton5);
        rb6 = findViewById(R.id.radioButton6);
        rb7 = findViewById(R.id.radioButton7);

//        bt1 = findViewById( R.id.imageView );
//        bt1.setHeight(height/3);

        mRef = FirebaseDatabase.getInstance().getReference("users");

        button = (Button) findViewById( R.id.bt2 );

        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checking();
                Intent b = new Intent( Food.this, ChooseFood.class );
                startActivity( b );
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

        tv.setText( day + "/" + month + "/" + year );

        tv.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerDialog datePickerDialog = new DatePickerDialog( Food.this,new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear + 1;
                        tv.setText( dayOfMonth + "/" + monthOfYear + "/" + year );
                        fdate = (dayOfMonth + "-" + monthOfYear + "-" + year);
                        //Toast.makeText(Food.this, fdate, Toast.LENGTH_SHORT).show();
                    }

                }, year, month, day );
                datePickerDialog.show();
            }
        } );

        textView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker= new TimePickerFragment();
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

        }        textView.setText(hourOfDay + " : " +minute+" "+time);
        ftime = hourOfDay + " : " +minute+" "+time;
    }

    private void checking()
    {
        FirebaseUser user1 = mAuth.getCurrentUser();
        final String userId = user1.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users/").child(userId);

        if(rb1.isChecked()) {
            DatabaseReference myref = database.getReference("users/" + userId).child("Donations")
                    .child(String.valueOf(fdate)).child(ftime).child("Food").child("Type:");
            myref.setValue("Veg");
        }

        if(rb2.isChecked()) {
            DatabaseReference myref = database.getReference("users/" + userId).child("Donations")
                    .child(String.valueOf(fdate)).child(ftime).child("Food").child("Type:");
            myref.setValue("Non-Veg");
        }

        if(rb3.isChecked()) {
            DatabaseReference myref = database.getReference("users/" + userId).child("Donations")
                    .child(String.valueOf(fdate)).child(ftime).child("Food").child("Type:");
            myref.setValue("Both");
        }

        if(rb4.isChecked()) {
            DatabaseReference myref3 = database.getReference("users/" + userId).child("Donations")
                    .child(String.valueOf(fdate)).child(ftime).child("Food").child("Serving:");
            myref3.setValue("10-30");
        }

        if(rb5.isChecked()) {
            DatabaseReference myref3 = database.getReference("users/" + userId).child("Donations")
                    .child(String.valueOf(fdate)).child(ftime).child("Food").child("Serving:");
            myref3.setValue("30-50");
        }

        if(rb6.isChecked()) {
            DatabaseReference myref3 = database.getReference("users/" + userId).child("Donations")
                    .child(String.valueOf(fdate)).child(ftime).child("Food").child("Serving:");
            myref3.setValue("50-100");
        }

        if(rb7.isChecked()) {
            DatabaseReference myref3 = database.getReference("users/" + userId).child("Donations")
                    .child(String.valueOf(fdate)).child(ftime).child("Food").child("Serving:");
            myref3.setValue("100+");
        }
    }
}
