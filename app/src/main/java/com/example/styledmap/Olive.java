package com.example.styledmap;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by ayush on 12/28/16.
 */

public class Olive extends Application {

    Realm realm;
    @Override
    public void onCreate() {
        super.onCreate();


        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

        realm = Realm.getInstance(
                new RealmConfiguration.Builder(getApplicationContext())
                        .name("myOtherRealm.realm")
                        .build()
        );
    }

    Realm getRealmInstance() {return realm;}
}
