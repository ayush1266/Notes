package org.thakur.ayush.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by win on 13-06-2018.
 */

public class passwordDatabase extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "password_db";

    public passwordDatabase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Password (ID PRIMARY KEY NOT NULL, Pass TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS Password");

        // Create tables again
        onCreate(db);
    }

    //Editing Password
    public long setPassword(String Password) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put("Pass", Password);

        // insert row
        long id = db.insert("Password", null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public String getPass() {
        // get readable database as we are not inserting anything

        String passwordFromDatabase = null;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Password", null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                passwordFromDatabase = cursor.getString(cursor.getColumnIndex("Pass"));
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        return passwordFromDatabase;
    }

    public int getPassCount() {
        String countQuery = "SELECT  * FROM Password" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }
}
