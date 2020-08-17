package com.venky.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.venky.assignment.adapter.ListAdapter;
import com.venky.assignment.model.ListResponse;
import com.venky.assignment.network.ApiClient;
import com.venky.assignment.network.NetworkCheck;
import com.venky.assignment.network.RestService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.nodatatext)
    TextView nodatatext;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipe_refresh_layout;
    ListAdapter listAdapter;
    @BindView(R.id.rv_inboxlist)
    RecyclerView rv_inboxlist;
    CompositeDisposable compositeDisposable;
    ArrayList<ListResponse> article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        compositeDisposable = new CompositeDisposable();
        swipe_refresh_layout.setOnRefreshListener(this);
        swipe_refresh_layout.post(new Runnable() {
            @Override
            public void run() {
                swipe_refresh_layout.setRefreshing(true);
                if (NetworkCheck.isInternetAvailable(MainActivity.this)) {
                    //calling method to get response from server
                    inbox();
                } else {
                    ApiClient.AlertDialogNoNetwork(MainActivity.this);
                }
            }
        });
    }

    private void inbox() {
        RestService requestInterface = ApiClient.getInterface();
        compositeDisposable.add(requestInterface.getList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(handleResponse -> {
                    swipe_refresh_layout.setRefreshing(false);
                    article = new ArrayList<ListResponse>();
                    if (!handleResponse.isEmpty() && handleResponse.size() > 0) {
                        rv_inboxlist.setVisibility(View.VISIBLE);
                        swipe_refresh_layout.setRefreshing(false);
                        article.addAll(handleResponse);
                        listAdapter = new ListAdapter(MainActivity.this, article);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                        rv_inboxlist.setLayoutManager(linearLayoutManager);
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        rv_inboxlist.setAdapter(listAdapter);
                        listAdapter.notifyDataSetChanged();
                        //   Toast.makeText(this, "No more data Available", Toast.LENGTH_SHORT).show();
                    } else {
                        if (article.size() == 0) {
                            rv_inboxlist.setVisibility(View.GONE);
                            nodatatext.setVisibility(View.VISIBLE);
                            swipe_refresh_layout.setRefreshing(false);
                        }
                    }
                    // inboxAdapter.notifyDataSetChanged();
                }, handleError -> {
                    swipe_refresh_layout.setRefreshing(false);
                }));
    }


    @Override
    public void onRefresh() {
        if (NetworkCheck.isInternetAvailable(MainActivity.this)) {
            swipe_refresh_layout.setRefreshing(false);
            inbox();

        } else {
            ApiClient.AlertDialogNoNetwork(MainActivity.this);
        }
    }
}