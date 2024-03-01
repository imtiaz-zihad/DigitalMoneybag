package com.bongoacademy.digitalmoneybag;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "digital_moneybag", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table expense(id INTEGER primary key autoincrement,amount DOUBLE, reason TEXT ,time DOUBLE)");
        db.execSQL("create table income(id INTEGER primary key autoincrement,amount DOUBLE, reason TEXT ,time DOUBLE)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists expense");
        db.execSQL("drop table if exists income");

    }
         //==========================================================
    public void addexpense(double amount ,String reason){

        SQLiteDatabase db = this.getWritableDatabase();

        long currentTimeMillis = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String formattedTime = sdf.format(new Date(currentTimeMillis));

        ContentValues conval = new ContentValues();
        conval.put("amount",amount);
        conval.put("reason",reason);
        conval.put("time",formattedTime);

        db.insert("expense",null,conval);
    }



    //==========================================================


    public void addIncome(double amount ,String reason){

        SQLiteDatabase db = this.getWritableDatabase();

        long currentTimeMillis = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String formattedTime = sdf.format(new Date(currentTimeMillis));

        ContentValues conval = new ContentValues();
        conval.put("amount",amount);
        conval.put("reason",reason);
        conval.put("time",formattedTime);

        db.insert("income",null,conval);
    }

    public double calculatedTotalExpense(){
        double total=0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from expense",null);
        if (cursor!=null && cursor.getCount()>0){

            while (cursor.moveToNext()){
                double amount = cursor.getDouble(1);
                total = total+amount;

            }

        }
        return total;

    }


    public double calculatedTotalIncome(){
        double totalExpense=0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from income",null);
        if (cursor!=null && cursor.getCount()>0){

            while (cursor.moveToNext()){
                double amount = cursor.getDouble(1);
                totalExpense = totalExpense+amount;

            }

        }
        return totalExpense;

    }


    public Cursor getAllExpenses(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor= db.rawQuery("select * from expense",null);
        return cursor;

    }
    public Cursor getAllIncome(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor= db.rawQuery("select * from income",null);
        return cursor;

    }

    public  void deleteExpense(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from expense where id like "+id);

    }
    public  void deleteIncome(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from income where id like "+id);

    }

    public  void updateIncome(String id,Double amount){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + "income" + " SET amount ='" + amount + "' WHERE id =" +id);

    }
    public  void updateEx(String id,Double amount){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("UPDATE " + "expense" + " SET amount ='" + amount + "' WHERE id =" +id);

       // db.execSQL("UPDATE " + "expense" + " SET name ='" + sName + "' WHERE id =" +id);

    }
}
