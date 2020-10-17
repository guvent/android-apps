package com.gt.falbak;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.gt.falbak.ui.KahveFali;

public class MainActivity extends AppCompatActivity {
    public static Notifications notifications;
    private Intent intent;

    int retry = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        (getWindow().getDecorView()).setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        Button basla = (Button) findViewById(R.id.basla);
        intent = new Intent(this, KahveFali.class);

        notifications = new Notifications(this,(NotificationManager) getSystemService(NOTIFICATION_SERVICE));

        basla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        load_ads();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Çıkmak için tekrar basın.", Toast.LENGTH_SHORT).show();
        if(retry<=0) System.exit(0); else retry--;
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