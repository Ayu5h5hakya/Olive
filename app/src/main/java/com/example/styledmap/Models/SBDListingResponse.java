package com.example.styledmap.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ayush on 1/18/17.
 */
//TODO ident vs id
public class SBDListingResponse {

    @SerializedName("status")
    public String status;
    @SerializedName("data")
    public Data data;

    public static class Skus {
        @SerializedName("id_")
        public int id;
        @SerializedName("id")
        public String ident;
        @SerializedName("name")
        public String name;
        @SerializedName("outlet_channel")
        public String outletChannel;
        @SerializedName("outlet_category")
        public String outletCategory;
    }

    public static class Data {
        @SerializedName("skus")
        public List<Skus> skus;
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
