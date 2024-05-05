package com.example.assignment6_listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class contactAdapter extends ArrayAdapter<Contact> {
    private static class ViewHolder {
        TextView name;
        TextView number;
    }

    public contactAdapter(Context context, int resource, List<Contact> objects) {
        super(context, resource, objects);
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Contact dtContact = getItem(position);
        ViewHolder viewContact;
        ImageView img = (ImageView) convertView.findViewById(R.id.img);

        if (convertView == null) {
            viewContact = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);

            viewContact.name = (TextView) convertView.findViewById(R.id.tName);
            viewContact.number = (TextView) convertView.findViewById(R.id.tNumber);

            convertView.setTag(viewContact);
            Button btn = (Button) convertView.findViewById(R.id.btn);
            btn.setTag(position);
            btn.setOnClickListener(op);
        } else {
            viewContact = (ViewHolder) convertView.getTag();
        }

        viewContact.name.setText(dtContact.getName());
        viewContact.number.setText(dtContact.getNumber());

        return convertView;
    }
}
