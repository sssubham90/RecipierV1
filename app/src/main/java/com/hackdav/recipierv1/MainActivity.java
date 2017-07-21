package com.hackdav.recipierv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private TextView Ingredients;
    private StringBuilder ingridients;
    private LinkedList<Integer> code=new LinkedList<>();
    private SparseArray<String> codeMap=new SparseArray<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button Add,Done,Clear;
        Ingredients =(TextView)findViewById(R.id.textView);
        Add=(Button)findViewById(R.id.button2);
        Done=(Button)findViewById(R.id.button1);
        Clear=(Button)findViewById(R.id.button3);
        ingridients=new StringBuilder(Ingredients.getText().toString());
        ingridients.append("Ingredient List: ");
        codeMap.put(10,"Potato");
        codeMap.put(11,"Brinjal");
        codeMap.put(12,"Tomato");
        codeMap.put(13,"Beans");
        codeMap.put(14,"Pumpkin");
        codeMap.put(15,"Cabbage");
        codeMap.put(16,"Okra");
        codeMap.put(17,"Carrot");
        codeMap.put(19,"Egg");
        codeMap.put(20,"Chicken");
        codeMap.put(21,"Prawn");
        codeMap.put(22,"Fish");
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Code[]=new int[code.size()];
                for(int i=0;i<code.size();i++)
                    Code[i]=code.get(i);
                final Intent GoLogin = new Intent(MainActivity.this, RecipeActivity.class);
                GoLogin.putExtra("Code", Code);
                startActivity(GoLogin);
                overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left);
            }
        });
        Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code.clear();
                ingridients.delete(0, ingridients.length());
                ingridients.append("Ingredient List: ");
                Ingredients.setText(ingridients);
                uncheckAll();
            }
        });
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ingredients.setText(ingridients);
            }
        });
    }

    public void uncheckAll(){
        CheckBox checkBox;
        checkBox=(CheckBox) findViewById(R.id.C10);
        checkBox.setChecked(false);
        checkBox=(CheckBox) findViewById(R.id.C11);
        checkBox.setChecked(false);
        checkBox=(CheckBox) findViewById(R.id.C12);
        checkBox.setChecked(false);
        checkBox=(CheckBox) findViewById(R.id.C13);
        checkBox.setChecked(false);
        checkBox=(CheckBox) findViewById(R.id.C14);
        checkBox.setChecked(false);
        checkBox=(CheckBox) findViewById(R.id.C15);
        checkBox.setChecked(false);
        checkBox=(CheckBox) findViewById(R.id.C16);
        checkBox.setChecked(false);
        checkBox=(CheckBox) findViewById(R.id.C17);
        checkBox.setChecked(false);
        checkBox=(CheckBox) findViewById(R.id.C19);
        checkBox.setChecked(false);
        checkBox=(CheckBox) findViewById(R.id.C20);
        checkBox.setChecked(false);
        checkBox=(CheckBox) findViewById(R.id.C21);
        checkBox.setChecked(false);
        checkBox=(CheckBox) findViewById(R.id.C22);
        checkBox.setChecked(false);
        ingridients.delete(0,ingridients.length());
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch(view.getId()) {
            case R.id.C10:
                if (checked){
                    code.add(10);
                    ingridients.append(codeMap.get(10)).append(" ");}
                break;
            case R.id.C11:
                if (checked){
                    code.add(11);
                    ingridients.append(codeMap.get(11)).append(" ");}
                break;
            case R.id.C12:
                if (checked){
                    code.add(12);
                    ingridients.append(codeMap.get(12)).append(" ");}
                break;
            case R.id.C13:
                if (checked){
                    code.add(13);
                    ingridients.append(codeMap.get(13)).append(" ");}
                break;
            case R.id.C14:
                if (checked){
                    code.add(14);
                    ingridients.append(codeMap.get(14)).append(" ");}
                break;
            case R.id.C15:
                if (checked){
                    code.add(15);
                    ingridients.append(codeMap.get(15)).append(" ");}
                break;
            case R.id.C16:
                if (checked){
                    code.add(16);
                    ingridients.append(codeMap.get(16)).append(" ");}
                break;
            case R.id.C17:
                if (checked){
                    code.add(17);
                    ingridients.append(codeMap.get(17)).append(" ");}
                break;
            case R.id.C19:
                if (checked){
                    code.add(19);
                    ingridients.append(codeMap.get(19)).append(" ");}
                break;
            case R.id.C20:
                if (checked){
                    code.add(20);
                    ingridients.append(codeMap.get(20)).append(" ");}
                break;
            case R.id.C21:
                if (checked){
                    code.add(21);
                    ingridients.append(codeMap.get(21)).append(" ");}
                break;
            case R.id.C22:
                if (checked){
                    code.add(22);
                    ingridients.append(codeMap.get(22)).append(" ");}
                break;
        }
    }
}
