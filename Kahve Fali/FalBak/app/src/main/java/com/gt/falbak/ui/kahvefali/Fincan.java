package com.gt.falbak.ui.kahvefali;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gt.falbak.R;
import com.gt.falbak.ui.KahveFali;

public class Fincan extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    private TextView photo_title;
    private TextView photo_text;
    private ImageView fincan_1;
    private ImageView fincan_2;
    private ImageView fincan_3;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private int photos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fincan);
        (getWindow().getDecorView()).setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        photo_title = findViewById(R.id.photo_title);

        photo_text = findViewById(R.id.photo_text);
        String description = this.get_photo_text(photos);
        photo_text.setText(description);

        Button foto_al = findViewById(R.id.foto_al);
        Button temizle = findViewById(R.id.temizle);
        Button gonder = findViewById(R.id.gonder);

        this.fincan_1 = (ImageView)this.findViewById(R.id.fincan_1);
        this.fincan_2 = (ImageView)this.findViewById(R.id.fincan_2);
        this.fincan_3 = (ImageView)this.findViewById(R.id.fincan_3);

        is_done(false);

        foto_al.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });

        temizle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear_all();
            }
        });

        gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, KahveFali.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                //Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this,
                        "Kamera Yetkisi Vermelsiniz - Ayarlar > Uygulamalar > İzinler",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            this.photos += 1;

            switch (this.photos) {
                case 1:
                    fincan_1.setImageBitmap(photo);
                    break;
                case 2:
                    fincan_2.setImageBitmap(photo);
                    break;
                case 3:
                    fincan_3.setImageBitmap(photo);
                    break;
            }

            String description = this.get_photo_text(photos);
            photo_text.setText(description);
        }
    }

    public String get_photo_text(int quantity) {
        switch (quantity) {
            case 1:
                return "2 Adet Kaldı";
            case 2:
                return "1 Adet Kaldı";
            case 3:
                is_done(true);
                return "Fotoğraflar Hazır";
            default:
                return "Hiç Fotoğraf Vermediniz";
        }
    }

    public void is_done(boolean yes) {
        LinearLayout fotocek = findViewById(R.id.fotocek);
        LinearLayout sonuc = findViewById(R.id.sonuc);

        ViewGroup.LayoutParams fotocek_params = fotocek.getLayoutParams();
        ViewGroup.LayoutParams sonuc_params = sonuc.getLayoutParams();


        if(yes == true) {
            fotocek_params.height = 0;
            fotocek_params.width = 0;
            fotocek.setLayoutParams(fotocek_params);
            fotocek.setVisibility(View.INVISIBLE);

            sonuc_params.height = LinearLayout.LayoutParams.MATCH_PARENT;
            sonuc_params.width = LinearLayout.LayoutParams.MATCH_PARENT;
            sonuc.setLayoutParams(sonuc_params);
            sonuc.setVisibility(View.VISIBLE);

            photo_title.setText(getText(R.string.foto_gonder));
        } else {
            fotocek_params.height = LinearLayout.LayoutParams.MATCH_PARENT;
            fotocek_params.width = LinearLayout.LayoutParams.MATCH_PARENT;
            fotocek.setLayoutParams(fotocek_params);
            fotocek.setVisibility(View.VISIBLE);

            sonuc_params.height = 0;
            sonuc_params.width = 0;
            sonuc.setLayoutParams(sonuc_params);
            sonuc.setVisibility(View.INVISIBLE);

            photo_title.setText(getText(R.string.foto_cek));
        }
    }

    public void clear_all() {
        this.photos = 0;
        this.is_done(false);

        this.fincan_1.setImageResource(R.drawable.cofee_cup_h);
        this.fincan_2.setImageResource(R.drawable.cofee_cup_h);
        this.fincan_3.setImageResource(R.drawable.cofee_cup_h);

        String description = this.get_photo_text(photos);
        this.photo_text.setText(description);
    }

    public void finish() {
        Intent intent = new Intent(this, Waiting.class);
        startActivity(intent);
    }
}