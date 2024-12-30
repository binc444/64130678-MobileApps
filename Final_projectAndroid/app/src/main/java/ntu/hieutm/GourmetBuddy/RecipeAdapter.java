package ntu.hieutm.GourmetBuddy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    private Context context;
    private List<Recipe> recipes;

    public RecipeAdapter(Context context, List<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.tvRecipeName.setText(recipe.getName());
        holder.tvRecipeCategory.setText("Danh mục: " + recipe.getCategory());
        holder.tvRecipeDifficulty.setText("Độ khó: " + recipe.getDifficulty());
        holder.tvRecipeTime.setText("Thời gian: " + recipe.getTime() + " phút");
        holder.tvRecipeServings.setText("Khẩu phần: " + recipe.getServings());
        holder.tvRecipeHealth.setText("Sức khỏe: " + recipe.getHealthInfo());
        holder.tvRecipeInstructions.setText("Hướng dẫn: " + recipe.getInstructions());

        // Lấy ID từ drawable
        int resourceId = context.getResources().getIdentifier(recipe.getImageUrl(), "drawable", context.getPackageName());

        if (resourceId != 0) {
            // Tạo URI từ drawable resource
            String uri = "android.resource://" + context.getPackageName() + "/" + resourceId;
            Glide.with(context)
                    .load(uri)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.placeholder_image)
                    .into(holder.ivRecipeImage);
        } else {
            holder.ivRecipeImage.setImageResource(R.drawable.placeholder_image);
        }

        // Thêm sự kiện nhấn vào ảnh
        holder.ivRecipeImage.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailRecipeActivity.class);
            intent.putExtra("recipe", recipe); // Gửi đối tượng Recipe qua Intent
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRecipeName, tvRecipeCategory, tvRecipeDifficulty, tvRecipeTime, tvRecipeServings, tvRecipeHealth, tvRecipeInstructions;
        ImageView ivRecipeImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecipeName = itemView.findViewById(R.id.tv_recipe_name);
            tvRecipeCategory = itemView.findViewById(R.id.tv_recipe_category);
            tvRecipeDifficulty = itemView.findViewById(R.id.tv_recipe_difficulty);
            tvRecipeTime = itemView.findViewById(R.id.tv_recipe_time);
            tvRecipeServings = itemView.findViewById(R.id.tv_recipe_servings);
            tvRecipeHealth = itemView.findViewById(R.id.tv_recipe_health);
            tvRecipeInstructions = itemView.findViewById(R.id.tv_recipe_instructions);
            ivRecipeImage = itemView.findViewById(R.id.iv_recipe_image);
        }
    }
}
