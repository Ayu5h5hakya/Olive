package com.example.styledmap.Utils;

import android.content.Context;

import com.example.styledmap.Models.Event;
import com.example.styledmap.Models.Photo;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by ayush on 1/23/17.
 */

public class RealmEngine {

    private static RealmEngine instance = null;
    private Realm realm;
    private Context context;

    public static RealmEngine getRealmInstance(Context context){
        if(instance==null){
            instance = new RealmEngine(context);
        }
        return instance;
    }

    private RealmEngine(Context context)
    {
        this.context = context;
        realm = Realm.getInstance(
                new RealmConfiguration.Builder(context)
                        .name("myOtherRealm.realm")
                        .build());
    }

    public void addEvent(LatLng elem)
    {
        realm.beginTransaction();
        Event event = realm.createObject(Event.class);
        event.setId(next_key());
        event.setPosition_lat(elem.latitude);
        event.setPosition_lang(elem.longitude);
        realm.commitTransaction();
    }

    public void addPhoto(int event_id,String photopath){
        realm.beginTransaction();
        Photo photo = realm.createObject(Photo.class);
        photo.setEvent_id(event_id);
        photo.setPhoto_url(photopath);
        realm.commitTransaction();
    }

    private int next_key(){
        return realm.where(Event.class).max("id").intValue()+1;
    }

    public ArrayList<LatLng> getAllMarkers(){
        ArrayList<LatLng> temp = new ArrayList<>();
        RealmResults<Event> results = realm.where(Event.class).findAll();
        for (Event event : results) temp.add(new LatLng(event.getPosition_lat(),event.getPosition_lang()));
        return temp;
    }

    public int containsMarker(Marker marker)
    {
        RealmResults<Event> query = realm.where(Event.class)
                                          .contains("position_lat",marker.getPosition().latitude+"")
                                          .contains("position_lang",marker.getPosition().longitude+"")
                                          .findAll();
        if (!query.isEmpty()) return query.get(0).getId();
        return -1;
    }
}
