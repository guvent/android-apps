package com.gt.falbak.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.gt.falbak.MainActivity;
import com.gt.falbak.R;
import com.gt.falbak.adapter.MenuAdapter;
import com.gt.falbak.ui.kahvefali.Fincan;
import com.gt.falbak.ui.kahvefali.SoruCevap;

import java.util.ArrayList;

public class KahveFali extends AppCompatActivity {
    ArrayList<String> menu = new ArrayList<>();
    Context thisContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kahve_fali);
        (getWindow().getDecorView()).setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        thisContext = this.getBaseContext();

        final Intent fincan = new Intent(this, Fincan.class);
        final Intent sorucevap = new Intent(this, SoruCevap.class);

        menu.add("Fincan Resmi");
        menu.add("Soru Cevap");

        GridView menuView = findViewById(R.id.secimler);
        MenuAdapter menuAdapter = new MenuAdapter(this, R.layout.menu_grid_view, menu);
        menuView.setAdapter(menuAdapter);

        menuView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String menuSecimi = menu.get(i).toString() + " Seçtiniz";
                Toast.makeText(thisContext, menuSecimi, Toast.LENGTH_SHORT).show();

                if(i==0) startActivity(fincan);
                if(i==1) startActivity(sorucevap);

            }
        });

        load_ads();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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