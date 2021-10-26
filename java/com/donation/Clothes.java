package com.donation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Clothes extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

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
    Button showmore;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter adapter;

    private RecyclerAdapter1 adapter1;

    List<TextUtils> textUtilsList, textUtilsList1;

//    int count = RecyclerAdapter.getData();
//    int count1 = RecyclerAdapter1.getData1();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes);

        recyclerView = findViewById(R.id.rc1);
        layoutManager = new LinearLayoutManager(Clothes.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        textUtilsList = new ArrayList<>();
        textUtilsList.add(new TextUtils("Share At Doorstep India", R.drawable.pic1));
        textUtilsList.add(new TextUtils("Shanti Avedna Cancer Hospice", R.drawable.pic2));

        textUtilsList1 = new ArrayList<>();
        textUtilsList1.add(new TextUtils("Share At Doorstep India", R.drawable.pic1));
        textUtilsList1.add(new TextUtils("Shanti Avedna Cancer Hospice", R.drawable.pic2));
        textUtilsList1.add(new TextUtils("Sneha Sadan", R.drawable.pic3));
        textUtilsList1.add(new TextUtils("Wishing Well", R.drawable.pic4));
        textUtilsList1.add(new TextUtils("Goonj", R.drawable.pic5));
        textUtilsList1.add(new TextUtils("Green Yatra", R.drawable.pic6));

        adapter = new RecyclerAdapter(getApplicationContext(), textUtilsList);
        recyclerView.setAdapter(adapter);

        showmore = findViewById(R.id.bt2);
        showmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showmore.setVisibility(View.GONE);
                adapter1 = new RecyclerAdapter1(getApplicationContext(), textUtilsList1);
                recyclerView.setAdapter(adapter1);
            }
        });

        FirebaseApp.initializeApp( Clothes.this );
        mAuth = FirebaseAuth.getInstance();

        textView=(TextView)findViewById(R.id.textView);
        textView1=(TextView) findViewById(R.id.textView1);

        mCurrentDate = Calendar.getInstance();

        day = mCurrentDate.get( Calendar.DAY_OF_MONTH );
        month = mCurrentDate.get( Calendar.MONTH );
        year = mCurrentDate.get( Calendar.YEAR );

        mRef = FirebaseDatabase.getInstance().getReference("users");

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
                final DatePickerDialog datePickerDialog = new DatePickerDialog( Clothes.this, R.style.DateClothes, new DatePickerDialog.OnDateSetListener(){
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
                DialogFragment timePicker= new TimePickerFragment4();
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