package com.ne4istb.my.dietcounter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ne4istb.my.dietcounter.utils.UtilsHelper;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class WaterRepository extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Ne4istbDietCounterDB2";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "water";
    public static final String KEY_ID = "id";
    public static final String KEY_VOLUME = "volume";
    public static final String KEY_DATE = "date";
    public static final String KEY_SHOWFLAG = "showFlag";

    private static final String[] COLUMNS = {KEY_ID, KEY_DATE, KEY_VOLUME, KEY_SHOWFLAG};

    public WaterRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        UtilsHelper.println("Water. Create");

        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_DATE + " INTEGER, " +
                KEY_VOLUME + " REAL, " +
                KEY_SHOWFLAG + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        UtilsHelper.println("Water. Old: " + oldVersion + ", new: " + newVersion);


        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void add(Float volume) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_DATE, new Date().getTime());
        values.put(KEY_VOLUME, volume);
        values.put(KEY_SHOWFLAG, true);

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    public List<Float> getUsedWater() {

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + KEY_VOLUME +
                " FROM " + TABLE_NAME +
                " WHERE " + KEY_SHOWFLAG + "=?";

        String[] selectionArgs = {"1"};
        Cursor cursor = db.rawQuery(query, selectionArgs);

        List<Float> waterVolumes = new LinkedList<Float>();

        if (cursor.moveToFirst()) {
            do {
                float volume = cursor.getFloat(0);
                waterVolumes.add(volume);
            } while (cursor.moveToNext());
        }

        db.close();

        return waterVolumes;
    }

    public void resetCurrentState() {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SHOWFLAG, false);

        String whereClause = KEY_SHOWFLAG + "=?";
        String[] whereArgs = {"1"};

        db.update(TABLE_NAME, values, whereClause, whereArgs);
    }
}
