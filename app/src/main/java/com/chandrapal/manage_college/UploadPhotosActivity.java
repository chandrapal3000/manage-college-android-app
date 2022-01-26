package com.chandrapal.manage_college;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class UploadPhotosActivity extends AppCompatActivity {

    EditText caption;
    Button update_button;
    ShapeableImageView profile_image;
    ProgressBar upload_progressbar;
    FloatingActionButton floating_change_image_button;
    private static final String dir = "https://manage-college.000webhostapp.com/upload_image/images/";
    private static final String WEB_URL = "https://manage-college.000webhostapp.com";
    private static final String web_upload_image_url = "https://manage-college.000webhostapp.com/android/insert_file.php";
    private static final String WEB_URL_INSERT_PHOTOS = "https://manage-college.000webhostapp.com/insert_photos_android.php";

    String encodedImage;
    String uploadedImageGeneratedName;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photos);

//        Objects.requireNonNull(getSupportActionBar()).hide();

        caption = findViewById(R.id.edit_text_caption_upload_photos);

        profile_image = findViewById(R.id.profileImage_upload_photos);
        floating_change_image_button = findViewById(R.id.floating_change_image_button_upload_photos);
        upload_progressbar = findViewById(R.id.upload_progressbar_upload_photos);
        update_button = findViewById(R.id.submit_upload_photos);

        upload_progressbar.getIndeterminateDrawable().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);

        SharedPreferences sharedPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE);

        if(sharedPreferences.contains("userType") && sharedPreferences.contains("username") ){

            if(sharedPreferences.getString("status", "").equals("disable")){

                new MaterialAlertDialogBuilder(UploadPhotosActivity.this)
                        .setTitle("Server response")
                        .setMessage("You are not verified, Contact admin")
                        .setCancelable(false)
                        .setPositiveButton("Ok",(dialog, which) -> {
                            onBackPressed();
                        })
                        .show();

            }else{

            }

        }else{
            new MaterialAlertDialogBuilder(UploadPhotosActivity.this)
                    .setTitle("Server response")
                    .setMessage("You are not logged in, You have to login to upload photos!")
                    .setCancelable(false)
                    .setPositiveButton("Login now",(dialog, which) -> {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    })
                    .setNegativeButton("Not now", (dialog, which) -> {
//                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                        finish();
                        onBackPressed();
                    })
                    .show();
        }

            floating_change_image_button.setOnClickListener(v->{
                Toast.makeText(this, "Change profile Image", Toast.LENGTH_SHORT).show();

                Dexter.withContext(this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                openSomeActivityForResult(intent);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                                Toast.makeText(getApplicationContext(), "You denied the permission", Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();

            });

            update_button.setOnClickListener(v-> {

                String sessionUsername = sharedPreferences.getString("username", "");
                String sessionUserId = sharedPreferences.getString("id", "");
                String captionString = caption.getText().toString();

                if(!encodedImage.equals("")) {

                    upload_progressbar.setVisibility(View.VISIBLE);

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, WEB_URL_INSERT_PHOTOS, response -> {
                        upload_progressbar.setVisibility(View.GONE);

                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        try {
                            JSONArray jsonArray = new JSONArray(response);
//                        String message = jsonArray.getJSONObject(0).getString("message");
//                        String messageFull = jsonArray.getJSONObject(0).getString("message_full");

                            if (response.equals("success")) {

                                new MaterialAlertDialogBuilder(UploadPhotosActivity.this)
                                        .setTitle("Response from server")
                                        .setMessage("Photo uploaded successfully, See in the photos page")
                                        .setPositiveButton("Ok", /* listener = */ null)
                                        .show();

                            } else {
                                Toast.makeText(getApplicationContext(), "could not save changes, please try again", Toast.LENGTH_LONG).show();
                                new MaterialAlertDialogBuilder(UploadPhotosActivity.this)
                                        .setTitle("Response from server")
                                        .setMessage("Could not upload photo, Try again")
                                        .setPositiveButton("Ok", /* listener = */ null)
                                        .show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }, error -> {
                        upload_progressbar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_LONG).show();
//                        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//                        builder.setTitle("Connection error");
//                        builder.setMessage("Could not connect to the server, Make sure you are connected to the internet");
//                        builder.setCancelable(true);
//                        builder.setPositiveButton("Try again", (dialog, which) -> dialog.cancel());
//                        AlertDialog alertDialog = builder.create();
//                        alertDialog.show();
                    }) {
                        @Nullable
                        @org.jetbrains.annotations.Nullable
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> data = new HashMap<>();

                            data.put("uploaded_by", sessionUsername);
                            data.put("user_id", sessionUserId);
                            data.put("caption", captionString);
                            data.put("encoded_file", encodedImage);

                            return data;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                } else{
                    Toast.makeText(getApplicationContext(), "Select an image first!", Toast.LENGTH_LONG).show();
                }
            });

    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Toast.makeText(this, "You Got the images", Toast.LENGTH_SHORT).show();
                    // There are no request codes
                    Intent data = result.getData();

                    Log.d(TAG, "log intent: "+data);

                    try {
                        Uri uri = data.getData();
                        Log.d(TAG, "log uri: "+data);

                        InputStream inputStream = getContentResolver().openInputStream(uri);
                        Log.d(TAG, "log inputStream: "+inputStream);

                        bitmap = BitmapFactory.decodeStream(inputStream);
                        profile_image.setImageBitmap(bitmap);
                        Log.d(TAG, "log bitmap: "+bitmap);

                        encodeBitmapImage(bitmap);

                    } catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });

    private void encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] bytesOfImage = byteArrayOutputStream.toByteArray();
        encodedImage = Base64.encodeToString(bytesOfImage, Base64.DEFAULT);
        Log.d(TAG, "log encodedImage: "+encodedImage);
    }

    private void openSomeActivityForResult(Intent intent) {
        Toast.makeText(this, "You Got the the intent", Toast.LENGTH_SHORT).show();
//        intent.setType("images/*");
        someActivityResultLauncher.launch(intent);
    }

}