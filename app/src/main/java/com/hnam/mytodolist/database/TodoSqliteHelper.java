package com.hnam.mytodolist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by hnam on 10/6/2016.
 */

public class TodoSqliteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "dss.db";
    private static final int DB_VERSION = 1;

    // TODOLIST DEVICE TABLE
    public static final String TODO_TABLE = "TODO";
    public static final String COLUMN_TODO_NAME = "NAME";
    public static final String COLUMN_DUE_TO_DATE = "DUE_TO_DATE";
    public static final String COLUMN_PRIORITY = "PRIORITY";

    private static String CREATE_TODO_TABLE =
            "CREATE TABLE " + TODO_TABLE + "(" +
                    BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TODO_NAME + " TEXT," +
                    COLUMN_DUE_TO_DATE + " TEXT, " +
                    COLUMN_PRIORITY + " INTEGER)";

    public TodoSqliteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TODO_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
