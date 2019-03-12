package com.stubborn.fileuploadingcef;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ImagesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageAdapter mAdp;
    private DatabaseReference mdatabaseref;
    private List<Upload> mupload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        recyclerView = (RecyclerView) findViewById(R.id.myRecycler);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));


        mdatabaseref = FirebaseDatabase.getInstance().getReference().child("uploads");
        mdatabaseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mupload = new ArrayList<Upload>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Upload p = dataSnapshot1.getValue(Upload.class);
                    mupload.add(p);
                }
                mAdp = new ImageAdapter(ImagesActivity.this, (ArrayList<Upload>) mupload);
                recyclerView.setAdapter(mAdp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ImagesActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
