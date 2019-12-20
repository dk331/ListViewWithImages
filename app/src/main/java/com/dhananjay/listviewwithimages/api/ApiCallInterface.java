package com.dhananjay.listviewwithimages.api;

import com.dhananjay.listviewwithimages.models.Response;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiCallInterface {

    @GET("facts.json")
    Observable<Response> getResponse();
}
