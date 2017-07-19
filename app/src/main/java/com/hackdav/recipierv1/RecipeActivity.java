package com.hackdav.recipierv1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.Arrays;


public class RecipeActivity extends Activity {

    String querry;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        int[] Code = getIntent().getIntArrayExtra("Code");
        Arrays.sort(Code);
        querry = Arrays.toString(Code).replace("[", "").replace("]", "");
        listView = (ListView) findViewById(R.id.list);
        CustomAdapter adapter =new CustomAdapter(this,querry);
        listView.setAdapter(adapter);
    }
}
