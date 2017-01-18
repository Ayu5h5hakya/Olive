package com.example.styledmap.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ayush on 1/18/17.
 */

public class ClassificationListingResponse {

    @SerializedName("status")
    public String status;
    @SerializedName("data")
    public Data data;

    public static class Category {
        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public String name;
        @SerializedName("brand_classification")
        public List<String> brandClassification;
    }

    public static class Data {
        @SerializedName("category")
        public List<Category> category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
