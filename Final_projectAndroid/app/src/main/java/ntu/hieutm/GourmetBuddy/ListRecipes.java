package ntu.hieutm.GourmetBuddy;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class ListRecipes extends AppCompatActivity {

    private RecyclerView rvRecipes;  // RecyclerView để hiển thị danh sách các món ăn
    private RecipeAdapter recipeAdapter;  // Adapter để quản lý dữ liệu trong RecyclerView
    private List<Recipe> recipes;  // Danh sách các món ăn (recipes)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_recipes);  // Thiết lập giao diện người dùng

        rvRecipes = findViewById(R.id.rv_recipes);

        // Thiết lập LayoutManager cho RecyclerView, để hiển thị danh sách theo kiểu dọc
        rvRecipes.setLayoutManager(new LinearLayoutManager(this));

        // Lấy danh sách các món ăn từ Intent
        recipes = getIntent().getParcelableArrayListExtra("recipes");

        // Nếu danh sách món ăn rỗng hoặc null, thông báo cho người dùng và dừng tiếp tục
        if (recipes == null || recipes.isEmpty()) {
            Toast.makeText(this, "Danh sách món ăn trống!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo và thiết lập Adapter cho RecyclerView
        recipeAdapter = new RecipeAdapter(this, recipes);
        rvRecipes.setAdapter(recipeAdapter);
    }

}
