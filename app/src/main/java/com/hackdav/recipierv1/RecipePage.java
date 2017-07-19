package com.hackdav.recipierv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class RecipePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        String url= intent.getStringExtra("URL");
        setContentView(R.layout.activity_recipe_page);
        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl(url);
    }
}
