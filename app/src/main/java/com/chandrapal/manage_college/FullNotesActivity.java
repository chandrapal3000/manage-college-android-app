package com.chandrapal.manage_college;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

public class FullNotesActivity extends AppCompatActivity implements View.OnClickListener {

    TextView username, uploadedTime, title, branch, subjectAndChapter;
    String user_id, username_string;
    ShapeableImageView profileImage;
    WebView webView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_notes);

//        Objects.requireNonNull(getSupportActionBar()).hide();

        username = findViewById(R.id.username);
        uploadedTime = findViewById(R.id.uploadedTime);
        branch = findViewById(R.id.branch);
        title =findViewById(R.id.title);
        webView = findViewById(R.id.notesWebView);
        profileImage = findViewById(R.id.profileImage);
        subjectAndChapter = findViewById(R.id.subjectAndChapter);
        ShapeableImageView shapeableImageView_full_notes = findViewById(R.id.CoverImage_full_notes);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapseingToolbar_full_notes);

        username.setText(getIntent().getStringExtra("notesUploadedBy"));
        user_id = getIntent().getStringExtra("notesUserId");
        username_string = getIntent().getStringExtra("notesUploadedBy");
        uploadedTime.setText(getIntent().getStringExtra("notesUploadedTime"));
        String branchString = "For "+getIntent().getStringExtra("notesBranch");
        branch.setText(branchString);
        collapsingToolbarLayout.setTitle(getIntent().getStringExtra("notesTitle"));
        title.setText(getIntent().getStringExtra("notesTitle"));

        String subjectAndChapterString = "Subject "+getIntent().getStringExtra("notesSubject")+" Chapter "+getIntent().getStringExtra("notesChapter");
        subjectAndChapter.setText(subjectAndChapterString);
        Picasso.get().load(getIntent().getStringExtra("notesUserProfileImage")).into(profileImage);

        String htmlString = getIntent().getStringExtra("notesNotesText");
//        String encodedHtmlBase64Encode = Base64.encodeToString(htmlString.getBytes(), Base64.NO_PADDING);

        webView.loadDataWithBaseURL(null,htmlString,"text/html", "utf-8", null);
        Log.d(TAG, "onCreate: "+htmlString);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.clearCache(true);
        webView.clearHistory();

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        username.setOnClickListener(this);
        profileImage.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
        intent.putExtra("user_id", user_id);
        intent.putExtra("uploaded_by", username_string);
        startActivity(intent);
//        overridePendingTransition(R.anim.nav_default_pop_enter_anim,R.anim.nav_default_pop_exit_anim);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        getMenuInflater().inflate(R.menu.action_bar_full_notes, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item){
//        if (item.getItemId() == android.R.id.home) {
//            onBackPressed();
//            return true;
//        }
//
//        if (item.getItemId() == R.id.action_share_action_bar_full_notes) {
//            Toast.makeText(getApplicationContext(), "Share this post", Toast.LENGTH_SHORT).show();
//        }
//
//        if (item.getItemId() == R.id.action_settings_action_bar_full_notes) {
//            Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();
//        }
//
//        if (item.getItemId() == R.id.action_faq_action_bar_full_notes) {
//            Toast.makeText(getApplicationContext(), "FAQs", Toast.LENGTH_SHORT).show();
//        }
//
//        if (item.getItemId() == R.id.action_about_us_action_bar_full_notes) {
//            Toast.makeText(getApplicationContext(), "About us", Toast.LENGTH_SHORT).show();
//        }
//
//        if (item.getItemId() == R.id.action_visit_website_action_bar_full_notes) {
//            Toast.makeText(getApplicationContext(), "Visit our website", Toast.LENGTH_SHORT).show();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

}