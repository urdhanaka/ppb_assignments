package com.example.assignment6_listview;

import android.content.DialogInterface;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.content.Context;
import android.view.ViewGroup;
import android.app.AlertDialog;

import java.util.List;

public class kontakAdapter extends ArrayAdapter<kontak> {
    // View lookup cache
    private static class ViewHolder {
        TextView nama;
        TextView nohp;
    }

    public kontakAdapter(Context context, int resource, List<kontak> objects) {
        super(context, resource, objects);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        kontak dtkontak = getItem(position);
        ViewHolder viewKontak; // view lookup cache stored in tag
        if (convertView == null) {
            viewKontak = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.item_user, parent, false);
            viewKontak.nama = (TextView) convertView.findViewById(R.id.nm);
            viewKontak.nohp = (TextView) convertView.findViewById(R.id.hp);
            convertView.setTag(viewKontak);

        } else {
            viewKontak = (ViewHolder) convertView.getTag();
        }

        assert dtkontak != null;
        viewKontak.nama.setText(dtkontak.getNama());
        viewKontak.nohp.setText(dtkontak.getNoHp());

        ImageView edit = (ImageView) convertView.findViewById(R.id.edit);
        ImageView del = (ImageView) convertView.findViewById(R.id.del);


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final kontak contactToUpdate = getItem(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Edit Contact");

                View editContactView = LayoutInflater.from(getContext()).inflate(R.layout.add_kontak, null);
                builder.setView(editContactView);

                final EditText editName = editContactView.findViewById(R.id.nm);
                final EditText editPhone = editContactView.findViewById(R.id.hp);

                editName.setText(contactToUpdate.getNama());
                editPhone.setText(contactToUpdate.getNoHp());

                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newName = editName.getText().toString();
                        String newPhone = editPhone.getText().toString();

                        contactToUpdate.setNama(newName);
                        contactToUpdate.setNoHp(newPhone);

                        notifyDataSetChanged();

                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();
            }
        });


        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call a function in KontakActivity to delete the contact
                ((MainActivity) getContext()).deleteKontak(position);
            }
        });

        return convertView;
    }
}