package com.example.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.Data.Blog;
import com.example.Data.PageData;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
public class MyDataBase_1 extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mydbone.db";
    public static final int VERSTION_CODE = 1;
    public static final String TABLE_NAME_1= "tn_1";
    public static final String TABLE_NAME_2= "tn_2";
    public static final String TABLE_NAME_3= "tn_3";
    public static final String TABLE_NAME_Timer= "tn_4";

    //catagory table
    public static final String TABLE_NAME_CATAGORY_1= "tn_5";
    public static final String TABLE_NAME_CATAGORY_2= "tn_6";
    public static final String TABLE_NAME_CATAGORY_3= "tn_7";


    public static final String LEFT_DAY_NAME_CAT = "left_day";
    public static final String SEEK_BAR_CAT = "seekbar_p";
    public static final String PERSENTAGE_TAKA = "persentage_taka";


    public static String KEY_ID = "keyid";
    public static final String POSITION_ADAPTER = "adapterp";
    public static final String SEEKBAR = "seek";
    public static final String SIZE = "size";
    public static final String REST_CODE = "rcode";
    public static final String TIME_CODE = "time";


    public MyDataBase_1(Context context) {
        super(context, DATABASE_NAME, null, VERSTION_CODE);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE_1 = "CREATE TABLE " + TABLE_NAME_1+ "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"  + POSITION_ADAPTER + " TEXT,"
                + SEEKBAR + " TEXT," + SIZE + " TEXT ," + REST_CODE + " TEXT " + ")";

        String CREATE_CONTACTS_TABLE_2 = "CREATE TABLE " + TABLE_NAME_2+ "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"  + POSITION_ADAPTER + " TEXT,"
                + SEEKBAR + " TEXT," + SIZE + " TEXT ," + REST_CODE + " TEXT " + ")";

        String CREATE_CONTACTS_TABLE_3 = "CREATE TABLE " + TABLE_NAME_3+ "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"  + POSITION_ADAPTER + " TEXT,"
                + SEEKBAR + " TEXT," + SIZE + " TEXT ," + REST_CODE + " TEXT " + ")";


        //Timer table..
        String CREATE_TIMER_TABLE = "CREATE TABLE " + TABLE_NAME_Timer
                + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                POSITION_ADAPTER  + " TEXT, "+ TIME_CODE + " TEXT " + " ) ";

        //tb from catagory
        String CREATE_TABLE_CAT_1 = " CREATE TABLE "+TABLE_NAME_CATAGORY_1 + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                POSITION_ADAPTER + " TEXT, "+ SEEK_BAR_CAT + " TEXT, "+LEFT_DAY_NAME_CAT + " TEXT, " + PERSENTAGE_TAKA + " TEXT " +")";

        String CREATE_TABLE_CAT_2= " CREATE TABLE "+TABLE_NAME_CATAGORY_2 + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                POSITION_ADAPTER + " TEXT, "+ SEEK_BAR_CAT + " TEXT, "+LEFT_DAY_NAME_CAT + " TEXT, " + PERSENTAGE_TAKA + " TEXT " +")";

        String CREATE_TABLE_CAT_3 = " CREATE TABLE "+TABLE_NAME_CATAGORY_3 + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                POSITION_ADAPTER + " TEXT, "+ SEEK_BAR_CAT + " TEXT, "+LEFT_DAY_NAME_CAT + " TEXT, " + PERSENTAGE_TAKA + " TEXT " +")";



        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE_1);
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE_2);
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE_3);
        sqLiteDatabase.execSQL(CREATE_TIMER_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_CAT_1);
        sqLiteDatabase.execSQL(CREATE_TABLE_CAT_2);
        sqLiteDatabase.execSQL(CREATE_TABLE_CAT_3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_1);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_2);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_3);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_Timer);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_CATAGORY_1);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_CATAGORY_2);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_CATAGORY_3);
        onCreate(sqLiteDatabase);
    }



    //Following Operation..


    public void insertData_catagory_one(PageData pageData){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(POSITION_ADAPTER,pageData.getAdapter_position_cat());
        contentValues.put(SEEK_BAR_CAT,pageData.getCat_progress());
        contentValues.put(LEFT_DAY_NAME_CAT,pageData.getCat_day());
        contentValues.put(PERSENTAGE_TAKA,pageData.getCat_persanag());

        long l = db.insert(TABLE_NAME_CATAGORY_1,null,contentValues);
        if (l != -1)
            Log.d("MMM","Dat into database..");
        else
            Log.d("MMM","Data is not save..");
    }

    public List<PageData> getData_catagory_one(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<PageData> pageDataList = new ArrayList<>();
        pageDataList.clear();

        Cursor cursor = db.query(TABLE_NAME_CATAGORY_1,new String[]{KEY_ID,POSITION_ADAPTER,SEEK_BAR_CAT,LEFT_DAY_NAME_CAT,PERSENTAGE_TAKA},null,null,null,null,null);
        if (cursor != null)
            cursor.moveToFirst();

        do {

            PageData pageData = new PageData();

            pageData.setAdapter_position_cat(Integer.parseInt(cursor.getString(cursor.getColumnIndex(POSITION_ADAPTER))));
            pageData.setCat_progress(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SEEK_BAR_CAT))));
            pageData.setCat_day(cursor.getString(cursor.getColumnIndex(LEFT_DAY_NAME_CAT)));
            pageData.setCat_persanag(cursor.getString(cursor.getColumnIndex(PERSENTAGE_TAKA)));

            pageDataList.add(pageData);

        }while (cursor.moveToNext());

        return pageDataList;
    }


    public boolean isChekDatabaseEmpty_Catagory_two(){
        SQLiteDatabase db =this.getReadableDatabase();
        boolean rowExists;

        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_CATAGORY_2, null);

        if (mCursor.moveToFirst())
        {
            // DO SOMETHING WITH CURSOR
            rowExists = true;

        } else
        {
            // I AM EMPTY
            rowExists = false;
        }

        return rowExists;
    }


    public void insertData_catagory_two(PageData pageData){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(POSITION_ADAPTER,pageData.getAdapter_position_cat());
        contentValues.put(SEEK_BAR_CAT,pageData.getCat_progress());
        contentValues.put(LEFT_DAY_NAME_CAT,pageData.getCat_day());
        contentValues.put(PERSENTAGE_TAKA,pageData.getCat_persanag());

        long l = db.insert(TABLE_NAME_CATAGORY_2,null,contentValues);
        if (l != -1)
            Log.d("MMM","Dat into database..");
        else
            Log.d("MMM","Data is not save..");
    }

    public List<PageData> getData_catagory_two(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<PageData> pageDataList = new ArrayList<>();
        pageDataList.clear();

        Cursor cursor = db.query(TABLE_NAME_CATAGORY_2,new String[]{KEY_ID,POSITION_ADAPTER,SEEK_BAR_CAT,LEFT_DAY_NAME_CAT,PERSENTAGE_TAKA},null,null,null,null,null);
        if (cursor != null)
            cursor.moveToFirst();

        do {

            PageData pageData = new PageData();

            pageData.setAdapter_position_cat(Integer.parseInt(cursor.getString(cursor.getColumnIndex(POSITION_ADAPTER))));
            pageData.setCat_progress(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SEEK_BAR_CAT))));
            pageData.setCat_day(cursor.getString(cursor.getColumnIndex(LEFT_DAY_NAME_CAT)));
            pageData.setCat_persanag(cursor.getString(cursor.getColumnIndex(PERSENTAGE_TAKA)));

            pageDataList.add(pageData);

        }while (cursor.moveToNext());

        return pageDataList;
    }


    public void insertData_catagory_three(PageData pageData){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(POSITION_ADAPTER,pageData.getAdapter_position_cat());
        contentValues.put(SEEK_BAR_CAT,pageData.getCat_progress());
        contentValues.put(LEFT_DAY_NAME_CAT,pageData.getCat_day());
        contentValues.put(PERSENTAGE_TAKA,pageData.getCat_persanag());

        long l = db.insert(TABLE_NAME_CATAGORY_3,null,contentValues);
        if (l != -1)
            Log.d("MMM","Dat into database..");
        else
            Log.d("MMM","Data is not save..");
    }

    public List<PageData> getData_catagory_three(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<PageData> pageDataList = new ArrayList<>();
        pageDataList.clear();

        Cursor cursor = db.query(TABLE_NAME_CATAGORY_3,new String[]{KEY_ID,POSITION_ADAPTER,SEEK_BAR_CAT,LEFT_DAY_NAME_CAT,PERSENTAGE_TAKA},null,null,null,null,null);
        if (cursor != null)
            cursor.moveToFirst();

        do {

            PageData pageData = new PageData();

            pageData.setAdapter_position_cat(Integer.parseInt(cursor.getString(cursor.getColumnIndex(POSITION_ADAPTER))));
            pageData.setCat_progress(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SEEK_BAR_CAT))));
            pageData.setCat_day(cursor.getString(cursor.getColumnIndex(LEFT_DAY_NAME_CAT)));
            pageData.setCat_persanag(cursor.getString(cursor.getColumnIndex(PERSENTAGE_TAKA)));

            pageDataList.add(pageData);

        }while (cursor.moveToNext());

        return pageDataList;
    }


    public boolean isChekDatabaseEmpty_Catagory_three(){
        SQLiteDatabase db =this.getReadableDatabase();
        boolean rowExists;

        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_CATAGORY_3, null);

        if (mCursor.moveToFirst())
        {
            // DO SOMETHING WITH CURSOR
            rowExists = true;

        } else
        {
            // I AM EMPTY
            rowExists = false;
        }

        return rowExists;
    }



    public boolean isChekDatabaseEmpty_Catagory_one(){
        SQLiteDatabase db =this.getReadableDatabase();
        boolean rowExists;

        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_CATAGORY_1, null);

        if (mCursor.moveToFirst())
        {
            // DO SOMETHING WITH CURSOR
            rowExists = true;

        } else
        {
            // I AM EMPTY
            rowExists = false;
        }

        return rowExists;
    }



    public void insertdatabase_one(Blog blog){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(POSITION_ADAPTER,blog.getAdapter_position());
        contentValues.put(SEEKBAR,blog.getSeekbarprogress());
        contentValues.put(SIZE,blog.getList_size());
        contentValues.put(REST_CODE,blog.getRestday_code());

        sqLiteDatabase.insert(TABLE_NAME_1,null,contentValues);

        long rowInserted =sqLiteDatabase.insert(TABLE_NAME_1, null, contentValues);

        if(rowInserted != -1)
            Log.d("FFM","Save into database...");
        else
            Log.d("FFM","not save data ");

    }


    public List<Blog> getListData_one(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Blog> blogList = new ArrayList<>();
        blogList.clear();

        Cursor cursor = db.query(TABLE_NAME_1,new String[]{POSITION_ADAPTER,SEEKBAR,SIZE,REST_CODE},null,null,null,null,null);

        if (cursor != null)
            cursor.moveToFirst();

        do {

            Blog blog = new Blog();

            blog.setAdapter_position(Integer.parseInt(cursor.getString(cursor.getColumnIndex(POSITION_ADAPTER))));
            blog.setSeekbarprogress(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SEEKBAR))));
            blog.setList_size(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SIZE))));
            blog.setRestday_code(Integer.parseInt(cursor.getString(cursor.getColumnIndex(REST_CODE))));

            blogList.add(blog);


        }while (cursor.moveToNext());

        return blogList;
    }
    public boolean isChekDatabaseEmpty_one(){
        SQLiteDatabase db =this.getReadableDatabase();
        boolean rowExists;

        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_1, null);

        if (mCursor.moveToFirst())
        {
            // DO SOMETHING WITH CURSOR
            rowExists = true;

        } else
        {
            // I AM EMPTY
            rowExists = false;
        }

        return rowExists;
    }



    public void insertdatabase_two(Blog blog){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(POSITION_ADAPTER,blog.getAdapter_position());
        contentValues.put(SEEKBAR,blog.getSeekbarprogress());
        contentValues.put(SIZE,blog.getList_size());
        contentValues.put(REST_CODE,blog.getRestday_code());

        sqLiteDatabase.insert(TABLE_NAME_2,null,contentValues);

        long rowInserted =sqLiteDatabase.insert(TABLE_NAME_2, null, contentValues);

        if(rowInserted != -1)
            Log.d("FFM","Save into database...");
        else
            Log.d("FFM","not save data ");

    }


    public List<Blog> getListData_two(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Blog> blogList = new ArrayList<>();
        blogList.clear();

        Cursor cursor = db.query(TABLE_NAME_2,new String[]{POSITION_ADAPTER,SEEKBAR,SIZE,REST_CODE},null,null,null,null,null);

        if (cursor != null)
            cursor.moveToFirst();

        do {

            Blog blog = new Blog();

            blog.setAdapter_position(Integer.parseInt(cursor.getString(cursor.getColumnIndex(POSITION_ADAPTER))));
            blog.setSeekbarprogress(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SEEKBAR))));
            blog.setList_size(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SIZE))));
            blog.setRestday_code(Integer.parseInt(cursor.getString(cursor.getColumnIndex(REST_CODE))));

            blogList.add(blog);


        }while (cursor.moveToNext());

        return blogList;
    }
    public boolean isChekDatabaseEmpty_two(){
        SQLiteDatabase db =this.getReadableDatabase();
        boolean rowExists;

        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_2, null);

        if (mCursor.moveToFirst())
        {
            // DO SOMETHING WITH CURSOR
            rowExists = true;

        } else
        {
            // I AM EMPTY
            rowExists = false;
        }

        return rowExists;
    }


    public void insertdatabase_three(Blog blog){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(POSITION_ADAPTER,blog.getAdapter_position());
        contentValues.put(SEEKBAR,blog.getSeekbarprogress());
        contentValues.put(SIZE,blog.getList_size());
        contentValues.put(REST_CODE,blog.getRestday_code());

        sqLiteDatabase.insert(TABLE_NAME_3,null,contentValues);

        long rowInserted =sqLiteDatabase.insert(TABLE_NAME_3, null, contentValues);

        if(rowInserted != -1)
            Log.d("FFM","Save into database...");
        else
            Log.d("FFM","not save data ");

    }


    public List<Blog> getListData_three(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Blog> blogList = new ArrayList<>();
        blogList.clear();

        Cursor cursor = db.query(TABLE_NAME_3,new String[]{POSITION_ADAPTER,SEEKBAR,SIZE,REST_CODE},null,null,null,null,null);

        if (cursor != null)
            cursor.moveToFirst();

        do {

            Blog blog = new Blog();

            blog.setAdapter_position(Integer.parseInt(cursor.getString(cursor.getColumnIndex(POSITION_ADAPTER))));
            blog.setSeekbarprogress(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SEEKBAR))));
            blog.setList_size(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SIZE))));
            blog.setRestday_code(Integer.parseInt(cursor.getString(cursor.getColumnIndex(REST_CODE))));

            blogList.add(blog);


        }while (cursor.moveToNext());

        return blogList;
    }
    public boolean isChekDatabaseEmpty_three(){
        SQLiteDatabase db =this.getReadableDatabase();
        boolean rowExists;

        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_3, null);

        if (mCursor.moveToFirst())
        {
            // DO SOMETHING WITH CURSOR
            rowExists = true;

        } else
        {
            // I AM EMPTY
            rowExists = false;
        }

        return rowExists;
    }

    //set a Timer Adapter

    public void insertTimer(Blog blog){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(POSITION_ADAPTER,blog.getA_positionDB());
        contentValues.put(TIME_CODE,blog.getTimer_value_DB());

        long a = db.insert(TABLE_NAME_Timer,null,contentValues);

        if (a != -1)
            Log.d("FFM","Data is Inserted..");
        else
            Log.d("FFM","Data is not inserted");

    }

    public List<Blog> getTimerBlogListDB(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Blog> blogList = new ArrayList<>();


        Cursor cursor = db.query(TABLE_NAME_Timer,new String[]{POSITION_ADAPTER,TIME_CODE}
        ,null,null,null,null,null);

        if (cursor != null)
            cursor.moveToFirst();

        do {
            Blog blog = new Blog();

            blog.setA_positionDB(Integer.parseInt(cursor.getString(cursor.getColumnIndex(POSITION_ADAPTER))));
            blog.setTimer_value_DB(Integer.parseInt(cursor.getString(cursor.getColumnIndex(TIME_CODE))));

            blogList.add(blog);

        }while (cursor.moveToNext());

        return blogList;

    }

    public boolean isChekDatabaseEmpty_TimerData(){
        SQLiteDatabase db =this.getReadableDatabase();
        boolean rowExists;

        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_Timer, null);

        if (mCursor.moveToFirst())
        {
            // DO SOMETHING WITH CURSOR
            rowExists = true;

        } else
        {
            // I AM EMPTY
            rowExists = false;
        }

        return rowExists;
    }


}
