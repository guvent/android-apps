package com.guventopal.basiclauncher.ui.calls;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.guventopal.basiclauncher.R;
import com.guventopal.basiclauncher.dtos.Contact;
import com.guventopal.basiclauncher.dtos.Missed;

import java.io.InputStream;
import java.util.ArrayList;

public class CallsAdapter extends ArrayAdapter {
    ArrayList<Missed> calls = new ArrayList<Missed>();
    LayoutInflater inflater;

    public CallsAdapter(Context context, int textViewRId, ArrayList objects) {
        super(context, textViewRId, objects);
        calls = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view = convertView;

        this.inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = this.inflater.inflate(R.layout.people_grid_view, null);

        TextView name = (TextView) view.findViewById(R.id.name);
        TextView phone = (TextView) view.findViewById(R.id.phone);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);

        Missed missed = (Missed) calls.get(position);
        name.setText(missed.getName());
        phone.setText(missed.getTime());

        if(missed.getType()=="incoming") {
            imageView.setColorFilter(Color.GREEN);
            imageView.setImageResource(R.drawable.incoming_call);
        }
        if(missed.getType()=="outgoing") {
            imageView.setColorFilter(Color.BLUE);
            imageView.setImageResource(R.drawable.outgoing_call);
        }
        if(missed.getType()=="missing") {
            imageView.setColorFilter(Color.RED);
            imageView.setImageResource(R.drawable.missed_call);
        }
        if(missed.getType()=="reject") {
            imageView.setColorFilter(Color.RED);
            imageView.setImageResource(R.drawable.missed_call);
        }
        if(missed.getType()=="blocked") {
            imageView.setColorFilter(Color.MAGENTA);
            imageView.setImageResource(R.drawable.blocked_call);
        }
        if(missed.getType()=="voicemail") {
            imageView.setColorFilter(Color.CYAN);
            imageView.setImageResource(R.drawable.missed_call);
        }

        return view;
    }
}
