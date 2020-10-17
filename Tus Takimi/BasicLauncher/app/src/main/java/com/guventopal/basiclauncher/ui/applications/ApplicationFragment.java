package com.guventopal.basiclauncher.ui.applications;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.autofill.AutofillValue;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.guventopal.basiclauncher.MainActivity;
import com.guventopal.basiclauncher.R;
import com.guventopal.basiclauncher.dtos.Application;

import java.util.ArrayList;

public class ApplicationFragment extends Fragment {
    private View root;
    private GridView appView;

    private ApplicationViewModel applicationViewModel;
    public static final int PERMISSIONS_REQUEST_READ_APPS = 2;

    private ArrayList<Application> applicationInfos;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        applicationViewModel =
                ViewModelProviders.of(this).get(ApplicationViewModel.class);
        this.root = inflater.inflate(R.layout.fragment_applications, container, false);

        appView = (GridView) this.root.findViewById(R.id.applications);

        applicationInfos = getApplications();

        ApplicationAdapter applicationAdapter = new ApplicationAdapter(
                this.getContext(), R.layout.apps_grid_view, applicationInfos);
        appView.setAdapter(applicationAdapter);

        appView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectApp(i);
            }
        });

        final LinearLayout banner = root.findViewById(R.id.banner);

        AdView mAdView = root.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();

                ViewGroup.LayoutParams params = banner.getLayoutParams();
                params.height = 320;
                banner.setLayoutParams(params);
                System.out.println("AD LOADED............");
            }
        });

//        final AdView adView = new AdView(getActivity());
//        adView.setAdUnitId("ca-app-pub-8300712157555654/7213255043");
//        AdSize adSize = new AdSize(300, 50);
//        adView.setAdSize(adSize);
//
//        final AdRequest.Builder adReq = new AdRequest.Builder();
//        final AdRequest adRequest = adReq.build();
//        adView.loadAd(adRequest);


//        final LinearLayout adLinLay = (LinearLayout) root.findViewById(R.id.banner);
//        adLinLay.addView(adView);


//
//        LinearLayout adContainer = (LinearLayout) root.findViewById(R.id.banner);
//        AdView mAdView = new AdView(this.root.getContext());
//        mAdView.setAdSize(AdSize.BANNER);
//        mAdView.setAdUnitId("ca-app-pub-8300712157555654/7213255043");
//        AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
//
////        adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
//
//        mAdView.loadAd(adRequestBuilder.build());
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        adContainer.addView(mAdView, params);

        return root;
    }

    private void selectApp(int index) {
        Application app = applicationInfos.get(index);
        PackageManager pm = MainActivity.pullPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(app.getPackageId());
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String permissions[],
            int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_APPS:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this.getActivity(), "Permission Granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this.getActivity(), "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private boolean showPhoneStatePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.READ_CONTACTS);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(), Manifest.permission.READ_CONTACTS)) {
                showExplanation("Permission Needed", "Rationale", Manifest.permission.READ_CONTACTS, PERMISSIONS_REQUEST_READ_APPS);
            } else {
                Toast.makeText(this.getActivity(), "Permission Denied!\nGo Settings > Applications > Allow Permission", Toast.LENGTH_SHORT).show();
                showExplanation("Permission Needed", "Rationale", Manifest.permission.READ_CONTACTS, PERMISSIONS_REQUEST_READ_APPS);
                requestPermission(Manifest.permission.READ_CONTACTS, PERMISSIONS_REQUEST_READ_APPS);
            }
        } else {
            //Toast.makeText(this.getActivity(), "Permission (already) Granted!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void showExplanation(String title,
                                 String message,
                                 final String permission,
                                 final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(permission, permissionRequestCode);
                    }
                });
        builder.create().show();
    }

    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(this.getActivity(), new String[]{permissionName}, permissionRequestCode);
    }

    public ArrayList<Application> getApplications() {
        return MainActivity.getInstalledApps();
    }

}