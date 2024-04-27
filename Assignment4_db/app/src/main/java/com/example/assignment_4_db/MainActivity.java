package com.example.assignment_4_db;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText name, id;
    private Button save, search;
    private SQLiteDatabase dbku;
    private SQLiteOpenHelper Opendb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // variable
        id = (EditText) findViewById(R.id.idNumberInputField);
        name = (EditText) findViewById(R.id.nameInputField);
        save = (Button) findViewById(R.id.saveButton);
        search = (Button) findViewById(R.id.searchButton);
        save.setOnClickListener(Operation);
        search.setOnClickListener(Operation);

        Opendb = new SQLiteOpenHelper(this, "db.sql", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };

        dbku = Opendb.getWritableDatabase();
        dbku.execSQL("create table if not exists mhs(nrp TEXT, nama TEXT);");

        // action bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    protected void onStop() {
        dbku.close();
        Opendb.close();
        super.onStop();
    }

    View.OnClickListener Operation = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.saveButton) {
                saveOperation();
            } else if (v.getId() == R.id.searchButton) {
                searchOperation();
            }
        }
    };

    private void saveOperation() {
        ContentValues data = new ContentValues();

        data.put("nama", name.getText().toString());
        data.put("nrp", id.getText().toString());
        dbku.insert("mhs", null, data);
        Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
    }

    private void searchOperation() {
        Cursor cur = dbku.rawQuery("SELECT * FROM mhs WHERE nrp='" + id.getText().toString() + "'", null);

        if (cur.getCount() > 0) {
            Toast.makeText(this, "Data ditemukan sejumlah " + cur.getCount(), Toast.LENGTH_SHORT).show();
            cur.moveToFirst();
            name.setText(cur.getString(cur.getColumnIndexOrThrow("nama")));
        } else {
            Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
        }

    }

    private void updateOperation() {
        ContentValues data = new ContentValues();

        data.put("nrp", id.getText().toString());
        data.put("nama", name.getText().toString());
        dbku.update("mhs", data, "nrp = '" + id.getText().toString() + "'", null);
        Toast.makeText(this, "Data berhasil diubah", Toast.LENGTH_SHORT).show();
    }

    private void deleteOpration() {
        dbku.delete("mhs", "nrp = '" + id.getText().toString() + "'", null);
        Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
    }
}
