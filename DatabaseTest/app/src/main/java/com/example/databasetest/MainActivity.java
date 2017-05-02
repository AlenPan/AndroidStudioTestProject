package com.example.databasetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.view.View.Y;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 3);

        Button createDatabase = (Button) findViewById(R.id.create_database);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });

        Button addData = (Button) findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();

//                // 开始组装第一条数据
//                values.put("name", "The Da Vinci Code");
//                values.put("author", "Dan Brown");
//                values.put("pages", 454);
//                values.put("price", 16.96);
//                db.insert("Book", null, values);
//                values.clear();
//                // 开始组装第二条数据
//                values.put("name", "The Lost Symbol");
//                values.put("author", "Dan Brown");
//                values.put("pages", 510);
//                values.put("price", 19.95);
//                db.insert("Book", null, values);

                // 直接使用SQL操作数据库
                db.execSQL("insert into Book(name, author, pages, price) values(?, ?, ?, ?)",
                        new String[]{"The Da Vinci Code", "Dan Brown", "454", "16.96"});

                Toast.makeText(MainActivity.this, "Insert succeeded", Toast.LENGTH_SHORT).show();
            }
        });

        Button updateData = (Button) findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();

//                ContentValues values = new ContentValues();
//                values.put("price", 10.99);
//                db.update("Book", values, "name = ?", new String[]{"The Da Vinci Code"});

                // 直接使用SQL操作数据库
                db.execSQL("update Book set price = ? where name = ?",
                        new String[]{"10.99", "The Da Vinci Code"});

                Toast.makeText(MainActivity.this, "Update succeeded", Toast.LENGTH_SHORT).show();
            }
        });

        Button deleteData = (Button) findViewById(R.id.delete_data);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();

//                db.delete("Book", "pages > ?", new String[]{"500"});

                // 直接使用SQL操作数据库
                db.execSQL("delete from Book where pages = ?", new String[]{"500"});

                Toast.makeText(MainActivity.this, "Delete succeeded", Toast.LENGTH_SHORT).show();
            }
        });

        Button queryData = (Button) findViewById(R.id.query_data);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();

//                Cursor cursor = db.query("Book", null, null, null, null, null, null);

                // 直接使用SQL操作数据库
                Cursor cursor = db.rawQuery("select * from Book", null);

                if (cursor.moveToFirst()) {
                    do {
                        // 遍历Cursor对象，取出数据并打印
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));

                        Log.d(TAG, "book name is " + name);
                        Log.d(TAG, "book author is " + author);
                        Log.d(TAG, "book pages is " + pages);
                        Log.d(TAG, "book price is " + price);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
        });
    }
}
