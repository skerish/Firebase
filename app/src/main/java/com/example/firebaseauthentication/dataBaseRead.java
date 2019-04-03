package com.example.firebaseauthentication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class dataBaseRead extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference reference;

    ListView listView;

    private static final String TAG = "dataBaseRead";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base_read);

        listView = findViewById(R.id.list);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        FirebaseUser user = mAuth.getCurrentUser();
        String uID = user.getUid();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                printing(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void printing(DataSnapshot dataSnapshot) {

        FirebaseUser user = mAuth.getCurrentUser();
        String uID = user.getUid();

        for (DataSnapshot ds : dataSnapshot.getChildren()) {         //DataSnapshot will take snapshot of first directory
                                                                    // which is "users" in this case.
            DataBaseReadHelper obj = new DataBaseReadHelper();
//            obj.setName(ds.child(uID).getValue(DataBaseReadHelper.class).getName());
//            obj.setEmail(ds.child(uID).getValue(DataBaseReadHelper.class).getEmail());
//            obj.setPhone(ds.child(uID).getValue(DataBaseReadHelper.class).getPhone());

            obj.setName(ds.child(uID).child("Name").getValue(String.class));
            obj.setEmail(ds.child(uID).child("E-mail").getValue(String.class));
            obj.setPhone(ds.child(uID).child("Phone no").getValue(String.class));

            ArrayList<String> arrayList = new ArrayList<>();
            arrayList.add(obj.getName());
            arrayList.add(obj.getEmail());
            arrayList.add(obj.getPhone());
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
            listView.setAdapter(adapter);

        }
    }

}
