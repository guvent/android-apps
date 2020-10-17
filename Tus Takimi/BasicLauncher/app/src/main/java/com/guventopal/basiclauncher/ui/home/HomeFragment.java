package com.guventopal.basiclauncher.ui.home;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.guventopal.basiclauncher.R;
import com.guventopal.basiclauncher.dtos.Contact;

public class HomeFragment extends Fragment {
    public static final int PERMISSIONS_REQUEST_CALL_PHONE = 1;

    private HomeViewModel homeViewModel;

    public TextView screen;
    private Button one;
    private Button two;
    private Button three;
    private Button four;
    private Button five;
    private Button six;
    private Button seven;
    private Button eight;
    private Button nine;
    private Button zero;
    private Button star;
    private Button square;
    private Button call;
    private Button erase;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        screen = root.findViewById(R.id.mainscreen);
        homeViewModel.getHandset().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                screen.setText(s);
            }
        });

        this.showPhoneStatePermission();

        this.one = root.findViewById(R.id.one);
        this.two = root.findViewById(R.id.two);
        this.three = root.findViewById(R.id.three);
        this.four = root.findViewById(R.id.four);
        this.five = root.findViewById(R.id.five);
        this.six = root.findViewById(R.id.six);
        this.seven = root.findViewById(R.id.seven);
        this.eight = root.findViewById(R.id.eight);
        this.nine = root.findViewById(R.id.nine);
        this.zero = root.findViewById(R.id.zero);
        this.star = root.findViewById(R.id.star);
        this.square = root.findViewById(R.id.square);
        this.call = root.findViewById(R.id.call);
        this.erase = root.findViewById(R.id.erase);

        this.makeEvents();

        return root;
    }

    private void makeEvents() {
        this.one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeNumber("1");
            }
        });
        this.two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeNumber("2");
            }
        });
        this.three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeNumber("3");
            }
        });
        this.four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeNumber("4");
            }
        });
        this.five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeNumber("5");
            }
        });
        this.six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeNumber("6");
            }
        });
        this.seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeNumber("7");
            }
        });
        this.eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeNumber("8");
            }
        });
        this.nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeNumber("9");
            }
        });
        this.zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeNumber("0");
            }
        });
        this.star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeNumber("*");
            }
        });
        this.square.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeNumber("#");
            }
        });

        this.erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeNumber("");
            }
        });
        this.erase.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                clearNumber();
                return true;
            }
        });

        this.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beginCall();
            }
        });
        this.call.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                beginDial();
                return true;
            }
        });
    }

    private void changeNumber(String number) {
        if(number.isEmpty()) {
            this.homeViewModel.prependHandset();
        } else {
            this.homeViewModel.appendHandset(number);
        }

        if(this.homeViewModel.isEmptyHandset()) {
            this.erase.setEnabled(false);
            this.call.setEnabled(false);
        } else {
            this.erase.setEnabled(true);
            this.call.setEnabled(true);
        }
    }

    private void clearNumber() {
        this.homeViewModel.setText("");
        this.erase.setEnabled(false);
        this.call.setEnabled(false);
    }

    private void beginCall() {
        String number = this.homeViewModel.getHandset().getValue();

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + Uri.encode(number)));
        startActivity(callIntent);

        Toast.makeText(this.getContext(), "Arama Başlatılıyor: " + number , Toast.LENGTH_LONG).show();
        clearNumber();
    }

    private void beginDial() {
        String number = this.homeViewModel.getHandset().getValue();

        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + Uri.encode(number)));
        startActivity(callIntent);

        Toast.makeText(this.getContext(), "Numara Çevriliyor: " + number , Toast.LENGTH_LONG).show();
        clearNumber();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CALL_PHONE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this.getActivity(), "Permission Granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this.getActivity(), "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private boolean showPhoneStatePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(), Manifest.permission.CALL_PHONE)) {
                showExplanation("Permission Needed", "Rationale", Manifest.permission.CALL_PHONE, PERMISSIONS_REQUEST_CALL_PHONE);
            } else {
                Toast.makeText(this.getActivity(), "Permission Denied!\nGo Settings > Applications > Allow Permission", Toast.LENGTH_SHORT).show();
                showExplanation("Permission Needed", "Rationale", Manifest.permission.CALL_PHONE, PERMISSIONS_REQUEST_CALL_PHONE);
                requestPermission(Manifest.permission.CALL_PHONE, PERMISSIONS_REQUEST_CALL_PHONE);
            }
        } else {
//            Toast.makeText(this.getActivity(), "Permission (already) Granted!", Toast.LENGTH_SHORT).show();
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
}