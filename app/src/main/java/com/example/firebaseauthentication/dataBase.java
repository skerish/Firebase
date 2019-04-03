package com.example.firebaseauthentication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class dataBase extends AppCompatActivity {

    EditText data1, data2, data3;
    Button button;
    TextView textView;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    private static final String TAG = "dataBase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base);

        data1 = findViewById(R.id.data1);
        data2 = findViewById(R.id.data2);
        data3 = findViewById(R.id.data3);
        button = findViewById(R.id.upload);
        textView = findViewById(R.id.text);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        String data = user.getEmail();
        textView.setText(data);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String d1 = data1.getText().toString();
                String d2 = data2.getText().toString();
                String d3 = data3.getText().toString();
                if(!d1.equals("") && !d2.equals("") && !d3.equals("")){
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    String key = user.getUid();
                    databaseReference.child("users").child(key).child("Name").setValue(d1);
                    databaseReference.child("users").child(key).child("E-mail").setValue(d2);
                    databaseReference.child("users").child(key).child("Phone no").setValue(d3);
                    data1.setText("");
                    data2.setText("");
                    data3.setText("");
                    Toast.makeText(getApplicationContext(), "Uploaded!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
