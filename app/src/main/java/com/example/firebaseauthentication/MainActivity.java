package com.example.firebaseauthentication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText email, pass;
    Button btnIn, btnOut, newAcc, dBase, dBaseRead, image;
    TextView textView;

    private FirebaseAuth mAuth;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        btnIn = findViewById(R.id.signIn);
        btnOut = findViewById(R.id.signOut);
        newAcc = findViewById(R.id.newAccount);
        dBase = findViewById(R.id.dbase);
        dBaseRead = findViewById(R.id.dbase2);
        textView = findViewById(R.id.user);
        image = findViewById(R.id.img);

        btnIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em = email.getText().toString();
                String ps = pass.getText().toString();
                signIn(em, ps);

                email.setText("");
                pass.setText("");
            }
        });

        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = mAuth.getCurrentUser();
                if(user != null){
                    Toast.makeText(getApplicationContext(), user.getEmail() + " signing out",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "No user currently sign In.",
                            Toast.LENGTH_SHORT).show();
                }
                mAuth.signOut();
                updateUI(user);

                email.setText("");
                pass.setText("");
            }
        });

        newAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em = email.getText().toString();
                String ps = pass.getText().toString();
                createNewAccount(em, ps);

                email.setText("");
                pass.setText("");
            }
        });

        dBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = mAuth.getCurrentUser();
                if(user != null){
                    Intent intent = new Intent(MainActivity.this, dataBase.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Error! Sign in first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dBaseRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = mAuth.getCurrentUser();
                if(user != null){
                    Intent intent = new Intent(MainActivity.this, dataBaseRead.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Error! Sign in first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = mAuth.getCurrentUser();
                if(user != null){
                    Intent intent = new Intent(MainActivity.this, ImageUpload.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Error! Sign in first", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void createNewAccount(String em, String ps) {
        mAuth.createUserWithEmailAndPassword(em, ps)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "Account as " + user.getEmail()
                                            + " created" , Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "Successfully signed in as " +
                                   user.getEmail(), Toast.LENGTH_SHORT ).show();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if(user != null){
            String name = user.getEmail();
            textView.setText(name);
            textView.setVisibility(View.VISIBLE);
            email.setFocusable(true);
        }
        else{
            textView.setText("");
            textView.setVisibility(View.INVISIBLE);
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        mAuth.signOut();
//        textView.setText("");
//        textView.setVisibility(View.INVISIBLE);
//    }

    @Override
    public void onStart() {
        super.onStart();
    }
}