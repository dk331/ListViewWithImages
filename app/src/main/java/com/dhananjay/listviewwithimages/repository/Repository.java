package com.dhananjay.listviewwithimages.repository;

import com.dhananjay.listviewwithimages.api.ApiCallInterface;
import com.dhananjay.listviewwithimages.models.Response;

import io.reactivex.Observable;

public class Repository {

    private ApiCallInterface apiCallInterface;

    public Repository(ApiCallInterface apiCallInterface) {
        this.apiCallInterface = apiCallInterface;
    }

    /*
     * method to call getResponse api
     * */
    public Observable<Response> getResponse() {
        return apiCallInterface.getResponse();
    }
}
