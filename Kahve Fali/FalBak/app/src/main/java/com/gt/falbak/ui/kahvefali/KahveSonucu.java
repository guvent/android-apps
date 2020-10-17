package com.gt.falbak.ui.kahvefali;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gt.falbak.MainActivity;
import com.gt.falbak.Notifications;
import com.gt.falbak.R;
import com.gt.falbak.adapter.ResultAdapter;
import com.gt.falbak.dtos.Sonuclar;
import com.gt.falbak.ui.KahveFali;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class KahveSonucu extends AppCompatActivity {
    Notifications notifications;
    ArrayList<Sonuclar> sonuclar;
    ArrayList<Sonuclar> veriler;
    ListView sonucListesi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kahve_sonucu);
        (getWindow().getDecorView()).setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        notifications = MainActivity.notifications;

        notifications.removeNotification();

        sonuclar = new ArrayList<>();
        veriler = new ArrayList<>();

        this.ikinci_sonuc(10);
        this.ilk_sonuc(15);
        this.sonuc_filtrele(random_ab(2,4));
        this.ucuncu_sonuc(0, true);


        sonucListesi = (ListView) findViewById(R.id.sonuclar);
        ResultAdapter resultAdapter = new ResultAdapter(getApplicationContext(), R.id.sonuclar, veriler);
        sonucListesi.setAdapter(resultAdapter);

        sonucListesi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sonuc_goster(veriler.get(i));
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, KahveFali.class);
        startActivity(intent);
    }

    public void sonuc_goster(Sonuclar sonuc) {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage(sonuc.getText());
        dlgAlert.setTitle(sonuc.getTitle());
        dlgAlert.setPositiveButton("OKUDUM", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    public void sonuc_filtrele(int adet) {
        for (int i=0; i<sonuclar.size(); i++) {
            if(i<=0) {
                veriler.add(sonuclar.get(i));
            } else {
                String a = ((Sonuclar) sonuclar.get(i)).getTitle();
                for (int j = 0; j <veriler.size(); j++) {
                    String b = ((Sonuclar) veriler.get(j)).getTitle();

                    if (!a.toLowerCase().trim().equals(b.trim().toLowerCase())) {
                        veriler.add(sonuclar.get(i));
                        adet--;
                        break;
                    }
                }
            }
            if(adet<=1) break;
        }

//
//        int count = adet;
//
//        for (int i=0; i<count; i++) {
//            if(count>=adet) {
//                veriler.add(sonuclar.get(i));
//            } else {
//                for (int j = 0; j <veriler.size(); j++) {
//                    String a = ((Sonuclar) sonuclar.get(i)).getTitle();
//                    String b = ((Sonuclar) veriler.get(j)).getTitle();
//                    if (!a.toLowerCase().trim().equals(b.trim().toLowerCase())) {
//                        veriler.add(sonuclar.get(i));
//                        count--;
//                    }
//                }
//            }
//
//            if(count<=0) break;
//        }
    }

    public void ilk_sonuc(int adet) {
        InputStream inputStream = getResources().openRawResource(R.raw.fal_anlam); // 906
        JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        int index = adet;
        int position = 0;
        int positions[] = new int[]{
                ((Double) (Math.random()*406) ).intValue()
        };

        try {
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                String title = "";
                String description = "";

                jsonReader.beginObject();

                while (jsonReader.hasNext()) {
                    if(jsonReader.nextName().equals("title")) title = jsonReader.nextString();
                    if(jsonReader.nextName().equals("description")) description = jsonReader.nextString();
                }

                jsonReader.endObject();

                for(int pos : positions) {
                    if(pos == position) {
                        sonuclar.add(new Sonuclar(capitalize(title), description));
                        index--;
                    }
                    if(index<=0) break;
                }
                position++;
            }
            jsonReader.endArray();
            jsonReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ikinci_sonuc(int adet) {
        InputStream inputStream = getResources().openRawResource(R.raw.fal_anlam2); // 275
        JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        int index = adet;
        int position = 0;
        int positions[] = new int[]{
                ((Double) (Math.random()*406) ).intValue(),
                ((Double) (Math.random()*406) ).intValue(),
                ((Double) (Math.random()*406) ).intValue(),
                ((Double) (Math.random()*406) ).intValue()
        };

        try {
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                String title = "";
                String description = "";

                jsonReader.beginObject();

                while (jsonReader.hasNext()) {
                    if(jsonReader.nextName().equals("title")) title = jsonReader.nextString();
                    if(jsonReader.nextName().equals("description")) description = jsonReader.nextString();
                }

                jsonReader.endObject();

                for(int pos : positions) {
                    if(pos == position) {
                        sonuclar.add(new Sonuclar(capitalize(title), description));
                        index--;
                    }
                    if(index<=0) break;
                }
                position++;
            }
            jsonReader.endArray();
            jsonReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ucuncu_sonuc(int adet, boolean mutlak) {
        InputStream inputStream = getResources().openRawResource(R.raw.fal_anlam3); // 406
        JsonReader jsonReader = new JsonReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        int index = adet;
        int position = 0;
        int positions[] = new int[]{
                ((Double) (Math.random()*406) ).intValue(),
                ((Double) (Math.random()*406) ).intValue(),
                ((Double) (Math.random()*406) ).intValue(),
                ((Double) (Math.random()*406) ).intValue()
            };

        try {
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                String title = "";
                String description = "";

                jsonReader.beginObject();

                while (jsonReader.hasNext()) {
                    if(jsonReader.nextName().equals("title")) title = jsonReader.nextString();
                    if(jsonReader.nextName().equals("description")) description = jsonReader.nextString();
                }

                jsonReader.endObject();

                for(int pos : positions) {
                    if(pos == position) {
                        if(mutlak) {
                            veriler.add(new Sonuclar(capitalize(title), description));
                        } else {
                            sonuclar.add(new Sonuclar(capitalize(title), description));
                        }
                        index--;
                    }
                    if(index<=0) break;
                }
                position++;
            }
            jsonReader.endArray();
            jsonReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String capitalize(String tx) {
        return (tx.substring(0,1).toUpperCase()) + (tx.substring(1,tx.length()).toLowerCase());
    }

    public int random_ab(int a, int b) {
        int c = ((Double) (Math.random()*((b+1)-a))).intValue();
        return (c+a);
    }
}