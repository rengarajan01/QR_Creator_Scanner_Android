package com.UnlimitedNibble.QuickResponse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Calendar;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "qrdata.db";
    private static String TABLE_NAME = "datainqr";
    private static String COL1 = "MSG_IN_QR";
    private static String COL2 = "DATE_SCANNED";

    int result;

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createtable = " CREATE TABLE " + TABLE_NAME + "( MSG_IN_QR TEXT )";
        sqLiteDatabase.execSQL(createtable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String dror = "DROP TABLE IF EXISTS " + TABLE_NAME;
        onCreate(sqLiteDatabase);

    }

    public boolean AddData(String contents) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        Date date = Calendar.getInstance().getTime();

//        contentValues.put(COL1, String.valueOf(date));
        contentValues.put(COL1, contents);


        result = (int) db.insert(TABLE_NAME,null,contentValues);

        return result != -1;

    }

    Cursor getListContents() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}
