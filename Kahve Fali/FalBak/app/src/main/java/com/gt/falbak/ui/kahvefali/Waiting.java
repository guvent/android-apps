package com.gt.falbak.ui.kahvefali;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.gt.falbak.MainActivity;
import com.gt.falbak.Notifications;
import com.gt.falbak.R;
import com.gt.falbak.ui.KahveFali;

public class Waiting extends AppCompatActivity {
    private int WAITING_SECOND = 180;
    private int WAITING_INTERVAL = 1000;

    private int WAITING_RANDOM_MIN = 100;
    private int WAITING_RANDOM_MAX = 300;

    private RewardedAd rewardedAd;
    private RewardedVideoAd rewardedAdVideo;

    Notifications notifications = null;
    Intent intent;

    TextView coffee_setup;
    TextView after_time;
    ProgressBar after_progress;

    LinearLayout timer;
    LinearLayout look;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);
        (getWindow().getDecorView()).setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        Bundle bundle = getIntent().getExtras();

        WAITING_SECOND = (
                (Double)(Math.random() * (WAITING_RANDOM_MAX - WAITING_RANDOM_MIN))
        ).intValue() + WAITING_RANDOM_MIN;

        coffee_setup = findViewById(R.id.coffee_setup);
        after_time = findViewById(R.id.after_time);
        after_progress = findViewById(R.id.after_progress);

        timer = findViewById(R.id.timer);
        look = findViewById(R.id.look);

        timer.setVisibility(View.VISIBLE);
        look.setVisibility(View.GONE);

        intent = new Intent(this, KahveSonucu.class);

        if(bundle==null) {

            notifications = MainActivity.notifications;

            after_progress.setMax(WAITING_SECOND);

            new CountDownTimer(((WAITING_SECOND + 1) * WAITING_INTERVAL), WAITING_INTERVAL) {
                @Override
                public void onTick(long l) {
                    set_after_text(l);
                }

                @Override
                public void onFinish() {
                    complete();
                }
            }.start();
        } else { complete(); }

        load_ads();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, KahveFali.class);
        startActivity(intent);
    }

    public void set_after_text(long l) {
        int last = Math.round(l/WAITING_INTERVAL);
        int minute = 0;
        int second = 0;

        second = last % 60;
        minute = (last - second) / 60;

        String s = (second<10) ? "0"+ second : Integer.toString(second);
        String m = (minute<10) ? "0"+ minute : Integer.toString(minute);

        after_time.setText("Kalan Süre \n "+ (m + ":" + s));
        after_progress.setProgress(last);
    }

    public void complete() {
        timer.setVisibility(View.GONE);
        look.setVisibility(View.VISIBLE);
        coffee_setup.setText(getString(R.string.fal_hazir));

        Button goster = findViewById(R.id.goster);
        goster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_reward_ads();
            }
        });

        if(notifications!=null) notifications.createNotification();
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

        rewardedAdVideo = MobileAds.getRewardedVideoAdInstance(this);
        rewardedAdVideo.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {

            }

            @Override
            public void onRewardedVideoAdOpened() { }

            @Override
            public void onRewardedVideoStarted() { }

            @Override
            public void onRewardedVideoAdClosed() { }

            @Override
            public void onRewarded(RewardItem rewardItem) {
                show_result();
            }

            @Override
            public void onRewardedVideoAdLeftApplication() { }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) { }

            @Override
            public void onRewardedVideoCompleted() { }
        });
        rewardedAdVideo.loadAd(getString(R.string.ad_reward_id), new AdRequest.Builder().build());
    }

    public void show_reward_ads() {
        if(rewardedAdVideo.isLoaded()) {
            rewardedAdVideo.show();
        } else {
//            Toast.makeText(this, "Ads Not Ready!!!", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "The rewarded ad wasn't loaded yet.");
            show_result();
        }

    }

    public void show_result() {
        startActivity(intent);
        if(notifications!=null) notifications.removeNotification();
    }
}