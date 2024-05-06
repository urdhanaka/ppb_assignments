package com.example.assignment6_listview;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.widget.ListView;
import java.util.ArrayList;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;


public class MainActivity extends AppCompatActivity {
    private ListView lv;
    private ImageView add;
    private kontakAdapter kAdapter;
    private SQLiteDatabase dbku;
    private SQLiteOpenHelper dbopen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.listView);
        add = findViewById(R.id.add);
        add.setOnClickListener(operasi);

        ArrayList<kontak> listKontak = new ArrayList<kontak>();
        kAdapter = new kontakAdapter(this,0,listKontak);
        lv.setAdapter(kAdapter);

        dbopen = new SQLiteOpenHelper(this,"kontak.db",null,1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
            }
            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
        };

        dbku = dbopen.getWritableDatabase();
        dbku.execSQL("create table if not exists kontak(nama TEXT, nohp TEXT);");
        ambildata();
    }

    View.OnClickListener operasi= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.add) {
                tambah_data();
            }
        }
    };

    private void insertKontak(String nm, String hp)
    {
        kontak newKontak = new kontak(nm,hp);
        kAdapter.add(newKontak);
    }

    @SuppressLint("Range")
    private void ambildata()
    {
        Cursor cur = dbku.rawQuery("select * from kontak",null);
        Toast.makeText(this,"Terdapat sejumlah " + cur.getCount(),
                Toast.LENGTH_LONG).show();
        int i=0;if(cur.getCount() > 0) cur.moveToFirst();
        while(i<cur.getCount())
        {
            insertKontak(cur.getString(cur.getColumnIndex("nama")),
                    cur.getString(cur.getColumnIndex("nohp")));
            cur.moveToNext();
            i++;
        }
    }

    private void tambah_data(){
        AlertDialog.Builder buat = new AlertDialog.Builder(this);
        buat.setTitle("Add Kontak");

        View vAdd = LayoutInflater.from(this).inflate(R.layout.add_kontak,null);
        final EditText nm = (EditText) vAdd.findViewById(R.id.nm);
        final EditText hp = (EditText) vAdd.findViewById(R.id.hp);

        buat.setView(vAdd);
        buat.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                insertKontak( nm.getText().toString(),hp.getText().toString());
                Toast.makeText(getBaseContext(),"Data Tersimpan",Toast.LENGTH_LONG).show();
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

    public void deleteKontak(int position) {
        kontak contactToDelete = kAdapter.getItem(position);
        kAdapter.remove(contactToDelete);
        kAdapter.notifyDataSetChanged();

        Toast.makeText(this, "Contact deleted", Toast.LENGTH_SHORT).show();
    }

}