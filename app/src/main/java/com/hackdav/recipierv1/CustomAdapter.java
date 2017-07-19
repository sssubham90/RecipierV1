package com.hackdav.recipierv1;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

class CustomAdapter extends ArrayAdapter<String> {


    private final Activity context;
    private final String querry;

    CustomAdapter(final Activity context, String querry) {
        super(context, R.layout.rowlayout);

        this.context = context;
        this.querry = querry;

    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final View rowView = inflater.inflate(R.layout.rowlayout, null, true);
        final TextView txtName = (TextView) rowView.findViewById(R.id.txtName);
        final TextView txtUrl = (TextView) rowView.findViewById(R.id.txtUrl);
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("https://recipier-f0da9.firebaseio.com/");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(querry).child(String.valueOf(position)).child("NAME").getValue() != null)
                        txtName.setText(dataSnapshot.child(querry).child(String.valueOf(position)).child("NAME").getValue().toString());
                    if(dataSnapshot.child(querry).child(String.valueOf(position)).child("URL").getValue() != null)
                        txtUrl.setText(dataSnapshot.child(querry).child(String.valueOf(position)).child("URL").getValue().toString());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Failed to read value.", databaseError.toException());
            }
        });
        return rowView;
    }
}