package com.example.styledmap;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.styledmap.Utils.Constant;
import com.example.styledmap.Utils.RealmEngine;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by ayush on 12/30/16.
 */
@RuntimePermissions
public class LocationphotoActivity extends AppCompatActivity
                                   implements AppBarLayout.OnOffsetChangedListener{

    ImageView imageView;
    private int CAMERA_REQUEST = 800;
    File mediaStorageDirectory,photoFile;
    EditText location_description;
    AppBarLayout appBarLayout;
    private int mMaxScrollSize;
    FloatingActionButton fab;
    boolean isHidden;
    RealmEngine realmEngine = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locationphoto);
        imageView = (ImageView) findViewById(R.id.id_photo);
        location_description = (EditText) findViewById(R.id.location_description);
        appBarLayout = (AppBarLayout) findViewById(R.id.id_appbar);
        fab = (FloatingActionButton) findViewById(R.id.id_fab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        realmEngine = RealmEngine.getRealmInstance(this);

        final int event_ID = getIntent().getIntExtra(Constant.EVENT_ID_KEY,-1);
        setSupportActionBar(toolbar);
        toolbar.setPadding(0,getStatusbarheight(),0,0);
        LocationphotoActivityPermissionsDispatcher.takeLocationPhotoWithCheck(this);

        appBarLayout.addOnOffsetChangedListener(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realmEngine.addPhoto(event_ID,photoFile.getAbsolutePath());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK)
        {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int wwidth = metrics.widthPixels;
            imageView.setImageBitmap(decodeSampledBitmapFromResource(photoFile.getAbsolutePath(),wwidth,500));
        }

    }

    public Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }

    private File getOutputMediaFile() {

        // Create storage directory if id does not exist
        if (!mediaStorageDirectory.exists()) {
            mediaStorageDirectory.mkdir();
        }

        // Media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        photoFile = new File(mediaStorageDirectory.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");

        return photoFile;
    }

    @NeedsPermission({Manifest.permission.CAMERA,
                      Manifest.permission.WRITE_EXTERNAL_STORAGE,
                      Manifest.permission.READ_EXTERNAL_STORAGE})
    void takeLocationPhoto()
    {
        mediaStorageDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Olive pictures");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,getOutputMediaFileUri());
        startActivityForResult(intent,CAMERA_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LocationphotoActivityPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeight)
    {
        final int imageWidth = options.outWidth;
        final int imageHeight = options.outHeight;
        int inSampleSize = 1;
        if (imageHeight > reqHeight || imageWidth > reqWidth)
        {
            final int halfHeight = imageHeight/2;
            final int halfWidth = imageWidth/2;
            while ((halfHeight/inSampleSize) >=reqHeight && (halfWidth/inSampleSize) >=reqWidth) inSampleSize*=2;
        }
        return inSampleSize;
    }

    public Bitmap decodeSampledBitmapFromResource(String imagePath,int reqHeight,int reqWidth)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath,options);
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imagePath,options);
    }

    private int getStatusbarheight()
    {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height","dimen","android");
        if(resourceId > 0) result = getResources().getDimensionPixelSize(resourceId);
        return result;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

        if (mMaxScrollSize == 0) mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int currentScrollPercentage = Math.abs(verticalOffset)*100/mMaxScrollSize;

        if (currentScrollPercentage>=20)
        {
            if (!isHidden){
                isHidden = true;
                ViewCompat.animate(fab).scaleX(0).scaleY(0).start();
            }
        }

        if (currentScrollPercentage<20)
        {
            if (isHidden){
                isHidden = false;
                ViewCompat.animate(fab).scaleX(1).scaleY(1).start();
            }
        }
    }
}
