package com.hackdav.recipierv1;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{

    private String[] name;
    private String[] url;
    private final String querry;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView Name;
        TextView Url;

        ViewHolder(TextView v,TextView u) {
            super(v);
            Name = v;
            Url=u;
        }
    }

    CustomAdapter(String[] Name,String[] Url,String Querry) {
        name = Name;
        url = Url;
        querry=Querry;
    }

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.name, parent, false);
        TextView u = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.url, parent, false);
        return new ViewHolder(v,u);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.Name.setText(name[position]);
        holder.Url.setText(url[position]);

    }
    @Override
    public int getItemCount() {
        return name.length;
    }
}