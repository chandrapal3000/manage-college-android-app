package com.chandrapal.manage_college;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import jp.wasabeef.richeditor.RichEditor;

import static android.content.ContentValues.TAG;

public class UploadNotesActivity extends AppCompatActivity {

    WebView webView;
    String WEB_URL_FORM_UPLOAD_NOTES_ANDROID = "http://schoolmanagementsystem.epizy.com/ck_editor_for_android.php";
    String WEB_URL_FORM_UPLOAD_NOTES_ANDROID_000WEBHOST = "https://manage-college.000webhostapp.com/form_upload_notes_android.php";
    String WEB_URL_INSERT_NOTES = "https://manage-college.000webhostapp.com/insert_notes.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notes);

//        Objects.requireNonNull(getSupportActionBar()).hide();

        Spinner spinner_branches = findViewById(R.id.spinner_branches_upload_notes);
        Spinner spinner_subject = findViewById(R.id.spinner_subjects_upload_notes);
        Spinner spinner_chapter = findViewById(R.id.spinner_chapters_upload_notes);
        TextInputEditText editText_title = findViewById(R.id.title_upload_notes);
        TextInputEditText editText_notes = findViewById(R.id.notes_upload_notes);
        TextView ckeditor_link = findViewById(R.id.textview_link_of_ckeditor_online_upload_notes);
        Button upload_button = findViewById(R.id.submit_upload_notes);
        ProgressBar upload_progressbar = findViewById(R.id.upload_progressbar_upload_notes);

        WebView webView = findViewById(R.id.web_view_activity_upload_notes);

        Button open_in_web_view = findViewById(R.id.button_open_in_web_view_upload_notes);
        Button open_in_web_browser = findViewById(R.id.button_open_in_web_browser_upload_notes);

        open_in_web_view.setOnClickListener(v->{
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("link", WEB_URL_FORM_UPLOAD_NOTES_ANDROID);
            startActivity(intent);
        });

        open_in_web_browser.setOnClickListener(v->{
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(WEB_URL_FORM_UPLOAD_NOTES_ANDROID));
            startActivity(intent);
        });

        SharedPreferences sharedPreferences = getSharedPreferences("loginInfo", MODE_PRIVATE);

        if(sharedPreferences.contains("userType") && sharedPreferences.contains("username") ){

            if(sharedPreferences.getString("userType", "").equals("student")){

                new MaterialAlertDialogBuilder(UploadNotesActivity.this)
                        .setTitle("Server response")
                        .setMessage("You are a student, You cannot upload notes!")
                        .setCancelable(false)
                        .setPositiveButton("Ok",(dialog, which) -> {
//                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                            finish();
                            onBackPressed();
                        })
                        .show();

            } else if(sharedPreferences.getString("status", "").equals("disable")){

                new MaterialAlertDialogBuilder(UploadNotesActivity.this)
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
            new MaterialAlertDialogBuilder(UploadNotesActivity.this)
                    .setTitle("Server response")
                    .setMessage("You are not logged in, You have to login to upload notes!")
                    .setCancelable(false)
                    .setPositiveButton("Login now",(dialog, which) -> {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    })
                    .setNegativeButton("Not now", (dialog, which) -> {
                        onBackPressed();
                    })
                    .show();
        }

        webView.loadUrl(WEB_URL_FORM_UPLOAD_NOTES_ANDROID);


        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.clearCache(true);
        webView.clearHistory();
        webView.requestDisallowInterceptTouchEvent(true);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);


        ckeditor_link.setText("Write notes here. Then click on source and copy it and paste in the above editext. You can include images, videos, links, pdf etc. Or You can also use simple html tags in above edittext like h1, h2, h3 for heading, p for paragraph, br for line break,, hr for horizontal line. For advance notes upload from website");

        upload_button.setOnClickListener(v -> {

            if (sharedPreferences.contains("username")) {

                String branch = spinner_branches.getSelectedItem().toString();
                String subject = spinner_subject.getSelectedItem().toString();
                String chapter = spinner_chapter.getSelectedItem().toString();
                String notes = Objects.requireNonNull(editText_notes.getText()).toString();
                String title = Objects.requireNonNull(editText_title.getText()).toString();
                String sessionUsername = sharedPreferences.getString("username", "");
                String sessionUserId = sharedPreferences.getString("id", "");

                if (!title.equals("") && !notes.equals("") && !branch.equals("Choose branch") && !subject.equals("Choose subject") && !chapter.equals("Choose chapter")) {

                    upload_progressbar.setVisibility(View.VISIBLE);

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, WEB_URL_INSERT_NOTES, response -> {
                        upload_progressbar.setVisibility(View.GONE);

                        Toast.makeText(UploadNotesActivity.this, response, Toast.LENGTH_LONG).show();

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            String message = jsonArray.getJSONObject(0).getString("message");
                            String messageFull = jsonArray.getJSONObject(0).getString("message_full");

                            Toast.makeText(UploadNotesActivity.this, messageFull, Toast.LENGTH_SHORT).show();

                            if (message.equals("success")) {

                                new MaterialAlertDialogBuilder(UploadNotesActivity.this)
                                        .setTitle("Notes uploaded successfully")
                                        .setMessage("See in notes page")
                                        .setPositiveButton("Ok", /* listener = */ null)
                                        .show();

                            } else {
                                Toast.makeText(UploadNotesActivity.this, "could not save changes, please try again", Toast.LENGTH_LONG).show();

                                new MaterialAlertDialogBuilder(UploadNotesActivity.this)
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
                        Toast.makeText(UploadNotesActivity.this, error.toString().trim(), Toast.LENGTH_LONG).show();

                        new MaterialAlertDialogBuilder(UploadNotesActivity.this)
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
                            data.put("notes_text", notes);
                            data.put("branch", branch);
                            data.put("subject", subject);
                            data.put("chapter", chapter);

                            return data;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(UploadNotesActivity.this);
                    requestQueue.add(stringRequest);
                } else {
                    Toast.makeText(UploadNotesActivity.this, "All fields are required", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(UploadNotesActivity.this, "You are not logged in", Toast.LENGTH_SHORT).show();
            }

        });


//        RichEditor editor = findViewById(R.id.rich_editor_upload_notes);
//
//        editor.setEditorHeight(300);
//        editor.setEditorFontSize(22);
//        editor.setEditorFontColor(Color.RED);
//        editor.setPadding(10, 10, 10, 10);
//        editor.setPlaceholder("Insert text here...");
    }

}