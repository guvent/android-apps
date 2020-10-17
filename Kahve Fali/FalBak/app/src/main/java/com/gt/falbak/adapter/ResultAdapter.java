package com.gt.falbak.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gt.falbak.R;
import com.gt.falbak.dtos.Sonuclar;

import java.util.ArrayList;

public class ResultAdapter extends ArrayAdapter {
    ArrayList<Sonuclar> sonuclar = new ArrayList<>();
    LayoutInflater inflater;

    public ResultAdapter(@NonNull Context context, int textViewResourceId, @NonNull ArrayList objects) {
        super(context, textViewResourceId, objects);
        sonuclar = objects;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.result_view, null);

        TextView title = view.findViewById(R.id.title);
        TextView context = view.findViewById(R.id.context);

        title.setText(((Sonuclar)sonuclar.get(position)).getTitle());
        context.setText(((Sonuclar)sonuclar.get(position)).getText());

        return view;
    }
}
