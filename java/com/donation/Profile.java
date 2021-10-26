package com.donation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity implements View.OnClickListener {
    private TextView fullName;
    private TextView email;
    private TextView number;
    private FirebaseAuth mAuth;
    private String currentId;
    private DatabaseReference databaseRef;
    Button donate;
    Button signout;
    Button delete;

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_profile );

        fullName = findViewById(R.id.Name);
        email = findViewById(R.id.Email);
        number = findViewById(R.id.Number);
        mAuth = FirebaseAuth.getInstance();
        currentId = mAuth.getCurrentUser().getUid();
        databaseRef = FirebaseDatabase.getInstance().getReference().child("users/").child(currentId);

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                {
                    FirebaseUser user1 = mAuth.getCurrentUser();
                    String userId = user1.getUid();

//                    String profEmail = dataSnapshot.child(userId).child("email").getValue().toString();
//                    String profName = dataSnapshot.child(userId).child("name").getValue().toString();
//                    String profNumber = dataSnapshot.child(userId).child("number").getValue().toString();

//                    email.setText(profEmail);
//                    fullName.setText(profName);
//                    number.setText(profNumber);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        signout=(Button)findViewById(R.id.logout);
        delete=(Button)findViewById(R.id.delete);
        donate=(Button)findViewById(R.id.bt);

        donate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a=new Intent(Profile.this, HomeActivity.class);
                startActivity(a);
            }
        } );

        signout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        } );

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase.getInstance().getReference("users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                mAuth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        signOut();
                    }
                });
            }
        });

        findViewById(R.id.signOutButton).setOnClickListener(this);
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
 //       mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();
    }
    public void signOut(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(Profile.this, LoginActivity.class);
        startActivity(intent);
    }


//    private void revokeAccess() {
//        mAuth.signOut();
//        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this,
//                new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        updateUI(null);
//                    }
//                });
//    }

//    private void updateUI(FirebaseUser user) {
//        if (user != null) {
//            findViewById(R.id.signInButton).setVisibility(View.GONE);
//        } else {
//            findViewById(R.id.signInButton).setVisibility(View.VISIBLE);
//        }
//    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.signOutButton) {
            signOut();
        }
    }

}
