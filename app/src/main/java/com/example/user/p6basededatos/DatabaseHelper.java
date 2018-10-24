package com.example.user.p6basededatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static java.lang.Integer.parseInt;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private  static final String DATABASE_NAME = "contactsManager";
    private static final String TABLE_CONTACTS= "constacts";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone number";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null,DATABASE_VERSION);

    }



    @Override
    public void onCreate(SQLiteDatabase db) {
    String CREATE_CONTACTS_TABLE = "CREATE TABLE "
            + TABLE_CONTACTS +  "(" + KEY_ID +
            " INTEGER PRIMARY KEY," +KEY_NAME+
            " TEXT,"+KEY_PH_NO+"TEXT)";
              db.execSQL(CREATE_CONTACTS_TABLE);
    }

    public void onUpgrade(
    SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_CONTACTS);
        onCreate(db);
    }

    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getPhoneNumber());
        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }
    public Contact getContact(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_ID,KEY_NAME,KEY_PH_NO},KEY_ID + "=?",
                new String[] { String.valueOf(id)},null,null,null,null);
        if(cursor != null)
            cursor.moveToFirst();
        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2));
        return contact;
    }

}
