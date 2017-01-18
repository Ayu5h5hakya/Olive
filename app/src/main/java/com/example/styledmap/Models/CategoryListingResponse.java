package com.example.styledmap.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ayush on 1/18/17.
 */

public class CategoryListingResponse {


    @SerializedName("status")
    public String status;
    @SerializedName("data")
    public Data data;

    public static class Category {
        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public String name;
    }

    public static class Channel {
        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public String name;
        @SerializedName("category")
        public List<Category> category;
    }

    public static class Data {
        @SerializedName("channel")
        public List<Channel> channel;
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
