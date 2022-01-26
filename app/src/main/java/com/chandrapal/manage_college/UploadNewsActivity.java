package com.chandrapal.manage_college;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UploadNewsActivity extends AppCompatActivity {

    WebView webView;
    String WEB_URL_INSERT_NEWS = "https://manage-college.000webhostapp.com/insert_news.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_news);

        Spinner spinner_branches = findViewById(R.id.spinner_branches_upload_news);
        Spinner spinner_status = findViewById(R.id.spinner_status_upload_news);
        TextInputEditText editText_title = findViewById(R.id.title_upload_news);
        Button upload_button = findViewById(R.id.submit_upload_news);
        ProgressBar upload_progressbar = findViewById(R.id.progressbar_submit_upload_news);

        SharedPreferences sharedPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE);

        if(sharedPreferences.contains("userType") && sharedPreferences.contains("username") ){

            if(sharedPreferences.getString("userType", "").equals("student")){

                new MaterialAlertDialogBuilder(UploadNewsActivity.this)
                        .setTitle("Server response")
                        .setMessage("You are a student, You cannot upload news!")
                        .setCancelable(false)
                        .setPositiveButton("Ok",(dialog, which) -> {
                            onBackPressed();
                        })
                        .show();

            } else if(sharedPreferences.getString("status", "").equals("disable")){

                new MaterialAlertDialogBuilder(UploadNewsActivity.this)
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
            new MaterialAlertDialogBuilder(UploadNewsActivity.this)
                    .setTitle("Server response")
                    .setMessage("You are not logged in, You have to login to upload news!")
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

        upload_button.setOnClickListener(v -> {

            if (sharedPreferences.contains("username")) {

                String branch = spinner_branches.getSelectedItem().toString();
                String status = spinner_status.getSelectedItem().toString();
                String title = Objects.requireNonNull(editText_title.getText()).toString();
                String sessionUsername = sharedPreferences.getString("username", "");
                String sessionUserId = sharedPreferences.getString("id", "");

                if (!title.equals("") && !branch.equals("Choose branch") && !status.equals("Choose status") ) {

                    upload_progressbar.setVisibility(View.VISIBLE);

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, WEB_URL_INSERT_NEWS, response -> {
                        upload_progressbar.setVisibility(View.GONE);

                        Toast.makeText(UploadNewsActivity.this, response, Toast.LENGTH_LONG).show();

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            String message = jsonArray.getJSONObject(0).getString("message");
                            String messageFull = jsonArray.getJSONObject(0).getString("message_full");

                            Toast.makeText(UploadNewsActivity.this, messageFull, Toast.LENGTH_SHORT).show();

                            if (message.equals("success")) {

                                new MaterialAlertDialogBuilder(UploadNewsActivity.this)
                                        .setTitle("Response from server")
                                        .setMessage("News uploaded successfully, See in news page")
                                        .setCancelable(true)
                                        .setPositiveButton("Ok", /* listener = */ null)
                                        .show();

                            } else {
                                Toast.makeText(UploadNewsActivity.this, "could not save changes, please try again", Toast.LENGTH_LONG).show();

                                new MaterialAlertDialogBuilder(UploadNewsActivity.this)
                                        .setTitle("Connection error")
                                        .setMessage("Could not connect to the server, Make sure you are connected to the internet")
                                        .setCancelable(true)
                                        .setPositiveButton("Ok", /* listener = */ null)
                                        .show();

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }, error -> {
                        upload_progressbar.setVisibility(View.GONE);
                        Toast.makeText(UploadNewsActivity.this, error.toString().trim(), Toast.LENGTH_LONG).show();

                        new MaterialAlertDialogBuilder(UploadNewsActivity.this)
                                .setTitle("Connection error")
                                .setMessage("Could not connect to the server, Make sure you are connected to the internet, if your internet is working fine then that may be server error.")
                                .setPositiveButton("Ok", /* listener = */ null)
                                .show();

                    }) {
                        @Nullable
                        @org.jetbrains.annotations.Nullable
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> data = new HashMap<>();

                            data.put("uploaded_by", sessionUsername);
                            data.put("user_id", sessionUserId);
                            data.put("title", title);
                            data.put("branch", branch);
                            data.put("status", status);

                            return data;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(UploadNewsActivity.this);
                    requestQueue.add(stringRequest);
                } else {
                    Toast.makeText(UploadNewsActivity.this, "All fields are required", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(UploadNewsActivity.this, "You are not logged in", Toast.LENGTH_SHORT).show();
            }

        });

    }
}