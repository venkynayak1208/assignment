package com.venky.assignment.network;


import com.venky.assignment.model.ListResponse;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Venky on 09-Oct-19.
 */

public interface RestService {

    @GET("v2/list?page=2&limit=20")
    Observable<ArrayList<ListResponse>> getList();
}
