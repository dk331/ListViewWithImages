package com.dhananjay.listviewwithimages.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Response {

    @SerializedName("title")
    private String title;
    @SerializedName("rows")
    private ArrayList<Row> rows = null;

    public String getTitle() {
        return title;
    }

    public ArrayList<Row> getRows() {
        return rows;
    }

}
