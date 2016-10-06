package com.hnam.mytodolist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.hnam.mytodolist.model.TodoItem;

import java.sql.SQLInput;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hnam on 10/6/2016.
 */

public class TodoDataSource {
    public static final String TAG = TodoDataSource.class.getSimpleName();
    private Context mContext;
    private TodoSqliteHelper mTodoSqliteHelper;

    public TodoDataSource(Context context){
        mContext = context;
        mTodoSqliteHelper  = new TodoSqliteHelper(mContext);
    }

    private SQLiteDatabase open(){
        return mTodoSqliteHelper.getWritableDatabase();
    }

    private void close(SQLiteDatabase database){
        database.close();
    }

    //get mytodo list
    public List<TodoItem> getTodoList(){
        SQLiteDatabase database = open();

        Cursor cursor = database.query(TodoSqliteHelper.TODO_TABLE,
                null,
                null,
                null,
                null,
                null,
                null);
        List<TodoItem> results = new ArrayList<>();

        if (cursor.moveToFirst()){
            do{
                long id = getLongValueFromColumnName(cursor, BaseColumns._ID);
                String name = getStringValueFromColumnName(cursor, TodoSqliteHelper.COLUMN_TODO_NAME);
                String dueTo = getStringValueFromColumnName(cursor, TodoSqliteHelper.COLUMN_DUE_TO_DATE);
                int priority = getIntValueFromColumnName(cursor, TodoSqliteHelper.COLUMN_PRIORITY);
                TodoItem item = new TodoItem(id, name, dueTo, priority);
                results.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        close(database);
        return results;
    }

    //add mytodo list
    public void addTodoItem(String name, String dueToDates, int priority){
        SQLiteDatabase database = open();
        database.beginTransaction();

        //implements details add todoitem
        ContentValues values = new ContentValues();
        values.put(TodoSqliteHelper.COLUMN_TODO_NAME, name);
        values.put(TodoSqliteHelper.COLUMN_DUE_TO_DATE, dueToDates);
        values.put(TodoSqliteHelper.COLUMN_PRIORITY, priority);
        database.insert(TodoSqliteHelper.TODO_TABLE, null, values);

        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);
    }

    //update mytodo list
    public void updateTodoItem(TodoItem item) {
        SQLiteDatabase database = open();
        database.beginTransaction();

        //implements details update todoitem
        ContentValues values = new ContentValues();
        values.put(TodoSqliteHelper.COLUMN_TODO_NAME, item.getName());
        values.put(TodoSqliteHelper.COLUMN_DUE_TO_DATE, item.getDueToDate());
        values.put(TodoSqliteHelper.COLUMN_PRIORITY, item.getPriority());
        database.update(TodoSqliteHelper.TODO_TABLE,
                values,
                String.format("%s=%s", BaseColumns._ID, String.valueOf(item.getId())),
                null);

        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);
    }

    //remove mytodo list
    public void removeTodo(TodoItem item){
        SQLiteDatabase database = open();
        database.beginTransaction();

        //implements datails
        database.delete(TodoSqliteHelper.TODO_TABLE,
                String.format("%s=%s", BaseColumns._ID, String.valueOf(item.getId())), null);

        database.setTransactionSuccessful();
        database.endTransaction();
        database.close();
    }

    private int getIntValueFromColumnName(Cursor cursor, String columnName){
        int index = cursor.getColumnIndex(columnName);
        return cursor.getInt(index);
    }

    private String getStringValueFromColumnName(Cursor cursor, String columnName){
        int index = cursor.getColumnIndex(columnName);
        return cursor.getString(index);
    }

    private long getLongValueFromColumnName(Cursor cursor, String columnName){
        int index = cursor.getColumnIndex(columnName);
        return cursor.getLong(index);
    }
}
