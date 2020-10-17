package com.guventopal.basiclauncher.ui.applications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.guventopal.basiclauncher.R;
import com.guventopal.basiclauncher.dtos.Application;

import java.util.ArrayList;

public class ApplicationAdapter extends ArrayAdapter {
    ArrayList<Application> applications = new ArrayList<Application>();
    LayoutInflater inflater;

    public ApplicationAdapter(Context context, int textViewRId, ArrayList objects) {
        super(context, textViewRId, objects);
        applications = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view = convertView;

        this.inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = this.inflater.inflate(R.layout.apps_grid_view, null);

        TextView name = (TextView) view.findViewById(R.id.name);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);

        Application app = (Application) applications.get(position);
        name.setText(app.getName());
        imageView.setColorFilter(0);
        imageView.setImageDrawable(app.getIcon());

        return view;
    }
}
