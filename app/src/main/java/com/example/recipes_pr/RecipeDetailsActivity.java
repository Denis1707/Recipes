package com.example.recipes_pr;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class RecipeDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details2);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        String recipeName = intent.getStringExtra("recipeName");

        TextView recipeNameView = findViewById(R.id.recipe_name_1);
        recipeNameView.setText(recipeName);

        TextView recipeInstructionsView = findViewById(R.id.recipe_instructions);

        DBHelper dbHelper = new DBHelper(this);
        String recipe = dbHelper.getRecipe(recipeName);
        if (recipe != null) {
            recipeInstructionsView.setText(recipe);
        } else {
            recipeInstructionsView.setText("Рецепт не найден");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}