package com.guventopal.basiclauncher;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.guventopal.basiclauncher.dtos.Application;
import com.guventopal.basiclauncher.dtos.GetContact;
import com.guventopal.basiclauncher.dtos.GetMissed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static Context contextOfApplication;
    public static BottomNavigationView navView;
    public static ArrayList<Application> installedApps;
    private static GetContact getContact;
    private static GetMissed getMissed;
    private static PackageManager packageManager;
    public static BottomNavigationView navContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contextOfApplication = getApplicationContext();

        installedApps = fetchnstalledApps();
        getContact = new GetContact(this);
        getMissed = new GetMissed(this);
        packageManager = getPackageManager();

        navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_contacts, R.id.navigation_menu)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupWithNavController(navView, navController);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }

    public static BottomNavigationView getNavViewOfApplication() {
        return navView;
    }

    public static ArrayList<Application> getInstalledApps() { return installedApps; }

    public static GetContact getContactHandle() { return getContact; }
    public static GetMissed getMissedHandle() { return getMissed; }

    public static PackageManager pullPackageManager() { return packageManager; }

    private ArrayList<Application> fetchnstalledApps() {
        ArrayList<Application> res = new ArrayList<Application>();
        PackageManager pm = this.getPackageManager();

        Intent indents[] = {
                new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_APP_CONTACTS),
                new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_APP_MESSAGING),
                new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_APP_CALENDAR),
                new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER),
            };

        for (Intent i : indents) {

            List<ResolveInfo> allApps = pm.queryIntentActivities(i, 0);
            for(ResolveInfo ri:allApps) {
                if(!ri.activityInfo.enabled) continue;

                String name = ri.loadLabel(pm).toString();
                String packageId = ri.activityInfo.packageName;
                String packageName = ri.activityInfo.packageName;
                Drawable icon = ri.activityInfo.loadIcon(pm);
//                System.out.println(ri.activityInfo.documentLaunchMode);

                //if(!isFound(res, packageId) && !name.isEmpty()) {
                    res.add(
                            (new Application(name, icon))
//                                    .setPackageName(packageName)
                                    .setPackageId(packageId)
                    );
                //}
            }
        }

        return res;
    }

    private boolean isFound(ArrayList<Application> applications, String packageId) {
        for (Application app : applications) {
            if(app.getPackageId().equals(packageId)) return true;
        }
        return false;
    }

}