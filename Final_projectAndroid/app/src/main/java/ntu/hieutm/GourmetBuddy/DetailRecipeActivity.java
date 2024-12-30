package ntu.hieutm.GourmetBuddy;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class DetailRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recipe);

        // Khởi tạo các thành phần giao diện
        ImageView ivRecipeImage = findViewById(R.id.iv_detail_recipe_image);
        TextView tvRecipeName = findViewById(R.id.tv_detail_recipe_name);

        // Nhận dữ liệu từ Intent
        Recipe recipe = getIntent().getParcelableExtra("recipe");
        if (recipe != null) {
            // Đặt tên món ăn vào TextView
            tvRecipeName.setText(recipe.getName());

            // Lấy ID của ảnh từ thư mục drawable dựa trên tên ảnh
            @SuppressLint("DiscouragedApi") int resourceId = getResources().getIdentifier(recipe.getImageUrl(), "drawable", getPackageName());
            if (resourceId != 0) {
                // Hiển thị ảnh từ thư mục drawable
                ivRecipeImage.setImageResource(resourceId);
            } else {
                // Hiển thị ảnh mặc định nếu không tìm thấy ảnh
                ivRecipeImage.setImageResource(R.drawable.placeholder_image);
            }
        }
        // Áp dụng hiệu ứng fade-in cho các thành phần
        applyFadeInAnimation(ivRecipeImage);
        applyFadeInAnimation(tvRecipeName);
    }

    private void applyFadeInAnimation(View view) {
        AlphaAnimation fadeIn = new AlphaAnimation(0f, 1f); // Từ mờ dần sang rõ
        fadeIn.setDuration(1000); // Thời gian hiệu ứng (1 giây)
        fadeIn.setStartOffset(300); // Bắt đầu hiệu ứng sau 300ms (tùy chỉnh)
        view.setAnimation(fadeIn);
        view.startAnimation(fadeIn);
    }
}
