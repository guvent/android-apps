package com.guventopal.basiclauncher.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> handset;

    public HomeViewModel() {
        handset = new MutableLiveData<>();
        handset.setValue("");
    }

    public LiveData<String> getHandset() {
        return handset;
    }
    public void setText(String text) {
        handset.setValue(text);
    }
    public void appendHandset(String text) {
        String s = handset.getValue();
        s += text;
        handset.setValue(s);
    }
    public void prependHandset() {
        String s = handset.getValue();
        s = s.substring(0, s.length()-1);
        handset.setValue(s);
    }

    public boolean isEmptyHandset() {
        return handset.getValue().isEmpty();
    }
}