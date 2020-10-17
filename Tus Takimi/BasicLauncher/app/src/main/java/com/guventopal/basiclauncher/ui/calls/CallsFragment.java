package com.guventopal.basiclauncher.ui.calls;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import android.widget.AdapterView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.guventopal.basiclauncher.dtos.GetContact;
import com.guventopal.basiclauncher.dtos.Contact;

import com.guventopal.basiclauncher.MainActivity;
import com.guventopal.basiclauncher.R;
import com.guventopal.basiclauncher.ui.contacts.PeopleAdapter;

import java.util.ArrayList;

public class CallsFragment extends Fragment {
    public static final int PERMISSIONS_REQUEST_READ_CONTACTS = 2;
    private BottomNavigationView bottomNavigationView;

    private ArrayList<Contact> contacts;
    private GridView contactView;
    private View root;
    private GetContact getContact;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.root = inflater.inflate(R.layout.fragment_calls, container, false);
        this.bottomNavigationView = MainActivity.getNavViewOfApplication();
        this.getContact = MainActivity.getContactHandle();

        contactView = root.findViewById(R.id.peoples);

        if(showPhoneStatePermission()) {
            contacts = this.getContact.getContactList();
            PeopleAdapter people = new PeopleAdapter(this.getContext(), R.layout.people_grid_view, contacts);
            contactView.setAdapter(people);
        }

        contactView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectContact(contacts.get(i));
            }
        });
        return root;
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_CONTACTS:
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
                showExplanation("Permission Needed", "Rationale", Manifest.permission.READ_CONTACTS, PERMISSIONS_REQUEST_READ_CONTACTS);
            } else {
                Toast.makeText(this.getActivity(), "Permission Denied!\nGo Settings > Applications > Allow Permission", Toast.LENGTH_SHORT).show();
                showExplanation("Permission Needed", "Rationale", Manifest.permission.READ_CONTACTS, PERMISSIONS_REQUEST_READ_CONTACTS);
                requestPermission(Manifest.permission.READ_CONTACTS, PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        } else {
            //Toast.makeText(this.getActivity(), "Permission (already) Granted!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void showExplanation(String title, String message,
                                 final String permission, final int permissionRequestCode) {
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

    private void selectContact(Contact contact) {
        if(contact.getCount()>0) {
            if(contact.getCount() == 1) {
                beginCall(contact.getPhone());
            } else if (contact.getCount() > 1) {
                multiSelectContact(contact);
            }
        }
    }

    private void multiSelectContact(final Contact contact) {
        final String[] numbers = contact.getAllPhones();

        if(contact.getCount()>1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
            builder.setTitle("Hangisi Aranacak?");
            builder.setItems(numbers, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    beginCall(numbers[i]);
                }
            });
            builder.show();
        }
    }

    private void beginCall(String number) {
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + Uri.encode(number)));
        startActivity(callIntent);

        Toast.makeText(this.getActivity(), "Arama Başlatılıyor: " + number, Toast.LENGTH_SHORT).show();
    }
}