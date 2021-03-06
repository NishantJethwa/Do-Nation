package com.donation;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Anonymous extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private static final String TAG = "AnonymousAuth";
    private EditText mEmailField;
    private EditText mPasswordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_anonymous );

        // [START declare_auth]
            // [START initialize_auth]
            // Initialize Firebase Auth
            mAuth = FirebaseAuth.getInstance();
            // [END initialize_auth]

            // Fields
            mEmailField = findViewById(R.id.fieldEmail);
            mPasswordField = findViewById(R.id.fieldPassword);

            // Click listeners
            findViewById(R.id.buttonAnonymousSignIn).setOnClickListener(this);
            findViewById(R.id.buttonAnonymousSignOut).setOnClickListener(this);
//            findViewById(R.id.buttonLinkAccount).setOnClickListener(this);
        }

        // [START on_start_check_user]
        @Override
        public void onStart() {
            super.onStart();
            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = mAuth.getCurrentUser();
            updateUI(currentUser);
        }
        // [END on_start_check_user]

        private void signInAnonymously() {
            // [START signin_anonymously]
            mAuth.signInAnonymously()
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInAnonymously:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInAnonymously:failure", task.getException());
                                Toast.makeText(Anonymous.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }

                        }
                    });
            // [END signin_anonymously]
        }

        private void signOut() {
            mAuth.signOut();
            updateUI(null);
        }

        private void linkAccount() {
            // Make sure form is valid
            if (!validateLinkForm()) {
                return;
            }

            // Get email and password from form
            String email = mEmailField.getText().toString();
            String password = mPasswordField.getText().toString();

            // Create EmailAuthCredential with email and password
            AuthCredential credential = EmailAuthProvider.getCredential(email, password);

            // [START link_credential]
            mAuth.getCurrentUser().linkWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "linkWithCredential:success");
                                FirebaseUser user = task.getResult().getUser();
                                updateUI(user);
                            } else {
                                Log.w(TAG, "linkWithCredential:failure", task.getException());
                                Toast.makeText(Anonymous.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        }

        private boolean validateLinkForm() {
            boolean valid = true;

            String email = mEmailField.getText().toString();
            if (TextUtils.isEmpty(email)) {
                mEmailField.setError("Required.");
                valid = false;
            } else {
                mEmailField.setError(null);
            }

            String password = mPasswordField.getText().toString();
            if (TextUtils.isEmpty(password)) {
                mPasswordField.setError("Required.");
                valid = false;
            } else {
                mPasswordField.setError(null);
            }

            return valid;
        }

        private void updateUI(FirebaseUser user) {

            boolean isSignedIn = (user != null);

            // Status text
            if (isSignedIn) {
            } else {
            }

            // Button visibility
            findViewById(R.id.buttonAnonymousSignIn).setEnabled(!isSignedIn);
            findViewById(R.id.buttonAnonymousSignOut).setEnabled(isSignedIn);
//            findViewById(R.id.buttonLinkAccount).setEnabled(isSignedIn);
        }

        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.buttonAnonymousSignIn) {
                signInAnonymously();
            } else if (i == R.id.buttonAnonymousSignOut) {
                signOut();
//            } else if (i == R.id.buttonLinkAccount) {
//                linkAccount();
            }
        }
}
