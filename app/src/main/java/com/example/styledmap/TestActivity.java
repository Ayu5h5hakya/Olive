package com.example.styledmap;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.styledmap.Models.AuthenticationResponse;
import com.example.styledmap.Models.CategoryListingResponse;
import com.example.styledmap.Models.ClassificationListingResponse;
import com.example.styledmap.Models.OutletDetailResponse;
import com.example.styledmap.Models.RMAP;
import com.example.styledmap.Models.SBDListingResponse;
import com.example.styledmap.Models.SKUListingResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func4;
import rx.schedulers.Schedulers;

/**
 * Created by ayush on 1/18/17.
 */

public class TestActivity extends AppCompatActivity {

    Button startTesting;
    RMAPinterface rmaPinterface;
    String[] dataArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        startTesting = (Button) findViewById(R.id.id_button);
        dataArray = new String[]{"4840388","4840392","4840408","4840409","24683728","24683733","24684286","58404678","58425711",
                              58472955,58472957,58473054,58752656,80913255,80913261,80913262,81465111,85650621,
                              85650623,111823085,111823094,111823096,111984901,112116416,112117065,112117066,
                              112117476,112551782,112551783,112551784,112551791,112551801,112551806,112655395,
                              120353047,120353053,120353057,133051043,133051053,136452122,136452123,136452124,
                              136452125,136452126,136452127,136525997,136618558,142107922,142108309,162263134,
                              162263136,162263142,169078007,169188252,169188253,169188258,169188259,169188260,
                              169188262,169188263,169188265,169188266,169188267,169188268,169344974,169429520,
                              169429524,169429532,171203195,171203198,171203199,180208175,180208508,180208969,
                              180208970,180209201,180209202,180209324,180209325,180209915,180210958,180210961,
                              180211672,180211673,180211674,180211675,180214606,180214608,180214979,180214981,
                              180216842,180216845,184128905,184128910,184131272,184131273,184131275,184131276,
                              184131277,184131278,184131279,184131280,184132821,184132822,184132823,184132824,
                              184132825,184133747,184133751,184133753,184133756,185882291,186151585,196249780,
                              199009940,199009943,199430771,200251537,212392696,212392697,212393878,217543158,
                              218439406,218439407,218439408,218441370,218576369,219001690,220294113,253307745,
                              254097239,301431441,302412885,324729914,332624227,332655800,336808072,342106619,
                              342106620,342106621,342106622,351924481,351924485,388989993,388989996,430749753,
                              431574605,435142491,448304074,448304075,448304076,448305811,448305812,455096210};

        HttpLoggingInterceptor httplogger = new HttpLoggingInterceptor();
        httplogger.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(httplogger).build();

        Retrofit retrofit = new Retrofit.Builder()
                                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .baseUrl(RMAPinterface.BASE_URL)
                                        .client(client)
                                        .build();
        rmaPinterface = retrofit.create(RMAPinterface.class);
        startTesting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rmaPinterface.getAuthenticationToken("samarpan@mail.com","123456").subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<AuthenticationResponse>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("witcher", "onError: "+e.getMessage());
                            }

                            @Override
                            public void onNext(AuthenticationResponse authenticationResponse) {
                                startZippedCalls(authenticationResponse.getData().token);
                            }
                        });
            }
        });
    }

    private void startZippedCalls(String token){

        JSONObject dataObject = null;

        try {
            dataObject = new JSONObject();
            dataObject.put("way_id", Arrays.toString(dataArray));
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        Observable<CategoryListingResponse> categorylistingObservable = rmaPinterface.getCategoryListing(token)
//                                                                                     .subscribeOn(Schedulers.newThread())
//                                                                                     .observeOn(AndroidSchedulers.mainThread());
//
//        Observable<SBDListingResponse> sbdlistingObservable = rmaPinterface.getSBDlisting(token)
//                                                                           .subscribeOn(Schedulers.newThread())
//                                                                           .observeOn(AndroidSchedulers.mainThread());
//
//        Observable<SKUListingResponse> skulistingObservable = rmaPinterface.getSKUListing(token)
//                                                                           .subscribeOn(Schedulers.newThread())
//                                                                           .observeOn(AndroidSchedulers.mainThread());
//
//        Observable<ClassificationListingResponse> classificationlistingObservable = rmaPinterface.getClassificationListing(token)
//                                                                                                 .subscribeOn(Schedulers.newThread())
//                                                                                                 .observeOn(AndroidSchedulers.mainThread());

            rmaPinterface.getstreetoutletlisting(token, dataObject.toString())
                       .subscribeOn(Schedulers.newThread())
                       .observeOn(AndroidSchedulers.mainThread())
                       .subscribe(new Action1<OutletDetailResponse>() {
                                      @Override
                                      public void call(OutletDetailResponse outletDetailResponse) {
                                          Log.d("witcher", "call: " + outletDetailResponse);
                                      }
                                  },
                               new Action1<Throwable>() {
                                   @Override
                                   public void call(Throwable throwable) {
                                       Log.d("witcher", "call: "+throwable.getMessage());
                                   }
                               });


//        Observable<RMAP> RMAPzipper = Observable.zip(categorylistingObservable,
//                sbdlistingObservable,
//                skulistingObservable,
//                classificationlistingObservable,
//                new Func4<CategoryListingResponse, SBDListingResponse, SKUListingResponse, ClassificationListingResponse, RMAP>() {
//                    @Override
//                    public RMAP call(CategoryListingResponse categoryListingResponse, SBDListingResponse sbdListingResponse, SKUListingResponse skuListingResponse, ClassificationListingResponse classificationListingResponse) {
//                        return new RMAP(categoryListingResponse,
//                                        sbdListingResponse,
//                                        skuListingResponse,
//                                        classificationListingResponse);
//                    }
//                });
//
//        RMAPzipper.subscribe(new Action1<RMAP>() {
//            @Override
//            public void call(RMAP rmap) {
//                Log.d("witcher", "call: "+rmap);
//            }
//        });
    }
}
