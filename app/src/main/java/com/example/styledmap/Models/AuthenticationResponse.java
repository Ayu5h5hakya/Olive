package com.example.styledmap.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ayush on 1/18/17.
 */

public class AuthenticationResponse {

    @SerializedName("status")
    public String status;
    @SerializedName("data")
    public Data data;

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

    public static class Data {
        @SerializedName("token")
        public String token;
        @SerializedName("created_on")
        public String createdOn;
        @SerializedName("expire_on")
        public String expireOn;
    }
}
