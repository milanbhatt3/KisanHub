package com.bhatt.milan.kisanhubdemo.database;

import android.provider.BaseColumns;

public final class TemperatureContract {

    private TemperatureContract() {}

    public static class TemperatureEntry implements BaseColumns {
        public static final String TABLE_NAME = "temp_year_data";
        public static final String COLUMN_NAME_COUNTRY = "country";
        public static final String COLUMN_NAME_TEMP_TYPE = "tempType";
        public static final String COLUMN_NAME_YEAR = "year";
        public static final String COLUMN_NAME_JAN = "JAN";
        public static final String COLUMN_NAME_FEB = "FEB";
        public static final String COLUMN_NAME_MAR = "MAR";
        public static final String COLUMN_NAME_APR = "APR";
        public static final String COLUMN_NAME_MAY = "MAY";
        public static final String COLUMN_NAME_JUN = "JUN";
        public static final String COLUMN_NAME_JUL = "JUL";
        public static final String COLUMN_NAME_AUG = "AUG";
        public static final String COLUMN_NAME_SEP = "SEP";
        public static final String COLUMN_NAME_OCT = "OCT";
        public static final String COLUMN_NAME_NOV = "NOV";
        public static final String COLUMN_NAME_DEC = "DEC";
        public static final String COLUMN_NAME_WIN = "WIN";
        public static final String COLUMN_NAME_SPR = "SPR";
        public static final String COLUMN_NAME_SUM = "SUM";
        public static final String COLUMN_NAME_AUT = "AUT";
        public static final String COLUMN_NAME_ANN = "ANN";
    }
}
