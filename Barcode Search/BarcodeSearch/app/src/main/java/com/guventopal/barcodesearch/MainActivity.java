package com.guventopal.barcodesearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.net.InetAddress;

public class MainActivity extends AppCompatActivity {
    private Intent mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = new Intent(this, BarcodeReader.class);

        Button checkInternet = findViewById(R.id.check_internet);

        checkInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_connection();
            }
        });

        check_connection();
    }

    public void check_connection() {
        Toast.makeText(this, "Check Internet Connection!", Toast.LENGTH_SHORT);
        if (isNetworkConnected()) {
            startActivity(mainActivity);
        } else {
            Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT);
        }
    }
//
//    public boolean isInternetAvailable() {
//        try {
//            InetAddress ipAddr = InetAddress.getByName("google.com");
//            return !ipAddr.equals("");
//
//        } catch (Exception e) {
//            return false;
//        }
//    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
