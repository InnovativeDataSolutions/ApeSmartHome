package com.ids.smarthomev2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Zaid on 8/3/17.
 */
public class Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "smarthome.db";

    public static final String table = "homeinfo";
    public static final String table2 = "controllerinfo";
    public static final String table3 = "cntrlstatus";

    //Table Homeinfo :[
    public static final String col1 = "homeid";
    public static final String col2 = "username";
    public static final String col3 = "gateway";
    public static final String col4 = "ip";
    public static final String col5 = "physicalid";
    public static final String col6 = "powerlineid";
    public static final String col7 = "name";
    public static final String col8 = "area";
    public static final String col9 = "devicemodel";
    public static final String col10 = "devicecode";
    public static final String col11 = "commandid";
    public static final String col12 = "masterid";
    //]

    //Table Controller : [
    public static final String colfk = "pidfk";
    public static final String col13 = "controllerid";
    public static final String col14 = "internalid";
    public static final String col15 = "controllername";
    public static final String col16 = "controllertype";
    public static final String col17 = "controllerstatus";
    //]

    //Table CntrlStatus :[
    public static final String col18 = "pidcs";
    public static final String col19 = "model";
    public static final String col20 = "switch";
    //]


    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_String2 = "CREATE TABLE " + table + "(" + col1 + " TEXT," + col2 + " TEXT," + col3 + " TEXT," + col4 + " TEXT," + col5 + " TEXT," + col6 + " TEXT," + col7 + " TEXT," + col8 + " TEXT," + col9 + " TEXT," + col10 + " TEXT," + col11 + " TEXT," + col12 + " TEXT" + ")";
        String SQL_String = "CREATE TABLE " + table2 + "(" + colfk + " TEXT," + col13 + " TEXT," + col14 + " TEXT," + col15 + " TEXT," + col16 + " TEXT," + col17 + " TEXT" + ")";
        String SQL_String3 = "CREATE TABLE " + table3 + "(" + col18 + " TEXT," + col19 + " TEXT," + col20 + " TEXT" +")";
        db.execSQL(SQL_String);
        db.execSQL(SQL_String2);
        db.execSQL(SQL_String3);
    }

    //String SQL_String2 = "CREATE TABLE " + table2 + "(" + col1ui + " TEXT" + col2ui + " TEXT" + col3ui + " TEXT" + col4ui + " TEXT" + col5ui + " TEXT" + col6ui + " TEXT" + col7ui + " TEXT" + ")";

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + table);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS" + table2);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS" + table3);
        onCreate(db);

    }

    public long check2(){
        SQLiteDatabase db = this.getReadableDatabase();
        long cnt  = DatabaseUtils.queryNumEntries(db, table);
        db.close();
        return cnt;

    }

    public Cursor getDevice(String area,SQLiteDatabase sqLiteDatabase){
        String [] devicename = {col1,col2,col3,col4,col5,col6,col7,col8,col9,col10,col11,col12};
        String selection = col8+" LIKE ?";
        String [] selectionargs = {area};

        Cursor cursor = sqLiteDatabase.query(table,devicename,selection,selectionargs,null,null,null);
        return cursor;
    }

    public Cursor getdevmodel(String powerln,SQLiteDatabase sqLiteDatabase){
        String [] devicename = {col1,col2,col3,col4,col5,col6,col7,col8,col9,col10,col11,col12};
        String selection = col6+" LIKE ?";
        String [] selectionargs = {powerln};

        Cursor cursor = sqLiteDatabase.query(table,devicename,selection,selectionargs,null,null,null);
        return cursor;
    }

    public Cursor getController(String pid,SQLiteDatabase sqLiteDatabase){
        String [] devicename = {colfk,col13,col14,col15,col16,col17};
        String selection = colfk+" LIKE ?";
        String [] selectionargs = {pid};

        Cursor cursor = sqLiteDatabase.query(table2,devicename,selection,selectionargs,null,null,null);
        return cursor;
    }

    public Cursor getDevModels(String model,SQLiteDatabase sqLiteDatabase){
        String [] devicename = {col1,col2,col3,col4,col5,col6,col7,col8,col9,col10,col11,col12};
        String selection = col9+" LIKE ?";
        String [] selectionargs = {model};

        Cursor cursor = sqLiteDatabase.query(table,devicename,selection,selectionargs,null,null,null);
        return cursor;
    }

    public Cursor getCntrlstatus(String pid,SQLiteDatabase sqLiteDatabase){
        String [] devicename = {col18,col19,col20};
        String selection = col18+" LIKE ?";
        String [] selectionargs = {pid};

        Cursor cursor = sqLiteDatabase.query(table3,devicename,selection,selectionargs,null,null,null);
        return cursor;
    }

    public void deleteCntrlstatus(String pid,SQLiteDatabase sqLiteDatabase){
        String selection = col18+" LIKE ?";
        String [] selectionargs = {pid};

        sqLiteDatabase.delete(table3,selection,selectionargs);
    }

    public long countdevsts(){
        SQLiteDatabase db = this.getReadableDatabase();
        long cnt  = DatabaseUtils.queryNumEntries(db, table3);
        db.close();
        return cnt;

    }


    public boolean inserthomeinfo(String homeid,String username,String gateway,String ipadd,String physicalid,String powerlineid,String name,String area,String modelname,String devicecode,String commandid,String masterid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col1, homeid);
        contentValues.put(col2, username);
        contentValues.put(col3, gateway);
        contentValues.put(col4, ipadd);
        contentValues.put(col5, physicalid);
        contentValues.put(col6, powerlineid);
        contentValues.put(col7, name);
        contentValues.put(col8, area);
        contentValues.put(col9, modelname);
        contentValues.put(col10, devicecode);
        contentValues.put(col11, commandid);
        contentValues.put(col12, masterid);
        Long result = db.insert(table, null, contentValues);

        if (result == -1){
            return false;
        }
        else{
            return true;
        }

    }

        public boolean insertcontrollerinfo(String pidfk,String controllerid,String internalid,String controllername,String controllertype,String controllerstatus){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
            contentValues.put(colfk, pidfk);
            contentValues.put(col13, controllerid);
            contentValues.put(col14, internalid);
            contentValues.put(col15, controllername);
            contentValues.put(col16, controllertype);
            contentValues.put(col17, controllerstatus);
        Long result = db.insert(table2, null, contentValues);

        if (result == -1){
            return false;
        }
        else{
            return true;
        }

    }

    public boolean insertcontrollerstatus(String pidcs,String model,String switchid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col18, pidcs);
        contentValues.put(col19, model);
        contentValues.put(col20, switchid);
        Long result = db.insert(table3, null, contentValues);

        if (result == -1){
            return false;
        }
        else{
            return true;
        }

    }

    public boolean deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ table);
        db.execSQL("delete from "+ table2);
        db.execSQL("delete from "+ table3);
        db.close();
        return true;
    }

    public boolean deletecurrentinfo()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ table);
        db.execSQL("delete from "+ table2);
        db.close();
        return true;
    }



    public Cursor gethomeinfo() {
        Cursor cursor = null;
        String empName = "";
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM homeinfo", null);

            return cursor;
        }finally {

        }
    }

    public Cursor getcontrollerinfo() {
        Cursor cursor = null;
        String empName = "";
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM controllerinfo", null);

            return cursor;
        }finally {

        }
    }
}

