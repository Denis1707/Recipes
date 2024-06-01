package com.example.recipes_pr;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class RecipeAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List<String> recipes;

    public RecipeAdapter(Context context, List<String> recipes) {
        super(context, R.layout.list_item, recipes);
        this.context = context;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

        String currentRecipe = recipes.get(position);

        TextView recipeTextView = listItem.findViewById(R.id.recipe_name);
        ImageButton deleteRecipeButton = listItem.findViewById(R.id.delete_recipe_button);

        recipeTextView.setText(currentRecipe);

        deleteRecipeButton.setOnClickListener(v -> {
            DBHelper dbHelper = new DBHelper(getContext());
            if (dbHelper.deleteRecipe(currentRecipe)) {
                recipes.remove(position);
                notifyDataSetChanged();
            }
        });

        listItem.setOnClickListener((v) -> {
            getContext().startActivity(
                    new Intent(getContext(), RecipeDetailsActivity.class).putExtra("recipeName", currentRecipe)
            );
        });

        return listItem;
    }
}