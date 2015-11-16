package com.basic.ronandy.listcountry;

/**
 * Created by Familia on 28/10/2015.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

    public SQLiteDatabase DB;
    public String DBPath;
    public static String DBName = "sample1";
    public static final int version = '1';
    public static Context currentContext;
    public static String tableName = "Country";

    public DBHelper(Context context) {
        super(context, DBName, null, version);
        currentContext = context;
        DBPath = "/data/data/" + context.getPackageName() + "/databases";
        createDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
// TODO Auto-generated method stub

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// TODO Auto-generated method stub

    }

    private void createDatabase() {
            DB = currentContext.openOrCreateDatabase(DBName, 0, null);
            DB.execSQL("CREATE TABLE IF NOT EXISTS " +
                    tableName +
                    " (Nombre VARCHAR, Capital VARCHAR, Superficie INT);");
            DB.execSQL("INSERT INTO " +
                    tableName +
                    " Values ('Brasil','Brasilia',8514877);");

            DB.execSQL("INSERT INTO " +
                    tableName +
                    " Values ('Argentina','Buenos Aires',2780400);");
            DB.execSQL("INSERT INTO " +
                    tableName +
                    " Values ('Perú','Lima',1285216);");
            DB.execSQL("INSERT INTO " +
                    tableName +
                    " Values ('Colombia','Bogota',1141748);");
            DB.execSQL("INSERT INTO " +
                    tableName +
                    " Values ('Bolivia','Sucre',1098581);");
            DB.execSQL("INSERT INTO " +
                    tableName +
                    " Values ('Venezuela','Caracas',916445);");
            DB.execSQL("INSERT INTO " +
                    tableName +
                    " Values ('Chile','Santiago',756102);");
            DB.execSQL("INSERT INTO " +
                    tableName +
                    " Values ('Paraguay','Asunción',406752);");
            DB.execSQL("INSERT INTO " +
                    tableName +
                    " Values ('Ecuador','Quito',283561);");
            DB.execSQL("INSERT INTO " +
                    tableName +
                    " Values ('Uruguay','Montevideo',176215);");
    }
}