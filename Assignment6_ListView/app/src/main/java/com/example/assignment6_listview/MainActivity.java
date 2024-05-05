package com.example.assignment6_listview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ListView lv;
    private ImageView add;
    private contactAdapter adapter;
    private SQLiteDatabase dbku;
    private SQLiteOpenHelper dbopen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // action bar
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        lv = (ListView) findViewById(R.id.mainListView);
        add = (ImageView) findViewById(R.id.addButton);
        add.setOnClickListener(operasi);

        ArrayList<Contact> contactList = new ArrayList<Contact>();
        contactAdapter adapter = new contactAdapter(this, 0, contactList);
        lv.setAdapter(adapter);

        dbopen = new SQLiteOpenHelper(this, "kontak.db", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };

        dbku = dbopen.getWritableDatabase();
        dbku.execSQL("CREATE TABLE IF NOT EXISTS Kontak(name TEXT, number TEXT);");
        ambilData();
    }

    View.OnClickListener operasi = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.addButton) {
                tambahData();
            }
        }
    };

    private void add_item(String nm, String hp) {
        ContentValues data = new ContentValues();
        data.put("name", nm);
        data.put("number", hp);
        dbku.insert("Kontak", null, data);
        Contact newContact = new Contact(nm, hp);
        adapter.add(newContact);
    }

    private void tambahData() {
        AlertDialog.Builder buat = new AlertDialog.Builder(this);
        buat.setTitle("Add Kontak");

        View vAdd = LayoutInflater.from(this).inflate(R.layout.add_contact, null);
        final EditText nm = (EditText) vAdd.findViewById(R.id.nm);
        final EditText hp = (EditText) vAdd.findViewById(R.id.hp);

        buat.setView(vAdd);
        buat.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                add_item(nm.getText().toString(), hp.getText().toString());
                Toast.makeText(getBaseContext(), "Data Tersimpan", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        buat.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        buat.show();
    }

    private void insertKontak(String nm, String hp) {
        Contact newContact = new Contact(nm, hp);
        adapter.add(newContact);
    }

    @SuppressLint("Range")
    private void ambilData() {
        int i = 0;

        Cursor cur = dbku.rawQuery("SELECT * FROM Kontak", null);
        Toast.makeText(this, "Terdapat sejumlah " + cur.getCount(), Toast.LENGTH_SHORT).show();
        if (cur.getCount() > 0)
            cur.moveToFirst();

        while (i < cur.getCount()) {
            insertKontak(cur.getString(cur.getColumnIndex("name")), cur.getString(cur.getColumnIndex("number")));
            cur.moveToNext();
            i++;
        }

        cur.close();
    }
}