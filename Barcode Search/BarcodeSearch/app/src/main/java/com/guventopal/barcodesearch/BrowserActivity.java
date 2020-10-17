package com.guventopal.barcodesearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class BrowserActivity extends AppCompatActivity {
    private Intent mainActivity;
    public WebView browser;
    public String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        mainActivity = new Intent(this, BarcodeReader.class);

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            url= null;
        } else {
            url= extras.getString("BARCODE_URL");
        }

        browser = (WebView) findViewById(R.id.browser);

        WebSettings settings = browser.getSettings();
        settings.setAllowContentAccess(true);
        settings.setDatabaseEnabled(true);
        settings.setDisplayZoomControls(false);
        settings.setGeolocationEnabled(true);
        settings.setJavaScriptEnabled(true);

        browser.setWebViewClient(new WebViewClient());

        browser.loadUrl(url);

        load_ads();

    }

    @Override
    public void onBackPressed() {
        startActivity(mainActivity);
    }

    public void load_ads() {
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        final LinearLayout banner = findViewById(R.id.main_banner);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();

                banner.setVisibility(View.VISIBLE);
                System.out.println("AD LOADED............");
            }
        });
    }
}