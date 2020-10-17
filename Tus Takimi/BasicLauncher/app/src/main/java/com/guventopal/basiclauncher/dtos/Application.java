package com.guventopal.basiclauncher.dtos;

import android.graphics.drawable.Drawable;

public class Application {
    private String name;
    private String packageName;
    private String packageId;
    Drawable icon;

    public Application(String name, Drawable icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String getPackageName() {
        return packageName;
    }

    public Application setPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public String getPackageId() {
        return packageId;
    }

    public Application setPackageId(String packageId) {
        this.packageId = packageId;
        return this;
    }
}
