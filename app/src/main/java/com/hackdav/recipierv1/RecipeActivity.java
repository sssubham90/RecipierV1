package com.hackdav.recipierv1;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import java.util.Arrays;


public class RecipeActivity extends Activity {

    private String querry;
    private String[] Name={"Loading"};
    private String[] Url={"Loading"};
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        int[] Code = getIntent().getIntArrayExtra("Code");
        Arrays.sort(Code);
        querry = Arrays.toString(Code).replace("[", "").replace("]", "");
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CustomAdapter(Name,Url,querry);
        mRecyclerView.setAdapter(mAdapter);
    }
}
