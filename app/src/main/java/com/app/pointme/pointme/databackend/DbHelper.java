package com.app.pointme.pointme.databackend;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by goparties on 29/1/16.
 */
public class DbHelper {
    private static DbHelper dataBaseHelper;
    private final Context context;
    private final PointMeDataBase pmDataBase;
    private final SQLiteDatabase sqLiteDatabase;

    private DbHelper(Context context) {
        this.context = context;
        pmDataBase = PointMeDataBase.getInstance(context);
        sqLiteDatabase = pmDataBase.getWritableDatabase();
    }

    public static DbHelper getInstance(Context context) {
        if (dataBaseHelper == null) {
            dataBaseHelper = new DbHelper(context);
        }
        return dataBaseHelper;
    }

}
