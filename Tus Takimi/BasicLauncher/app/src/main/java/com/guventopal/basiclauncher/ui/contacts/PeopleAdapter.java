package com.guventopal.basiclauncher.ui.contacts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.guventopal.basiclauncher.R;
import com.guventopal.basiclauncher.dtos.Contact;

import java.io.InputStream;
import java.util.ArrayList;

public class PeopleAdapter extends ArrayAdapter {
    private ArrayList<Contact> people = new ArrayList<Contact>();
    private LayoutInflater inflater;
    private Context ctx;

    public PeopleAdapter(Context context, int textViewRId, ArrayList objects) {
        super(context, textViewRId, objects);
        ctx = context;
        people = objects;
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

        Contact contact = (Contact) people.get(position);
        name.setText(contact.getName());
        phone.setText(contact.getPhone());

        InputStream inputStream = contact.getImage();

        if(inputStream!=null) {
            imageView.setColorFilter(0);
            imageView.setImageBitmap((Bitmap)BitmapFactory.decodeStream(inputStream));
        } else {
            imageView.setImageResource(R.drawable.ic_people_foreground);
        }

        return view;
    }
}
