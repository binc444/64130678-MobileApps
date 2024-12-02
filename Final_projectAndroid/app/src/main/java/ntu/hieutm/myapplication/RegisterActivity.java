package ntu.hieutm.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText, fullNameEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton;
    private TextView loginLink;
    private DB_GourmetBuddy dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Liên kết giao diện
        usernameEditText = findViewById(R.id.usernameEditText);
        fullNameEditText = findViewById(R.id.fullNameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        registerButton = findViewById(R.id.registerButton);
        loginLink = findViewById(R.id.loginLink);

        // Khởi tạo database
        dbHelper = new DB_GourmetBuddy(this);

        // Xử lý khi nhấn nút Đăng ký
        registerButton.setOnClickListener(v -> registerUser());

        // Chuyển sang màn hình Đăng nhập
        loginLink.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void registerUser() {
        // Lấy dữ liệu từ giao diện
        String username = usernameEditText.getText().toString().trim();
        String fullName = fullNameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        // Kiểm tra dữ liệu nhập vào
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(fullName) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu xác nhận không khớp!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Mã hóa mật khẩu bằng MD5
        String hashedPassword = Utils.md5(password);
        if (hashedPassword == null) {
            Toast.makeText(this, "Lỗi khi mã hóa mật khẩu!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lưu vào database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_GourmetBuddy.COLUMN_TEN_DANG_NHAP, username);
        values.put(DB_GourmetBuddy.COLUMN_MAT_KHAU, hashedPassword);
        values.put(DB_GourmetBuddy.COLUMN_HO_TEN, fullName);

        long newRowId = db.insert(DB_GourmetBuddy.TABLE_USER, null, values);

        if (newRowId == -1) {
            Toast.makeText(this, "Tên đăng nhập đã tồn tại!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        db.close();
    }
}