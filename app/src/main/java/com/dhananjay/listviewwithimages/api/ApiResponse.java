package com.dhananjay.listviewwithimages.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dhananjay.listviewwithimages.enums.Status;
import com.dhananjay.listviewwithimages.models.Response;

import java.util.Observable;

public class ApiResponse extends Observable {

    public final Status status;

    @Nullable
    public final Response data;

    @Nullable
    public final Throwable error;

    private ApiResponse(Status status, @Nullable Response data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static ApiResponse loading() {
        return new ApiResponse(Status.LOADING, null, null);
    }

    public static ApiResponse success(@NonNull Response data) {
        return new ApiResponse(Status.SUCCESS, data, null);
    }

    public static ApiResponse error(@NonNull Throwable error) {
        return new ApiResponse(Status.ERROR, null, error);
    }

}
