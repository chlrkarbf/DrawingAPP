package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private final int PERMISSIONS_REQUEST_RESULT = 100;

    MyView myView;

    LinearLayout llFingerPainting;
    TextView tvFingerPainting;
    Button btSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvFingerPainting = (TextView)findViewById(R.id.tv_finger_painting);
        btSave = (Button)findViewById(R.id.bt_submit);
        btSave.setOnClickListener(onClickListener);

        llFingerPainting = (LinearLayout)findViewById(R.id.ll_finger_painting);
        myView = new MyView(MainActivity.this);
        llFingerPainting.addView(myView);

        requestPermissionCamera();


    }
    View.OnClickListener onClickListener = (view) -> {
        switch (view.getId()){
            case R.id.bt_submit:
                Bitmap bitmap = myView.getCanvasBitmap();
                if(isExternalStorageWritable()){
                    saveImage(bitmap);
            }
                break;
        }
    };

    public boolean isExternalStorageWritable(){
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)){
            return true;
        }
        return false;
    }

    private void saveImage(Bitmap finalBitmap){
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/finger_painting");
        myDir.mkdirs();

        String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss").format(new Date());
        String fname = "finger_"+ timeStamp + ".jpg";

        File file = new File(myDir,fname);
        if(file.exists()) file.delete();

        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
            out.flush();
            out.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean requestPermissionCamera(){
        int sdkVersion = Build.VERSION.SDK_INT;

        if(sdkVersion >= Build.VERSION_CODES.M){

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.
                        READ_EXTERNAL_STORAGE)||ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            }else{
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.
                                READ_EXTERNAL_STORAGE},PERMISSIONS_REQUEST_RESULT);
                }
                }else{

                }
            }else {

            }
            return true;
        }
        @Override
         public void onRequestPermissionsResult(int requestCode,String permissions[],int[] grantResults) {

            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (PERMISSIONS_REQUEST_RESULT == requestCode) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("permission", "agree");

                } else {
                    Log.i("permission", "disagree");
                }
                return;
            }
        }
    }

