package com.example.arnold.partyfi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Verbal on 03/12/2014.
 */
public class PDBAdapter
{
    // Logcat tag
    private static final String LOG = "DatabaseHelp";

    // Database Version
    private static final int DATABASE_VERSION = 5;

    // Database Name
    private static final String DATABASE_NAME = "partyManager";
    //Table Names
    private static final String TABLE_PARTY = "party";
    private static final String TABLE_ACCOUNT = "account";
    // Common column names
    private static final String KEY_ID = "_id";
    // PARTY Table - column names
    private static final String KEY_LAT = "lat";
    private static final String KEY_LNG = "lng";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESC = "description";
    private static final String KEY_ADDR = "address";

    // ACCOUNT Table - column names
    private static final String KEY_UNAME = "uname";
    private static final String KEY_PWORD = "pword";

    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    // Table Create Statements
    // Party table create statement
    static final String CREATE_TABLE_PARTY = "CREATE TABLE "
            + TABLE_PARTY + "( _id integer primary key autoincrement," + KEY_LAT
            + " DOUBLE," + KEY_LNG + " DOUBLE," + KEY_TITLE
            + " TEXT," + KEY_DESC + " TEXT," + KEY_ADDR + " TEXT" + ");";

    // Account table create statement
    static final String CREATE_TABLE_ACCOUNT = "CREATE TABLE "
            + TABLE_ACCOUNT + "( _id INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_UNAME
            + " TET," + KEY_PWORD + " TEXT" + ");";

    public PDBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    public class DatabaseHelper extends SQLiteOpenHelper
    {

        public DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {

                        // creating required tables
            db.execSQL(CREATE_TABLE_PARTY);
            db.execSQL(CREATE_TABLE_ACCOUNT);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            // on upgrade drop older tables
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTY);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);

            // create new tables
            onCreate(db);
        }
    }
    public long createParty(Party party)
    {
        ContentValues values = new ContentValues();
        values.put(KEY_LAT, party.lat);
        values.put(KEY_LNG, party.lng);
        values.put(KEY_TITLE, party.title);
        values.put(KEY_DESC, party.description);
        values.put(KEY_ADDR, party.address);

        long party_id = db.insert(TABLE_PARTY, null, values);

        return party_id;
    }

    public PDBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        DBHelper.close();
    }
    public Party getParty(long party_id)
    {
        //SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_PARTY + " WHERE "
                + KEY_ID + " = " + party_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Party p = new Party();
        p.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        p.setDescription(c.getString(c.getColumnIndex(KEY_DESC)));
        p.setLat(c.getDouble(c.getColumnIndex(KEY_LAT)));
        p.setDescription(c.getString(c.getColumnIndex(KEY_DESC)));

        return p;
    }

    /*
 * getting all parties
 * */
    //public List<Party> getAllParties()
    public Cursor getAllParties()
    {
//        List<Party> parties = new ArrayList<Party>();
//        String selectQuery = "SELECT  * FROM " + TABLE_PARTY;
//
//        Log.e(LOG, selectQuery);
//
//        //SQLiteDatabase db = this.getReadableDatabase();
//        Cursor c = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (c.moveToFirst()) {
//            do {
//                Party p = new Party();
//                p.setId(c.getInt(c.getColumnIndex(KEY_ID)));
//                p.setDescription(c.getString(c.getColumnIndex(KEY_DESC)));
//                p.setLat(c.getDouble(c.getColumnIndex(KEY_LAT)));
//                p.setDescription(c.getString(c.getColumnIndex(KEY_DESC)));
//
//                // adding to party list
//                parties.add(p);
//            } while (c.moveToNext());
//        }
//
//        return parties;
        return db.query(TABLE_PARTY,
                new String[]{KEY_ID, KEY_LAT, KEY_LNG, KEY_DESC, KEY_TITLE},
                null, null, null, null, null);
    }

    public void deleteParty(long party_id)
    {
        //SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PARTY, KEY_ID + " = ?",
                new String[]{String.valueOf(party_id)});
    }

    // closing database


}