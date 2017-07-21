package com.hackdav.recipierv1;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{

    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> url = new ArrayList<>();

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView Name;
        TextView Url;

        ViewHolder(TextView v,TextView u) {
            super(v);
            Name = v;
            Url=u;
            Name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    CustomAdapter(DatabaseReference myRef) {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    name.add(postSnapshot.child("NAME").getValue().toString());
                    url.add(postSnapshot.child("URL").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Failed to read value.", error.toException());
            }
        });}

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.name, parent, false);
        TextView u = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.url, parent, false);
        return new ViewHolder(v,u);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.Name.setText(name.get(position));
        holder.Url.setText(url.get(position));

    }
    @Override
    public int getItemCount() {
        return name.size();
    }
}