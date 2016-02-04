package com.app.pointme.pointme.databackend;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by goparties on 29/1/16.
 */
public class PointMeDataBase extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "pointme";
    private static final int DATABASE_VERSION = 1;
    private static PointMeDataBase dbInstance;

    private PointMeDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized PointMeDataBase getInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = new PointMeDataBase(context.getApplicationContext());
        }
        return dbInstance;
    }


    private PointMeDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private PointMeDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
