package ntu.hieutm.GourmetBuddy;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Ánh xạ BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        // Đặt mục "Hồ sơ" được chọn
        bottomNavigationView.setSelectedItemId(R.id.profile);

        // Lắng nghe sự kiện chọn mục trong BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.home) {
                // Chuyển sang MainActivity
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish(); // Kết thúc ProfileActivity để tránh lặp màn hình
                return true;
            } else if (id == R.id.profile) {
                // Đã ở màn hình hồ sơ, không cần chuyển Activity
                return true;
            }

            return false;
        });
    }
}