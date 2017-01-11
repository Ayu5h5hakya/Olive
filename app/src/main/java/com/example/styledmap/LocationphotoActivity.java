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
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by ayush on 12/30/16.
 */
@RuntimePermissions
public class LocationphotoActivity extends AppCompatActivity {

    ImageView imageView;
    private int CAMERA_REQUEST = 800;
    File mediaStorageDirectory,photoFile;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locationphoto);
        imageView = (ImageView) findViewById(R.id.id_photo);
        mediaStorageDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Olive pictures");
        LocationphotoActivityPermissionsDispatcher.takeLocationPhotoWithCheck(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK)
        {
            LocationphotoActivityPermissionsDispatcher.accessFileSystemWithCheck(this);
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

    @NeedsPermission({Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE})
    public void takeStorePhoto(){
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,getOutputMediaFileUri());
        startActivityForResult(intent,CAMERA_REQUEST);
        imageView.setImageBitmap(BitmapFactory.decodeFile(photoFile.getAbsolutePath()));
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LocationphotoActivityPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
    }
}
