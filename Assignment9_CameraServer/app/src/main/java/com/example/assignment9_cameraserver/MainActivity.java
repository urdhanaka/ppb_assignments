package com.example.assignment9_cameraserver;

import androidx.appcompat.app.AppCompatActivity;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    Button captureImageButton;
    Button uploadImageButton;
    ImageView resultImageView;
    String fileAbsolutePath;
    Uri contentUri;
    Uri tmp;
    File image;
    private static final int kode = 222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        captureImageButton = findViewById(R.id.button_capture);
        resultImageView = findViewById(R.id.image_view);
        uploadImageButton = findViewById(R.id.button_upload);

        captureImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Hasil Foto");
                Date d = new Date();
                CharSequence s = DateFormat.format("yyyyMMdd-hh-mm-ss", d.getTime());
                fileAbsolutePath = folder + File.separator + s.toString() + ".png";

                if (!folder.exists()) {
                    boolean res = folder.mkdirs();

                    if (!res) {
                        Log.e("Folder", "Image folder can't be created");
                    }
                }

                try {
                    image = new File(fileAbsolutePath);
                } catch (Exception ignored) {
                }

                contentUri = FileProvider.getUriForFile(MainActivity.this, "com.example.camera.provider", image);

                i.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                startActivityForResult(i, kode);
            }
        });

        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (resultImageView.getDrawable() == null) {
                    Toast.makeText(MainActivity.this, "You haven't take any picture", Toast.LENGTH_SHORT).show();
                }

                uploadFile();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == kode) {
                Intent mediaIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                File f = new File(image.getPath());
                contentUri = Uri.fromFile(f);
                mediaIntent.setData(contentUri);
                Bitmap bm = BitmapFactory.decodeFile(image.getPath());
                resultImageView.setImageBitmap(bm);
            }
        }
    }

    private void uploadFile() {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/png"), image);
        MultipartBody.Part body = MultipartBody.Part.createFormData("picture", image.getName(), requestFile);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.webServerUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Api api = retrofit.create(Api.class);

        Call<ServerResponse> call = api.uploadImage(body);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Toast.makeText(getApplicationContext(), "File Uploaded Successfully...", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Error occured...", Toast.LENGTH_LONG).show();
            }
        });
    }
}

