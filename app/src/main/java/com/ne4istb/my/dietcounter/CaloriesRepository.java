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

public class CaloriesRepository extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Ne4istbDietCounterDB";
    private static final int DATABASE_VERSION = 3;

    private static final String TABLE_NAME = "calories";
    public static final String KEY_ID = "id";
    public static final String KEY_CARBOHYDRATES = "carbohydrates";
    public static final String KEY_PROTEINS = "proteins";
    public static final String KEY_FAT = "fat";
    public static final String KEY_DATE = "date";
    public static final String KEY_SHOWFLAG = "showFlag";

    private static final String[] COLUMNS = {KEY_ID, KEY_DATE, KEY_CARBOHYDRATES, KEY_PROTEINS, KEY_FAT, KEY_SHOWFLAG};

    public CaloriesRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_DATE + " INTEGER, " +
                KEY_CARBOHYDRATES + " REAL, " +
                KEY_PROTEINS + " REAL, " +
                KEY_FAT + " REAL, " +
                KEY_SHOWFLAG + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        UtilsHelper.println("Calories. Old: " + oldVersion + ", new: " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void add(Float carbohydrates, Float proteins, Float fat) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_DATE, new Date().getTime());
        values.put(KEY_CARBOHYDRATES, carbohydrates);
        values.put(KEY_PROTEINS, proteins);
        values.put(KEY_FAT, fat);
        values.put(KEY_SHOWFLAG, true);

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    public List<Calories> getAllCalories() {

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " +
                KEY_CARBOHYDRATES + ", " + KEY_PROTEINS + ", " + KEY_FAT +
                " FROM " + TABLE_NAME +
                " WHERE " + KEY_SHOWFLAG + "=?";

        String[] selectionArgs = {"1"};
        Cursor cursor = db.rawQuery(query, selectionArgs);

        List<Calories> caloriesList = new LinkedList<Calories>();
        Calories calories;

        if (cursor.moveToFirst()) {
            do {
                float carbohydrates = cursor.getFloat(0);
                float proteins = cursor.getFloat(1);
                float fat = cursor.getFloat(2);

                calories = new Calories(carbohydrates, proteins, fat);

                caloriesList.add(calories);
            } while (cursor.moveToNext());
        }

        db.close();

        return caloriesList;
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
