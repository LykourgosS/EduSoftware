package com.unipi.lykourgoss.edusoftware;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.unipi.lykourgoss.edusoftware.models.User;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private TextView textViewSignIn;

    private FirebaseAuth mAuth;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        //button on click method for creating a new account
        findViewById(R.id.buttonCreateAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

        //underline textView for redirecting to create account
        textViewSignIn = findViewById(R.id.textViewSignIn);
        textViewSignIn.setText(Html.fromHtml("<u>Already have an account? Sign in</u>"));

        //textView on click method for going to sign in activity
        textViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
            }
        });
    }

    //method for creating new accounts
    private void createAccount() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Minimum length for password should be 6");
            editTextPassword.requestFocus();
            return;
        }

        final AlertDialog dialog = Dialog.progressbarAction(this, Dialog.LOADING);

        //if all of the above are OK create the new account
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) { //New account created successfully
                            addAccountToFirebaseDatabase(dialog);
                        } else { //something went wrong in the creation of the new account
                            dialog.cancel();
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    //method to store account info along with Map of meeting ids in Firebase Real Database
    private void addAccountToFirebaseDatabase(final AlertDialog dialog){
        String uid = mAuth.getUid();
        String email = mAuth.getCurrentUser().getEmail();
        databaseReference.child("users").child(uid).setValue(new User(uid, email))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        dialog.cancel();
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Account created successfully", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(CreateAccountActivity.this, MainActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed to add user in database\n(Error: " + task.getException().getMessage() + ")", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
