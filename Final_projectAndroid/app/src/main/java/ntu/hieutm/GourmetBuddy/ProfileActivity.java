package ntu.hieutm.GourmetBuddy;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private TextView dbTenDangNhap, dbHoTen, dbNgaySinh, dbGioiTinh;
    private DAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Ánh xạ các TextView và RecyclerView
        anhXaViews();

        // Khởi tạo DAO và mở kết nối cơ sở dữ liệu
        dao = new DAO(this);
        dao.open();

        // Lấy thông tin người dùng
        int userId = 1; //
        hienThiInfoUser(userId);

        // Ánh xạ BottomNavigationView và lắng nghe sự kiện chọn mục
        xuLyBotNav();
    }

    private void anhXaViews() {
        dbTenDangNhap = findViewById(R.id.db_ten_dang_nhap);
        dbHoTen = findViewById(R.id.db_ho_ten);
        dbNgaySinh = findViewById(R.id.db_ngay_sinh);
        dbGioiTinh = findViewById(R.id.db_gioi_tinh);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
    }

    private void hienThiInfoUser(int userId) {
        Cursor cursor = dao.getUserInfo(userId);

        if (cursor != null && cursor.moveToFirst()) {
            // Hiển thị thông tin người dùng
            setTextIfExists(cursor, DB_GourmetBuddy.COLUMN_TEN_DANG_NHAP, dbTenDangNhap, "Tên Đăng Nhập: ");
            setTextIfExists(cursor, DB_GourmetBuddy.COLUMN_HO_TEN, dbHoTen, "Họ và Tên: ");
            setTextIfExists(cursor, DB_GourmetBuddy.COLUMN_NGAY_SINH, dbNgaySinh, "Ngày sinh: ");
            setTextIfExists(cursor, DB_GourmetBuddy.COLUMN_GIOI_TINH, dbGioiTinh, "Giới tính: ");
        } else {
            Toast.makeText(this, "Không tìm thấy thông tin người dùng!", Toast.LENGTH_SHORT).show();
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    private void setTextIfExists(Cursor cursor, String columnName, TextView textView, String prefix) {
        int columnIndex = cursor.getColumnIndex(columnName);
        if (columnIndex != -1) {
            String value = cursor.getString(columnIndex);
            textView.setText(prefix + value);
        }
    }

    private void xuLyBotNav() {
        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.home) {
                // Chuyển sang MainActivity
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                return true;
            } else if (id == R.id.profile) {
                // Đã ở màn hình hồ sơ
                return true;
            }

            return false;
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Đóng kết nối với cơ sở dữ liệu khi Activity bị hủy
        dao.close();
    }
}
