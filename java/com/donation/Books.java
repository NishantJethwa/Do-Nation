package com.donation;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
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

public class Books extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{

    FirebaseDatabase database=FirebaseDatabase.getInstance();
    Button button;
    TextView textView2;
    TextView textView;
    Calendar mCurrentDate;
    int day, month, year;
    CheckBox mFirstCheckBox;
    CheckBox mSecondCheckBox;
    CheckBox mThirdCheckBox;
    CheckBox mFourthCheckBox;
    CheckBox mFifthCheckBox;
    CheckBox mSixthCheckBox;
    DatabaseReference mRef;
    TextView tv;
    EditText editText;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    String fdate;
    String ftime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_books );

        FirebaseApp.initializeApp( Books.this );
        mAuth = FirebaseAuth.getInstance();

        textView=(TextView)findViewById(R.id.textView6);
        textView2=(TextView) findViewById(R.id.textview10);
        editText=(EditText)findViewById(R.id.et1);

        tv = (TextView) findViewById( R.id.textView4 );
        mCurrentDate = Calendar.getInstance();

        day = mCurrentDate.get( Calendar.DAY_OF_MONTH );
        month = mCurrentDate.get( Calendar.MONTH );
        year = mCurrentDate.get( Calendar.YEAR );

        mFirstCheckBox = findViewById(R.id.cb1);
        mSecondCheckBox = findViewById(R.id.cb2);
        mThirdCheckBox = findViewById(R.id.cb3);
        mFourthCheckBox = findViewById(R.id.cb4);
        mFifthCheckBox = findViewById(R.id.cb5);
        mSixthCheckBox = findViewById(R.id.cb6);

        mRef = FirebaseDatabase.getInstance().getReference("users");

        button=(Button)findViewById(R.id.bt1);

        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checking();
                Intent intent = new Intent( Books.this, ChooseBooks.class );
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

        textView2.setText( day + "/" + month + "/" + year );

        textView2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerDialog datePickerDialog = new DatePickerDialog( Books.this, R.style.DateBooks, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear + 1;
                        textView2.setText( dayOfMonth + "/" + monthOfYear + "/" + year );
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
                DialogFragment timePicker= new TimePickerFragment2();
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

        String bnumber = editText.getText().toString().trim();
        DatabaseReference myref1 = database.getReference("users/" + userId).child("Donations")
                .child(String.valueOf(fdate)).child(ftime).child("Books").child("Number of Books: ");
        myref1.setValue(bnumber);

        if(mFirstCheckBox.isChecked()){

            DatabaseReference myref = database.getReference("users/" + userId).child("Donations")
                    .child(String.valueOf(fdate)).child(ftime).child("Books").child("Age-Group1");
            myref.setValue("Nursery & Pre-School");

        }

        if(mSecondCheckBox.isChecked()){

            DatabaseReference myref = database.getReference("users/" + userId).child("Donations")
                    .child(String.valueOf(fdate)).child(ftime).child("Books").child("Age-Group2");
            myref.setValue("Primary School");

        }

        if(mThirdCheckBox.isChecked()){

            DatabaseReference myref = database.getReference("users/" + userId).child("Donations")
                    .child(String.valueOf(fdate)).child(ftime).child("Books").child("Age-Group3");
            myref.setValue("Secondary School");

        }

        if(mFourthCheckBox.isChecked()){

            DatabaseReference myref = database.getReference("users/" + userId).child("Donations")
                    .child(String.valueOf(fdate)).child(ftime).child("Books").child("Age-Group4");
            myref.setValue("Junior College");

        }

        if(mFifthCheckBox.isChecked()){

            DatabaseReference myref = database.getReference("users/" + userId).child("Donations")
                    .child(String.valueOf(fdate)).child(ftime).child("Books").child("Age-Group5");
            myref.setValue("Degree College");

        }

        if(mSixthCheckBox.isChecked()){

            DatabaseReference myref = database.getReference("users/" + userId).child("Donations")
                    .child(String.valueOf(fdate)).child(ftime).child("Books").child("Age-Group6");
            myref.setValue("Other");

        }
    }
}
