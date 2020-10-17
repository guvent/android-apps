package com.guventopal.basiclauncher.dtos;

import android.graphics.Bitmap;

import java.io.InputStream;

public class Contact {
    private int count = 0;
    private String name = "";
    private String phone = "";
    private String phone2 = "";
    private String phone3 = "";
    private String phone4 = "";
    private String phone5 = "";
    private String phone6 = "";
    private String phone7 = "";
    private String phone8 = "";
    private InputStream image = null;

    public Contact(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void addPhone(String phone) {
        if(this.phone.isEmpty()) { this.phone = phone; this.count++; return; }
        if(this.phone2.isEmpty()) { this.phone2 = phone; this.count++; return; }
        if(this.phone3.isEmpty()) { this.phone3 = phone; this.count++; return; }
        if(this.phone4.isEmpty()) { this.phone4 = phone; this.count++; return; }
        if(this.phone5.isEmpty()) { this.phone5 = phone; this.count++; return; }
        if(this.phone6.isEmpty()) { this.phone6 = phone; this.count++; return; }
        if(this.phone7.isEmpty()) { this.phone7 = phone; this.count++; return; }
        if(this.phone8.isEmpty()) { this.phone8 = phone; this.count++; return; }
        return;
    }

    public int getCount() {
        return count;
    }

    public String[] getAllPhones() {
        String[] phones = new String[this.getCount()];
        if(!this.phone.isEmpty()) { phones[0] = this.phone; }
        if(!this.phone2.isEmpty()) { phones[1] = this.phone2; }
        if(!this.phone3.isEmpty()) { phones[2] = this.phone3; }
        if(!this.phone4.isEmpty()) { phones[3] = this.phone4; }
        if(!this.phone5.isEmpty()) { phones[4] = this.phone5; }
        if(!this.phone6.isEmpty()) { phones[5] = this.phone6; }
        if(!this.phone7.isEmpty()) { phones[6] = this.phone7; }
        if(!this.phone8.isEmpty()) { phones[7] = this.phone8; }
        return phones;
    }

    public boolean checkPhones(String phone) {
        if(this.phone.equals(phone)) { return true; }
        if(this.phone2.equals(phone)) { return true; }
        if(this.phone3.equals(phone)) { return true; }
        if(this.phone4.equals(phone)) { return true; }
        if(this.phone5.equals(phone)) { return true; }
        if(this.phone6.equals(phone)) { return true; }
        if(this.phone7.equals(phone)) { return true; }
        if(this.phone8.equals(phone)) { return true; }
        return false;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }
}
