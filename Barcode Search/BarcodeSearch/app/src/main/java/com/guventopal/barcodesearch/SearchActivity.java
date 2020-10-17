package com.guventopal.barcodesearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

public class SearchActivity extends AppCompatActivity {
    private Intent webbrowser;
    private Intent mainActivity;
    DatabaseHelper dbHelper;
    public String barcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        webbrowser = new Intent(this, BrowserActivity.class);
        mainActivity = new Intent(this, BarcodeReader.class);

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            barcode = null;
        } else {
            barcode = extras.getString("BARCODE");
        }

        Toast.makeText(this, barcode, Toast.LENGTH_SHORT).show();

        initDatabase();

        search_on(barcode);
    }

    @Override
    public void onBackPressed() {
        startActivity(mainActivity);
    }

    private void search_on(String barcode) {
        String urun = search_on_db(barcode);
        //System.out.println(urun);
//        Toast.makeText(this, urun, Toast.LENGTH_SHORT).show();

        if(urun.equals("")) {
            urun = search_on_web(barcode);
//            Toast.makeText(this, urun, Toast.LENGTH_SHORT).show();
            //System.out.println(urun);
        }

        if(urun.equals("")) {
//            Toast.makeText(this, urun, Toast.LENGTH_SHORT).show();
            urun = barcode;
        }

        Toast.makeText(this, urun,Toast.LENGTH_SHORT).show();
        search_on_google(urun);
    }

    private void search_on_google(String query) {
        webbrowser.putExtra("BARCODE_URL", "https://www.google.com/search?q="+query);
        startActivity(webbrowser);
    }

    private String search_on_web(String barkod) {
        String product = "";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url;
        HttpsURLConnection urlConnection = null;
        try {
            url = new URL("https://barcodesdatabase.org/wp-content/themes/bigdb/lib/barcodedbsearch.php?barcodenumber="+barkod);

            urlConnection = (HttpsURLConnection) url.openConnection();

            urlConnection.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
            //urlConnection.setRequestProperty("accept-encoding", "gzip, deflate, br");
            urlConnection.setRequestProperty("accept-language", "tr-TR,tr;q=0.9,en-US;q=0.8,en;q=0.7");
            urlConnection.setRequestProperty("sec-fetch-dest", "document");
            urlConnection.setRequestProperty("sec-fetch-mode", "navigate");
            urlConnection.setRequestProperty("sec-fetch-site", "none");
            urlConnection.setRequestProperty("sec-fetch-user", "?1");
            urlConnection.setRequestProperty("upgrade-insecure-requests", "1");
            urlConnection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36");

            if(urlConnection.getResponseCode() == 200) {
                Pattern p = Pattern.compile("(\"subtitle\":[.]{0,1})\"(.*?)\"");
                Matcher m;

                InputStream in = urlConnection.getInputStream();
                InputStreamReader isw = new InputStreamReader(in, StandardCharsets.UTF_8);

                BufferedReader bufferedReader = new BufferedReader(isw);
                String line = bufferedReader.readLine();
                //System.out.println(line);
                m = p.matcher(line);

                while (m.find()) {
                    product += m.group();
                }

                product = product.replace("\"", "");
                product = product.replace(":", "");
                product = product.replace("subtitle", "");

//                JsonReader jsonReader = new JsonReader(isw);
//                jsonReader.beginObject();
//                while(jsonReader.hasNext()) {
//                    if(jsonReader.nextName().equals("subtitle")) {
//                        product = jsonReader.nextString();
//                    }
//                }
//                jsonReader.endObject();
//                jsonReader.close();

////
//                int data = isw.read();
//                while (data != -1) {
//                    char current = (char) data;
//                    data = isw.read();
//                    System.out.print(current);
//                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return product;
    }

    private String search_on_db(String barkod) {
        Cursor crs = dbHelper.getDatabase().rawQuery("Select Ad From barcode Where Barkod='"+barkod+"';", null);
        if(crs.getCount()>0) {
            crs.moveToFirst();
            return crs.getString(crs.getColumnIndex("Ad"));
        }
        return "";
    }

    private void initDatabase() {
        dbHelper = new DatabaseHelper(this);
        try {
            dbHelper.createDataBase();
            dbHelper.openDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}