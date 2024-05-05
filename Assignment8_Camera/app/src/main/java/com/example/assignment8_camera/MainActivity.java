package com.example.assignment8_camera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button b1;
    ImageView iv;
    String nmFile;
    private static final int kodekamera = 222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        // action bar
//        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);

        b1 = findViewById(R.id.button_capture);
        iv = findViewById(R.id.image_view);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File imagesFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Hasil Foto");
                Date d = new Date();
                CharSequence s = DateFormat.format("yyyyMMdd-hh-mm-ss", d.getTime());
                nmFile = imagesFolder + File.separator + s.toString() + ".jpg";

                if (!imagesFolder.exists()) {
                    boolean res = imagesFolder.mkdirs();

                    if (!res) {
                        Log.e("Folder", "Image folder can't be created");
                    }
                }

                File image = new File(nmFile);

                Uri uriSavedImage = FileProvider.getUriForFile(MainActivity.this, "com.example.camera.provider", image);
                it.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(it, kodekamera);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode){
                case (kodekamera):
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    File f = new File(nmFile);
                    Uri contentUri = Uri.fromFile(f);
                    mediaScanIntent.setData(contentUri);
                    sendBroadcast(mediaScanIntent);

                    Bitmap bm = BitmapFactory.decodeFile(nmFile);
                    iv.setImageBitmap(bm);
                    Toast.makeText(this, "Data Telah Terload ke ImageView" + nmFile, Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    private void prosesKamera(Intent datanya) {
        Bitmap bm;
        BitmapFactory.Options options;
        options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        bm = BitmapFactory.decodeFile(nmFile, options);
        iv.setImageBitmap(bm);
        Toast.makeText(this, "Data telah terload ke ImageView" + nmFile, Toast.LENGTH_SHORT).show();
    }
}