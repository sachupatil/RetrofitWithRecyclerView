package com.example.midigiwordapps;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.core.app.ActivityCompat.*;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyviewHolder> {

    Context context;
    List<Model> list;
    Dialog myDialog;

    public RecyclerAdapter(Context context, List<Model> list) {
        this.context = context;
        this.list = list;
    }

    public void setList(List<Model> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerAdapter.MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.MyviewHolder holder, int position) {
       holder.tvnamem.setText(list.get(position).getName().toString());
        holder.tvphone.setText(list.get(position).getPhone().toString());
        Picasso.with(context).load(list.get(position).getImage()).into(holder.image);
        Log.d("Imges :",list.get(position).getImage());

        holder.tvphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" +list.get(holder.getAdapterPosition()).getPhone()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

//                        Intent intent = new Intent(context,CallingActivity.class);
//                        intent.putExtra("phonecall", list.get(holder.getAdapterPosition()).getPhone());
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(intent);
                    }
                });
            }


    @Override
    public int getItemCount() {
        if(list != null){
            return list.size();
        }
        return 0;

    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView tvnamem,tvphone;
        ImageView image;

        public MyviewHolder(View itemView) {
            super(itemView);
            tvnamem = (TextView)itemView.findViewById(R.id.name);
            image = (ImageView)itemView.findViewById(R.id.imageView);
            tvphone=(TextView)itemView.findViewById(R.id.phone);
        }
    }
}
