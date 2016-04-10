package com.alexilyin.android.a41_assetsandgson;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.BaseColumns;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by user on 05.04.16.
 */
public class CacheDBContract {

    // http://kylewbanks.com/blog/Tutorial-Implementing-a-Client-Side-Cache-using-the-SQLite-Database-on-Android-and-SQLiteOpenHelper

    public static final String CACHE_DB = "cache.db";
    public static final int CACHE_DB_VERSION = 1;


    public final static class Asset implements BaseColumns {

        public static final String TABLE_NAME = "Asset";

//        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_ID_TYPE = "INTEGER PRIMARY KEY";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_TITLE_TYPE = "STRING";

        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_IMAGE_TYPE = "BLOB";

        public static final String SQL_CREATE_TABLE = "create table " + TABLE_NAME + " ( " +
                _ID+ " " + COLUMN_ID_TYPE + ", " +
                COLUMN_TITLE + " " + COLUMN_TITLE_TYPE + ", " +
                COLUMN_IMAGE + " " + COLUMN_IMAGE_TYPE + " )";

        public static final String SQL_DROP_TABLE = "drop table if exists " + TABLE_NAME;

        // CRUD

        public static void insert(Context context, JsonObject asset) {

            ContentValues contentValues = assetToContentValues(context, asset);

            CacheDBHelper dbHelper = CacheDBHelper.getInstance(context);

            dbHelper.getWritableDatabase().insert(TABLE_NAME, "null", contentValues);

            dbHelper.close();
        }

        public static Cursor get(Context context) {
            CacheDBHelper dbHelper = CacheDBHelper.getInstance(context);
            return dbHelper.getReadableDatabase().query(TABLE_NAME, null, null, null, null, null, null);
        }

        // Getters

        public static String getTitle(Cursor cursor) {
            int columnIndex = cursor.getColumnIndex(COLUMN_TITLE);
            return cursor.getString(columnIndex);
        }

        public static Bitmap getImage(Cursor cursor) {
            int columnIndex = cursor.getColumnIndex(COLUMN_IMAGE);
            byte[] imageByteArray = cursor.getBlob(columnIndex);
            ByteArrayInputStream imageStream = new ByteArrayInputStream(imageByteArray);
            return BitmapFactory.decodeStream(imageStream);
        }

        // Inner methods

        private static ContentValues assetToContentValues(Context context, JsonObject asset) {
            ContentValues contentValues = new ContentValues();
//            contentValues.put(_ID, asset.jsonId);
            contentValues.put(COLUMN_TITLE, asset.jsonTitle);
            contentValues.put(COLUMN_IMAGE, imageFileToBLOB(context, asset.jsonFilename));
            return contentValues;
        }

        private static byte[] imageFileToBLOB(Context context, String filename) {
            BufferedInputStream bis = null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            try {
                bis = new BufferedInputStream(
                        context.getAssets().open(filename)
                );

                int current;
                while ((current = bis.read()) != -1) {
                    baos.write((byte) current);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return baos.toByteArray();
        }

    }
}
