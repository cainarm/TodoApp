package com.example.cainarm.todo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;


import com.example.cainarm.todo.database.DBhelper;
import com.example.cainarm.todo.model.Todo;

import java.util.ArrayList;

/**
 * Created by cainarm on 2/24/17.
 */

public class TodoDao{
    private Context mContext;
    private DBhelper helper;


    public static final String TODO_TABLE = "todo";
    public static final String NAME_COLUMN = "name";
    public static final String ID_COLUMN = "id";

    public static String sqlCreateTbl =
            "CREATE TABLE if not exists todo ("+
                    ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
                    NAME_COLUMN + " TEXT NOT NULL );";

    public TodoDao(Context context){
        helper = new DBhelper(context);
        SQLiteDatabase writer = helper.getWritableDatabase();
        writer.execSQL(sqlCreateTbl);
        writer.close();
    }

    public void insert(Todo todo){
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NAME_COLUMN, todo.name);

        try {
            db.insertOrThrow(TODO_TABLE, null, values);
        }catch (SQLiteConstraintException ex){

        }catch (Throwable e){
            throw e;
        }finally {
            db.close();
        }
    }

    public ArrayList<Todo> getAll(){

        SQLiteDatabase db = helper.getReadableDatabase();
        String[] columns = new String[]{ID_COLUMN,NAME_COLUMN};

        Cursor cursor = db.rawQuery("select * from " + TODO_TABLE + " order by id desc", null);
        ArrayList<Todo> todos = new ArrayList<>();

        if(cursor.moveToFirst()){
            do {
                Todo td  = new Todo(cursor.getInt(0),cursor.getString(1));
                todos.add(td);
            }while (cursor.moveToNext());
        }

        return todos;
    }
    public void delete(int id){
        SQLiteDatabase db = helper.getWritableDatabase();

        db.delete(TODO_TABLE, "ID" + "=" + String.valueOf(id), null);
        db.close();
    }
    public Todo getById(int id){
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from " + TODO_TABLE + " where id = ?", new String[]{String.valueOf(id)});
        ArrayList<Todo> todos = new ArrayList<>();

        if(cursor.moveToFirst()) {return new Todo(cursor.getInt(0), cursor.getString(1));}

        return null;
    }
}
