package com.venky.assignment.network;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.venky.assignment.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Venky on 09-Oct-19.
 */

public class ApiClient {

    public static String HOSTNAME1="https://newsapi.org/v2/";

    public static RestService getInterface() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
        RestService service = new Retrofit.Builder()
                .baseUrl(HOSTNAME1)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build().create(RestService.class);
        return service;
    }

    public static void AlertDialogNoNetwork(final Context context) {
        if (context != null) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
            builder1.setTitle("Error");
            builder1.setMessage("NO internet connection. Please check your connection and try again");
            builder1.setCancelable(false);
            builder1.setIcon(R.drawable.nonetwork);

            builder1.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                        }
                    });


            final AlertDialog alert11 = builder1.create();
            alert11.show();
            alert11.getButton(alert11.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(android.R.color.black));
            alert11.getButton(alert11.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(NetworkCheck.isInternetAvailable(context))
                    {
                        alert11.dismiss();
                    }
                    else {

                    }
                }
            });

        }

    }
}
