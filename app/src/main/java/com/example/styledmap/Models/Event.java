package com.example.styledmap.Models;

import com.google.android.gms.maps.model.LatLng;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ayush on 12/28/16.
 */

public class Event extends RealmObject{

    @PrimaryKey
    private int id;

    private double position_lat,position_lang;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public double getPosition_lat() {
        return position_lat;
    }

    public void setPosition_lat(double position_lat) {
        this.position_lat = position_lat;
    }

    public double getPosition_lang() {
        return position_lang;
    }

    public void setPosition_lang(double position_lang) {
        this.position_lang = position_lang;
    }

}
