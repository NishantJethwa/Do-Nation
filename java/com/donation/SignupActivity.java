package com.donation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignupActivity extends AppCompatActivity {
    EditText editTextEmail, editTextPassword, names, contact;
    Button create;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    TextView signin, t1;

    ProgressDialog progressDialog;
    ImageButton viewpassword;
    EditText forpassword;
    int count = 0;

    //For firebase
    int counter = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        findViewById(android.R.id.content).setFocusableInTouchMode(true);

        create = findViewById(R.id.bt1);

        forpassword = findViewById(R.id.et1);
//        viewpassword = findViewById(R.id.viewpassword);
//        viewpassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                count++;
//                if(count % 2 == 0) {
//                    forpassword.setTransformationMethod(new PasswordTransformationMethod());
//                }
//                else {
//                    forpassword.setTransformationMethod(new SingleLineTransformationMethod());
//                }
//            }
//        });

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        editTextEmail = findViewById( R.id.et);
        editTextPassword = findViewById( R.id.et1);
        contact = findViewById( R.id.editContact);
        names = findViewById( R.id.editName);
        signin = findViewById(R.id.textView2);
        progressDialog = new ProgressDialog( SignupActivity.this);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        create.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        } );
        mAuth = FirebaseAuth.getInstance();

        t1= findViewById(R.id.textView2);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateIntent(view);
            }
        });

    }

    void registerUser() {

        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String name = names.getText().toString().trim();
        final String number = contact.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            names.setError( "Please enter your Name" );
            names.requestFocus();
            return;
        }

        if (editTextEmail.getText().toString().trim().length() < 0) {
            editTextEmail.setError( "Email is required" );
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher( email ).matches()) {
            editTextEmail.setError( "Please Enter a Valid Email ID" );
            editTextEmail.requestFocus();
            return;
        }

        if (editTextPassword.getText().toString().trim().length() < 0) {
            editTextPassword.setError( "Password id required" );
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError( "Minimum Length of Password is 6" );
            editTextPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(number)) {
            contact.setError( "Please enter your contact details" );
            contact.requestFocus();
            return;
        }

        if (number.length() < 10) {
            contact.setError( "Number Should Be of 10 Digits" );
            contact.requestFocus();
            return;
        }

        if (number.length() > 10) {
            contact.setError( "Number Should Be of 10 Digits" );
            contact.requestFocus();
            return;
        }

        progressDialog.setMessage( "Signing up please Wait..." );
        progressDialog.show();

        mAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                        boolean isNewUser = task.getResult().getSignInMethods().isEmpty();

                        if (isNewUser) {
                            mAuth.createUserWithEmailAndPassword( email, password ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        User user = new User(email, name, number);
                                        editTextEmail = findViewById(R.id.et);
                                        String email = editTextEmail.getText().toString();

                                        FirebaseUser user1 =  mAuth.getCurrentUser();
                                        String userId = user1.getUid();

                                        FirebaseDatabase.getInstance().getReference("users").child(userId).child("Name").setValue(name);
                                        FirebaseDatabase.getInstance().getReference("users").child(userId).child("Email").setValue(email);
                                        FirebaseDatabase.getInstance().getReference("users/" + userId).child("Number").setValue(number)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            counter++;
                                                            Toast.makeText(getApplicationContext(), "User Registered Successfully",
                                                                    Toast.LENGTH_SHORT).show();
                                                        }
                                                        else {
                                                            Toast.makeText(SignupActivity.this, "Error: Email Already Exists",
                                                                    Toast.LENGTH_SHORT).show();
                                                        }
                                                        progressDialog.dismiss();
                                                    }
                                                });
                                    }
                                }
                            } );
                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(SignupActivity.this,
                                    "An account has already been created or has signed in using Google from this email "
                                    ,Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();

        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom())
                hideKeyboard(SignupActivity.this);
        }
        return super.dispatchTouchEvent(ev);
    }

    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    int doubleBackToExitPressed = 1;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressed == 2) {
            finishAffinity();
            System.exit(0);
        }
        else {
            doubleBackToExitPressed++;
            Toast.makeText(this, "Please press Back again to exit app", Toast.LENGTH_SHORT).show();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressed=1;
            }
        }, 2000);
    }

    public void animateIntent(View view) {

        // Ordinary Intent for launching a new activity
        Intent intent = new Intent(this, LoginActivity.class);

        // Get the transition name from the string
        String transitionName = getString(R.string.transition_string);

        // Define the view that the animation will start from
        View viewStart = findViewById(R.id.cardView);

        ActivityOptionsCompat options =

                ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                        viewStart,   // Starting view
                        transitionName    // The String
                );
        //Start the Intent
        ActivityCompat.startActivity(this, intent, options.toBundle());


    }


}