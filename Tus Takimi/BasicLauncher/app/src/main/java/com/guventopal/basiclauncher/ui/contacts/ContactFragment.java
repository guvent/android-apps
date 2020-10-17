package com.guventopal.basiclauncher.ui.contacts;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.guventopal.basiclauncher.MainActivity;
import com.guventopal.basiclauncher.R;


public class ContactFragment extends Fragment {
    private Context applicationContext;
    public static BottomNavigationView navContact;

    private ContactViewModel contactViewModel;
    private View root;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.root = inflater.inflate(R.layout.fragment_contacts, container, false);
        this.applicationContext = MainActivity.getContextOfApplication();

        navContact = root.findViewById(R.id.call_view);

        NavController navController = Navigation.findNavController(root.findViewById(R.id.call_host_fragment));
        NavigationUI.setupWithNavController(navContact, navController);

        return root;
    }


}