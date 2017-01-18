package com.example.styledmap;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.styledmap.Models.AuthenticationResponse;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by ayush on 1/18/17.
 */

public class TestActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_test);

        Retrofit retrofit = new Retrofit.Builder()
                                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .baseUrl(RMAPinterface.BASE_URL)
                                        .build();
        RMAPinterface rmaPinterface = retrofit.create(RMAPinterface.class);
        rmaPinterface.getAuthenticationToken().subscribeOn(Schedulers.newThread())
                                              .observeOn(AndroidSchedulers.mainThread())
                                              .subscribe(new Action1<AuthenticationResponse>() {
                                                  @Override
                                                  public void call(AuthenticationResponse authenticationResponse) {
                                                      String token = authenticationResponse.getData().token;
                                                      Log.d("witcher", "call: "+token);
                                                  }
                                              });
    }
}
