package com.example.recipes_pr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context){
        super(context,"recipes",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE recipes (name TEXT, recipe TEXT);");
        db.execSQL("INSERT INTO recipes (name, recipe) VALUES " +
                "(\"cake\", \"Flour, sugar, eggs\"), " +
                "(\"cookies\", \"Flour, butter, sugar\");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS recipes");
        onCreate(db);
    }

    public String getRecipe(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String recipe = null;
        Cursor cursor = db.rawQuery("SELECT recipe FROM recipes WHERE name=?", new String[]{name});
        if (cursor.moveToFirst()) {
            recipe = cursor.getString(0);
        }
        cursor.close();
        return recipe;
    }

    public boolean addRecipe(String name, String recipe) {
        if (recipeExists(name)) {
            return false;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("recipe", recipe);
        db.insert("recipes", null, contentValues);
        db.close();
        return true;
    }

    public boolean recipeExists(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT 1 FROM recipes WHERE name=?", new String[]{name});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    public boolean deleteRecipe(String name) {
        if (!recipeExists(name)) {
            return false;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete("recipes", "name = ?", new String[]{name});
        db.close();
        return rowsAffected > 0;
    }
}
