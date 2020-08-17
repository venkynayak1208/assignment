package com.venky.assignment.network;


import com.venky.assignment.model.ListResponse;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Venky on 17-Aug-20.
 */

public interface RestService {

    //calling api to get result
    @GET("v2/list?page=2&limit=20")
    Observable<ArrayList<ListResponse>> getList();
}
