package com.example.diary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{

    //Set up the database elements
    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "todolist";
    private static final String COL1 = "ID";
    private static final String COL2 = "items";
    private static final String COL3 = "completed";

    public DatabaseHelper(Context context){
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, items TEXT, completed INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP IF TABLE EXISTS todolist");
        onCreate(db);
    }

    public boolean addData(String item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item);
        contentValues.put(COL3, 0);

        Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //If date as inserted correctly it will return -1
        if (result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    //Retrieve elements from database
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    //Delete entry from database
    public void deleteData(String item){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME+ " WHERE "+ COL2 +"='"+item+"'";
        db.execSQL(query);
    }

    //Clear element from the database
    public void clearData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME;
        db.execSQL(query);
    }

    public void setCompleted(String task){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE todolist SET completed = 1 WHERE "+ COL2 +"='"+task+"'";
        db.execSQL(query);
    }

    public void removeCompleted(String task){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE todolist SET completed = 0 WHERE "+ COL2 +"='"+task+"'";
        db.execSQL(query);
    }

    public boolean isItem(String item){

        boolean found=false;

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM todolist";
        Cursor data = db.rawQuery(query, null);
        while(data.moveToNext()){

            if(data.getString(1).equals(item)){
                found=true;
            }

        }

        return found;
    }

    public int countItems(){

        int count = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM todolist";
        Cursor data = db.rawQuery(query, null);
        while(data.moveToNext()){
            count++;
        }

        return count;
    }

}
