package com.gt.falbak.ui.kahvefali;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.gt.falbak.R;
import com.gt.falbak.ui.KahveFali;

public class SoruCevap extends AppCompatActivity {
    ToggleButton kahve_sade;
    ToggleButton kahve_orta;
    ToggleButton kahve_sekerli;
    ToggleButton telve_acik;
    ToggleButton telve_normal;
    ToggleButton telve_koyu;
    ToggleButton sut_evet;
    ToggleButton sut_hayir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soru_cevap);
        (getWindow().getDecorView()).setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        final Intent intent = new Intent(this, Waiting.class);
        Button cevapla = findViewById(R.id.cevapla);

        cevapla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        secimleri_hazirla();
        secimleri_calistir();

        load_ads();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, KahveFali.class);
        startActivity(intent);
    }

    public void secimleri_hazirla() {
        kahve_sade = findViewById(R.id.kahve_sade);
        kahve_orta = findViewById(R.id.kahve_orta);
        kahve_sekerli = findViewById(R.id.kahve_sekerli);

        telve_acik = findViewById(R.id.telve_acik);
        telve_normal = findViewById(R.id.telve_normal);
        telve_koyu = findViewById(R.id.telve_koyu);

        sut_evet = findViewById(R.id.sut_evet);
        sut_hayir = findViewById(R.id.sut_hayir);
    }

    public void secimleri_calistir() {
        kahve_sade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { set_kahve(0); }
        });

        kahve_orta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { set_kahve(1); }
        });

        kahve_sekerli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { set_kahve(2); }
        });

        telve_acik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { set_telve(0); }
        });

        telve_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { set_telve(1); }
        });

        telve_koyu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { set_telve(2); }
        });

        sut_hayir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { set_sut(0); }
        });

        sut_evet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { set_sut(1); }
        });


    }

    public void set_kahve(int i) {
        switch (i) {
            case 0:
                kahve_sade.setBackgroundColor(getColor(R.color.colorPressed));
                kahve_orta.setBackgroundColor(getColor(R.color.colorUnpressed));
                kahve_sekerli.setBackgroundColor(getColor(R.color.colorUnpressed));
                break;
            case 1:
                kahve_sade.setBackgroundColor(getColor(R.color.colorUnpressed));
                kahve_orta.setBackgroundColor(getColor(R.color.colorPressed));
                kahve_sekerli.setBackgroundColor(getColor(R.color.colorUnpressed));
                break;
            case 2:
                kahve_sade.setBackgroundColor(getColor(R.color.colorUnpressed));
                kahve_orta.setBackgroundColor(getColor(R.color.colorUnpressed));
                kahve_sekerli.setBackgroundColor(getColor(R.color.colorPressed));
                break;
        }
    }

    public void set_telve(int i) {
        switch (i) {
            case 0:
                telve_acik.setBackgroundColor(getColor(R.color.colorPressed));
                telve_normal.setBackgroundColor(getColor(R.color.colorUnpressed));
                telve_koyu.setBackgroundColor(getColor(R.color.colorUnpressed));
                break;
            case 1:
                telve_acik.setBackgroundColor(getColor(R.color.colorUnpressed));
                telve_normal.setBackgroundColor(getColor(R.color.colorPressed));
                telve_koyu.setBackgroundColor(getColor(R.color.colorUnpressed));
                break;
            case 2:
                telve_acik.setBackgroundColor(getColor(R.color.colorUnpressed));
                telve_normal.setBackgroundColor(getColor(R.color.colorUnpressed));
                telve_koyu.setBackgroundColor(getColor(R.color.colorPressed));
                break;
        }
    }

    public void set_sut(int i) {
        switch (i) {
            case 0:
                sut_hayir.setBackgroundColor(getColor(R.color.colorPressed));
                sut_evet.setBackgroundColor(getColor(R.color.colorUnpressed));
                break;
            case 1:
                sut_hayir.setBackgroundColor(getColor(R.color.colorUnpressed));
                sut_evet.setBackgroundColor(getColor(R.color.colorPressed));
                break;
        }

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