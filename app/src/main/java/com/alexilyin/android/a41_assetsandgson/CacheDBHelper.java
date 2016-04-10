package com.alexilyin.android.a41_assetsandgson;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

/**
 * Created by user on 05.04.16.
 */
public class CacheDBHelper extends SQLiteOpenHelper {

    private static CacheDBHelper instance;

    private CacheDBHelper(Context context) {
        super(context, CacheDBContract.CACHE_DB, null, CacheDBContract.CACHE_DB_VERSION);
    }

    public static CacheDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new CacheDBHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CacheDBContract.Asset.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CacheDBContract.Asset.SQL_DROP_TABLE);
        onCreate(db);
    }
}
