package com.example.styledmap.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ayush on 1/18/17.
 */

public class OutletDetailResponse {
    @SerializedName("status")
    public String status;
    @SerializedName("data")
    public Data data;

    public static class Brand {
        @SerializedName("id")
        public int id;
        @SerializedName("skus")
        public List<String> skus;
    }

    public static class Inventory {
        @SerializedName("brand")
        public List<Brand> brand;
    }

    public static class Retailoutlets {
        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public String name;
        @SerializedName("owner_mobile_number")
        public String ownerMobileNumber;
        @SerializedName("store_phone")
        public String storePhone;
        @SerializedName("store_owner")
        public String storeOwner;
        @SerializedName("store_size")
        public String storeSize;
        @SerializedName("monthly_avg_business")
        public String monthlyAvgBusiness;
        @SerializedName("longitude")
        public String longitude;
        @SerializedName("pan_number")
        public String panNumber;
        @SerializedName("is_verified")
        public boolean isVerified;
        @SerializedName("verified")
        public boolean verified;
        @SerializedName("category")
        public String category;
        @SerializedName("category_id")
        public String categoryId;
        @SerializedName("latitude")
        public String latitude;
        @SerializedName("inventory")
        public Inventory inventory;
    }

    public static class Street {
        @SerializedName("id")
        public int id;
        @SerializedName("name")
        public String name;
        @SerializedName("way_id")
        public int wayId;
        @SerializedName("retailoutlets")
        public List<Retailoutlets> retailoutlets;
    }

    public static class Data {
        @SerializedName("street")
        public List<Street> street;
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
