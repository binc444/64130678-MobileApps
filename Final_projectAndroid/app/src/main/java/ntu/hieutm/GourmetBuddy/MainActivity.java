package ntu.hieutm.GourmetBuddy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ các thành phần giao diện
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        // Đặt mục "Món ăn" được chọn
        bottomNavigationView.setSelectedItemId(R.id.home);

        // Lắng nghe sự kiện chọn mục trong BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.profile) {
                // Chuyển sang ProfileActivity
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish(); // Kết thúc MainActivity để tránh lặp màn hình
                return true;
            } else if (id == R.id.home) {
                // Đã ở màn hình chính, không cần chuyển Activity
                return true;
            }

            return false;
        });

    }
}
