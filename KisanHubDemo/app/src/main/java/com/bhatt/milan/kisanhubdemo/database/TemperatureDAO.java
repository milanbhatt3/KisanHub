package com.bhatt.milan.kisanhubdemo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bhatt.milan.kisanhubdemo.R;
import com.bhatt.milan.kisanhubdemo.database.SQLHelper;
import com.bhatt.milan.kisanhubdemo.model.Temperature;

import java.util.ArrayList;
import java.util.List;

import static com.bhatt.milan.kisanhubdemo.database.TemperatureContract.TemperatureEntry;

public class TemperatureDAO {

    private Context context;
    private SQLiteDatabase database;
    private SQLHelper dbHelper;
    private String[] allColumns = {TemperatureEntry.COLUMN_NAME_COUNTRY, TemperatureEntry.COLUMN_NAME_TEMP_TYPE,
                                    TemperatureEntry.COLUMN_NAME_YEAR, TemperatureEntry.COLUMN_NAME_WIN, TemperatureEntry.COLUMN_NAME_SPR,
                                    TemperatureEntry.COLUMN_NAME_SUM, TemperatureEntry.COLUMN_NAME_AUT, TemperatureEntry.COLUMN_NAME_ANN};

    public TemperatureDAO(Context context) {
        this.context = context;
        dbHelper = new SQLHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long addTemperature(Temperature temperature) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TemperatureEntry.COLUMN_NAME_COUNTRY, temperature.getCountry());
        contentValues.put(TemperatureEntry.COLUMN_NAME_TEMP_TYPE, temperature.getTempType());
        contentValues.put(TemperatureEntry.COLUMN_NAME_YEAR, temperature.getYear());
        contentValues.put(TemperatureEntry.COLUMN_NAME_JAN, temperature.getJanuary());
        contentValues.put(TemperatureEntry.COLUMN_NAME_FEB, temperature.getFebruary());
        contentValues.put(TemperatureEntry.COLUMN_NAME_MAR, temperature.getMarch());
        contentValues.put(TemperatureEntry.COLUMN_NAME_APR, temperature.getApril());
        contentValues.put(TemperatureEntry.COLUMN_NAME_MAY, temperature.getMay());
        contentValues.put(TemperatureEntry.COLUMN_NAME_JUN, temperature.getJune());
        contentValues.put(TemperatureEntry.COLUMN_NAME_JUL, temperature.getJuly());
        contentValues.put(TemperatureEntry.COLUMN_NAME_AUG, temperature.getAugust());
        contentValues.put(TemperatureEntry.COLUMN_NAME_SEP, temperature.getSeptember());
        contentValues.put(TemperatureEntry.COLUMN_NAME_OCT, temperature.getOctober());
        contentValues.put(TemperatureEntry.COLUMN_NAME_NOV, temperature.getNovember());
        contentValues.put(TemperatureEntry.COLUMN_NAME_DEC, temperature.getDecember());
        contentValues.put(TemperatureEntry.COLUMN_NAME_DEC, temperature.getDecember());
        contentValues.put(TemperatureEntry.COLUMN_NAME_WIN, temperature.getWinter());
        contentValues.put(TemperatureEntry.COLUMN_NAME_SPR, temperature.getSpring());
        contentValues.put(TemperatureEntry.COLUMN_NAME_SUM, temperature.getSummer());
        contentValues.put(TemperatureEntry.COLUMN_NAME_AUT, temperature.getAutumn());
        contentValues.put(TemperatureEntry.COLUMN_NAME_ANN, temperature.getAnnual());
        return database.insert(TemperatureEntry.TABLE_NAME,null,contentValues);
    }

    public List<Temperature> getTemperatureBaseOnAllCriteria(String region, String parameter, int limit) {

        List<Temperature> temperatureList = new ArrayList<>();
        String paramSelected = getParameter(parameter);

        String selectionAll = "country =? AND tempType =?";

        Cursor cursor = database.query(
                TemperatureEntry.TABLE_NAME,
                allColumns,
                selectionAll,
                new String[]{ region, paramSelected},
                null,
                null,
                null,
                String.valueOf(limit));

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Temperature temperature = cursorToTemperature(cursor);
            temperatureList.add(temperature);
            cursor.moveToNext();
        }

        cursor.close();
        return temperatureList;
    }

    private String getParameter(String parameter) {
        if (parameter.equalsIgnoreCase(context.getString(R.string.max_temp))) {
            return context.getString(R.string.tmax);
        } else if (parameter.equalsIgnoreCase(context.getString(R.string.min_temp))) {
            return context.getString(R.string.tmin);
        } else if (parameter.equalsIgnoreCase(context.getString(R.string.mean_temp))) {
            return context.getString(R.string.tmean);
        }
        return parameter;
    }

    private Temperature cursorToTemperature(Cursor cursor) {
        Temperature temperature = new Temperature();
        temperature.setCountry(cursor.getString(cursor.getColumnIndex(TemperatureEntry.COLUMN_NAME_COUNTRY)));
        temperature.setTempType(cursor.getString(cursor.getColumnIndex(TemperatureEntry.COLUMN_NAME_TEMP_TYPE)));
        temperature.setYear(cursor.getString(cursor.getColumnIndex(TemperatureEntry.COLUMN_NAME_YEAR)));
        temperature.setWinter(cursor.getString(cursor.getColumnIndex(TemperatureEntry.COLUMN_NAME_WIN)));
        temperature.setSpring(cursor.getString(cursor.getColumnIndex(TemperatureEntry.COLUMN_NAME_SPR)));
        temperature.setSummer(cursor.getString(cursor.getColumnIndex(TemperatureEntry.COLUMN_NAME_SUM)));
        temperature.setAutumn(cursor.getString(cursor.getColumnIndex(TemperatureEntry.COLUMN_NAME_AUT)));
        temperature.setAnnual(cursor.getString(cursor.getColumnIndex(TemperatureEntry.COLUMN_NAME_ANN)));
        return temperature;
    }
}
