package ntu.hieutm.GourmetBuddy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FoodAvailableActivity extends AppCompatActivity {

    private RecyclerView rvRecipes;
    private RecipeAdapter recipeAdapter;
    private List<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_recipes);

        // Ánh xạ các thành phần giao diện
        rvRecipes = findViewById(R.id.rv_recipes);

        // Thiết lập RecyclerView
        rvRecipes.setLayoutManager(new LinearLayoutManager(this));

        // Lấy danh sách công thức từ cơ sở dữ liệu
        DBHelper dbHelper = new DBHelper(this);
        recipes = dbHelper.getAllRecipes();

        if (recipes == null || recipes.isEmpty()) {
            Toast.makeText(this, "Danh sách món ăn trống!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Thiết lập Adapter
        recipeAdapter = new RecipeAdapter(this, recipes);
        rvRecipes.setAdapter(recipeAdapter);

    }
}
