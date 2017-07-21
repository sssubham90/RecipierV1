package com.hackdav.recipierv1;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

public class RecipeActivity extends Activity {

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String querry;
        database = FirebaseDatabase.getInstance();
        RecyclerView mRecyclerView;
        RecyclerView.Adapter mAdapter;
        RecyclerView.LayoutManager mLayoutManager;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        int[] Code = getIntent().getIntArrayExtra("Code");
        Arrays.sort(Code);
        querry = Arrays.toString(Code).replace("[", "").replace("]", "");
        final DatabaseReference myRef = database.getReference("https://recipier-f0da9.firebaseio.com/"+querry);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CustomAdapter(myRef);
        mRecyclerView.setAdapter(mAdapter);
    }
}
