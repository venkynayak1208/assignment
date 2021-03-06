package com.venky.assignment.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.venky.assignment.R;
import com.venky.assignment.model.ListResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    Context context;
    ArrayList<ListResponse> favouriteResponses = new ArrayList<>();
    AlertDialog.Builder alertDialog;
    public ListAdapter(Context favouriteActivity, ArrayList<ListResponse> favouriteResponseArrayList) {
        this.context = favouriteActivity;
        this.favouriteResponses = favouriteResponseArrayList;
    }

    @NonNull
    @Override
    public ListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_item_row, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final ListResponse favouriteRespons = favouriteResponses.get(position);

        if(favouriteRespons.getDownloadUrl()==null||favouriteRespons.getDownloadUrl().equalsIgnoreCase(""))
        {

        }else{
            Glide.with(context).load(favouriteRespons.getDownloadUrl().toString()).into(holder.item_image);

        }

        holder.tv_title.setText(favouriteRespons.getAuthor());
        holder.tv_desc.setText("Url : "+favouriteRespons.getUrl()+"\n"+"Hieght :"+favouriteRespons.getHeight()+"\n"+"Width : "+favouriteRespons.getWidth()+"\n");

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 alertDialog = new AlertDialog.Builder((context));

                alertDialog.setTitle("Information");
                alertDialog.setMessage("\n\n"+"Author : "+favouriteRespons.getAuthor()+"\n"+"Hieght :"+favouriteRespons.getHeight()+"\n"+"Width : "+favouriteRespons.getWidth()+"\n"+"Url : "+favouriteRespons.getUrl());
                alertDialog.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }
                );


            alertDialog.show();
        }
        });


    }

    @Override
    public int getItemCount() {
        return favouriteResponses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView item_image;
        TextView tv_desc, tv_title;
        CardView card;

        public MyViewHolder(View itemView) {
            super(itemView);

            item_image = itemView.findViewById(R.id.news_thumbnail);
            tv_title = itemView.findViewById(R.id.news_title);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            card=itemView.findViewById(R.id.card);
        }
    }


}