package com.chandrapal.manage_college;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.material.appbar.CollapsingToolbarLayout;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        WebView webView = findViewById(R.id.web_view_activity_web_view);
//        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapseingToolbar_activity_web_view);
//        collapsingToolbarLayout.setTitle(getIntent().getStringExtra("link"));
        webView.loadUrl(getIntent().getStringExtra("link"));
//        webView.loadUrl("http://schoolmanagementsystem.epizy.com");
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.clearCache(true);
        webView.clearHistory();

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

    }
}