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

import java.util.ArrayList;

public class MenuAdapter extends ArrayAdapter {
    ArrayList<String> menu = new ArrayList<>();
    LayoutInflater inflater;

    public MenuAdapter(@NonNull Context context, int textViewResourceId, @NonNull ArrayList objects) {
        super(context, textViewResourceId, objects);
        menu = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        this.inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = this.inflater.inflate(R.layout.menu_grid_view, null);

        TextView name = (TextView) view.findViewById(R.id.name);

        name.setText(menu.get(position).toString());

        return view;
    }
}
