package com.chandrapal.manage_college;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chandrapal.manage_college.ui.photos.AdapterPhotos;
import com.github.drjacky.imagepicker.ImagePicker;
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

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

import static android.content.ContentValues.TAG;

public class UpdateProfileActivity extends AppCompatActivity {

    EditText username, fullname, phone, email, bio, password;
    Button update_button;
    ShapeableImageView profile_image;
    ProgressBar profile_image_progressbar, profile_update_progressbar;
    FloatingActionButton floating_change_image_button;
    Button upload_image_button;
    private static final String dir = "https://manage-college.000webhostapp.com/upload_image/images/";
    private static final String WEB_URL = "https://manage-college.000webhostapp.com";
    private static final String web_upload_image_url = "https://manage-college.000webhostapp.com/android/insert_file.php";
    private static final String WEB_URL_UPDATE_USERS = "https://manage-college.000webhostapp.com/update_users.php";

    String encodedImage;
    String uploadedImageGeneratedName;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

//        Objects.requireNonNull(getSupportActionBar()).hide();

        username = findViewById(R.id.username_update_profile);
        fullname = findViewById(R.id.fullname_update_profile);
        phone = findViewById(R.id.phone_update_profile);
        email = findViewById(R.id.email_update_profile);
        bio = findViewById(R.id.bio_update_profile);
        password = findViewById(R.id.password_update_profile);
        profile_image = findViewById(R.id.profileImage_update_profile);
        profile_image_progressbar = findViewById(R.id.profile_image_progressbar_update_profile);
        floating_change_image_button = findViewById(R.id.floating_change_image_button_update_profile);
        upload_image_button = findViewById(R.id.upload_image_button_update_profile);
        profile_update_progressbar = findViewById(R.id.profile_update_progressbar_update_profile);
        update_button = findViewById(R.id.submit_update_profile);

        profile_image_progressbar.getIndeterminateDrawable().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);
        profile_update_progressbar.getIndeterminateDrawable().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);



        SharedPreferences sharedPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE);


        if(sharedPreferences.contains("userType") && sharedPreferences.contains("username") ){

            if(sharedPreferences.getString("status", "").equals("disable")){

                new MaterialAlertDialogBuilder(UpdateProfileActivity.this)
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
            new MaterialAlertDialogBuilder(UpdateProfileActivity.this)
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

        if(sharedPreferences.contains("username")) {
            username.setText(sharedPreferences.getString("username", ""));
            fullname.setText(sharedPreferences.getString("fullname", ""));
            phone.setText(sharedPreferences.getString("phone", ""));
            email.setText(sharedPreferences.getString("email", ""));
            bio.setText(sharedPreferences.getString("bio", ""));
            Picasso.get().load(dir+sharedPreferences.getString("profileImage", "")).into(profile_image);
            uploadedImageGeneratedName = sharedPreferences.getString("profileImage", "");

            floating_change_image_button.setOnClickListener(v->{
                Toast.makeText(this, "Change profile Image", Toast.LENGTH_SHORT).show();
//                ImagePicker.Companion.with(this).crop().cropOval().cropFreeStyle().galleryOnly().maxResultSize(1080, 1080, true).createIntent();

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

            upload_image_button.setOnClickListener(v->{
                profile_image_progressbar.setVisibility(View.VISIBLE);

                    String sessionUsername = sharedPreferences.getString("username", "");
                    String sessionUserId = sharedPreferences.getString("id", "");

                Log.d(TAG, "onCreate: log inside uploadProfileImageToServer: username "+sessionUsername+" user id :"+sessionUserId);

                    Log.d(TAG, "log inside uploadProfileImageToServer: ");


                StringRequest stringRequest = new StringRequest(Request.Method.POST, web_upload_image_url, response -> {
                    profile_image_progressbar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                    try {
                            JSONArray jsonArray = new JSONArray(response);
                            String message = jsonArray.getJSONObject(0).getString("message");
                            String messageFull = jsonArray.getJSONObject(0).getString("message_full");

                        Toast.makeText(getApplicationContext(), messageFull, Toast.LENGTH_LONG).show();

                        if(message.equals("success")) {
                                uploadedImageGeneratedName = jsonArray.getJSONObject(0).getString("file_name");
                                Picasso.get().load(dir+uploadedImageGeneratedName).into(profile_image);
                                Toast.makeText(getApplicationContext(),"Image updated, now cloick on save changes to save changes you mase here", Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(getApplicationContext(), "could not update image", Toast.LENGTH_LONG).show();
//                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getApplicationContext());
//                                builder.setTitle("Connection error");
//                                builder.setMessage("Could not connect to the server, Make sure you are connected to the internet");
//                                builder.setCancelable(true);
//                                builder.setPositiveButton("Ok", (dialog, which) -> dialog.cancel());
//                                android.app.AlertDialog alertDialog = builder.create();
//                                alertDialog.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                }, error -> {
                    profile_image_progressbar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_LONG).show();
//                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getApplicationContext());
//                        builder.setTitle("Connection error");
//                        builder.setMessage("Could not connect to the server, Make sure you are connected to the internet");
//                        builder.setCancelable(true);
//                        builder.setPositiveButton("Ok", (dialog, which) -> dialog.cancel());
//                        android.app.AlertDialog alertDialog = builder.create();
//                        alertDialog.show();
                }){
                    @Nullable
                    @org.jetbrains.annotations.Nullable
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> data = new HashMap<>();
                        data.put("uploaded_by", sessionUsername);
                        data.put("user_id", sessionUserId);
                        data.put("encoded_file", encodedImage);

                        return data;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);

            });

            update_button.setOnClickListener(v-> {

                String sessionUsername = sharedPreferences.getString("username", "");
                String sessionUserId = sharedPreferences.getString("id", "");
                String usernameString = username.getText().toString();
                String fullnameString = fullname.getText().toString();
                String phoneString = phone.getText().toString();
                String emailString = email.getText().toString();
                String bioString = bio.getText().toString();
                String passwordString = password.getText().toString();

                if(!passwordString.equals("") && !usernameString.equals("") ) {

                    profile_update_progressbar.setVisibility(View.VISIBLE);

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, WEB_URL_UPDATE_USERS, response -> {
                        profile_update_progressbar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        try {
                            JSONArray jsonArray = new JSONArray(response);
//                        String message = jsonArray.getJSONObject(0).getString("message");
//                        String messageFull = jsonArray.getJSONObject(0).getString("message_full");

                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                            if (response.equals("success")) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("profileImage", uploadedImageGeneratedName);
                                editor.putString("username",usernameString );
                                editor.putString("fullname", fullnameString);
                                editor.putString("email", emailString);
                                editor.putString("phone",phoneString );
                                editor.putString("bio", bioString);
                                editor.apply();

                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getApplicationContext());
                                builder.setTitle("Changes saved");
                                builder.setMessage("You have to restart the app to see changes");
                                builder.setCancelable(true);
                                builder.setPositiveButton("Ok", (dialog, which) -> dialog.cancel());
                                android.app.AlertDialog alertDialog = builder.create();
                                alertDialog.show();

                            } else {
                                Toast.makeText(getApplicationContext(), "could not save changes, please try again", Toast.LENGTH_LONG).show();
//                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getApplicationContext());
//                                builder.setTitle("Connection error");
//                                builder.setMessage("Could not connect to the server, Make sure you are connected to the internet");
//                                builder.setCancelable(true);
//                                builder.setPositiveButton("Ok", (dialog, which) -> dialog.cancel());
//                                android.app.AlertDialog alertDialog = builder.create();
//                                alertDialog.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }, error -> {
                        profile_update_progressbar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_LONG).show();
//                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getApplicationContext());
//                        builder.setTitle("Connection error");
//                        builder.setMessage("Could not connect to the server, Make sure you are connected to the internet");
//                        builder.setCancelable(true);
//                        builder.setPositiveButton("Ok", (dialog, which) -> dialog.cancel());
//                        android.app.AlertDialog alertDialog = builder.create();
//                        alertDialog.show();
                    }) {
                        @Nullable
                        @org.jetbrains.annotations.Nullable
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> data = new HashMap<>();

                            data.put("username", usernameString);
                            data.put("id", sessionUserId);
                            data.put("fullname", fullnameString);
                            data.put("phone", phoneString);
                            data.put("email", emailString);
                            data.put("bio", bioString);
                            data.put("password", passwordString);
                            data.put("profile_image", uploadedImageGeneratedName);

                            return data;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                } else{
                    Toast.makeText(getApplicationContext(), "Password and username field are required", Toast.LENGTH_LONG).show();
                }
            });

        } else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(Html.fromHtml("<font color='#FF7F27'>Login Status</font>"));
            builder.setCancelable(false);
            builder.setMessage("You are not logged in yet, Login now?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            });
            builder.setNegativeButton("Not now", (dialog, which) -> dialog.cancel());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
//                    profile_image_progressbar.setVisibility(View.VISIBLE);
                    upload_image_button.setVisibility(View.VISIBLE);
//                    Toast.makeText(this, "You Got the images", Toast.LENGTH_SHORT).show();
                    // There are no request codes
                    Intent data = result.getData();

                    Log.d(TAG, "log intent: "+data);

                    try {
                        Uri uri = data.getData();
                        Log.d(TAG, "log uri: "+data);

                        InputStream inputStream = getContentResolver().openInputStream(uri);
                        Log.d(TAG, "log inputStream: "+inputStream);

                        bitmap = BitmapFactory.decodeStream(inputStream);
//                        profile_image.setImageBitmap(bitmap);
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
//        Toast.makeText(this, "You Got the the intent", Toast.LENGTH_SHORT).show();
//        intent.setType("images/*");
        someActivityResultLauncher.launch(intent);
    }

}