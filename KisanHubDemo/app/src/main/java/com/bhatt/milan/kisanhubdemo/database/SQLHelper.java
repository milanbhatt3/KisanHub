package com.bhatt.milan.kisanhubdemo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.bhatt.milan.kisanhubdemo.database.TemperatureContract.TemperatureEntry;

public class SQLHelper  extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Temperature.db";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TemperatureEntry.TABLE_NAME + " (" +
                    TemperatureEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TemperatureEntry.COLUMN_NAME_COUNTRY + " TEXT," +
                    TemperatureEntry.COLUMN_NAME_TEMP_TYPE + " TEXT," +
                    TemperatureEntry.COLUMN_NAME_YEAR + " TEXT," +
                    TemperatureEntry.COLUMN_NAME_JAN + " TEXT," +
                    TemperatureEntry.COLUMN_NAME_FEB + " TEXT," +
                    TemperatureEntry.COLUMN_NAME_MAR + " TEXT," +
                    TemperatureEntry.COLUMN_NAME_APR + " TEXT," +
                    TemperatureEntry.COLUMN_NAME_MAY + " TEXT," +
                    TemperatureEntry.COLUMN_NAME_JUN + " TEXT," +
                    TemperatureEntry.COLUMN_NAME_JUL + " TEXT," +
                    TemperatureEntry.COLUMN_NAME_AUG + " TEXT," +
                    TemperatureEntry.COLUMN_NAME_SEP + " TEXT," +
                    TemperatureEntry.COLUMN_NAME_OCT + " TEXT," +
                    TemperatureEntry.COLUMN_NAME_NOV + " TEXT," +
                    TemperatureEntry.COLUMN_NAME_DEC + " TEXT," +
                    TemperatureEntry.COLUMN_NAME_WIN + " TEXT," +
                    TemperatureEntry.COLUMN_NAME_SPR + " TEXT," +
                    TemperatureEntry.COLUMN_NAME_SUM + " TEXT," +
                    TemperatureEntry.COLUMN_NAME_AUT + " TEXT," +
                    TemperatureEntry.COLUMN_NAME_ANN + " TEXT)";

    private static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TemperatureEntry.TABLE_NAME;


    public SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }
}
