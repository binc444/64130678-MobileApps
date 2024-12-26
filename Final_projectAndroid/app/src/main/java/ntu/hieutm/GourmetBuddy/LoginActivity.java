package ntu.hieutm.GourmetBuddy;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private TextView registerLink;

    private DB_GourmetBuddy dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerLink = findViewById(R.id.registerLink);

        // Khởi tạo đối tượng DB_GourmetBuddy
        dbHelper = new DB_GourmetBuddy(this);

        // Bắt sự kiện cho nút Đăng nhập
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                    if (authenticateUser(username, password)) {
                        // Nếu đăng nhập thành công, chuyển sang màn hình chính
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Nếu thông tin đăng nhập sai
                        Toast.makeText(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu sai!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Bắt sự kiện cho liên kết đăng ký
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến màn hình đăng ký
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    // Phương thức xác thực người dùng
    private boolean authenticateUser(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Mã hóa mật khẩu người dùng nhập vào
        String hashedPassword = Utils.md5(password);

        // Truy vấn để kiểm tra tên đăng nhập và mật khẩu
        String[] projection = {
                DB_GourmetBuddy.COLUMN_USER_ID,
                DB_GourmetBuddy.COLUMN_TEN_DANG_NHAP,
                DB_GourmetBuddy.COLUMN_MAT_KHAU
        };

        String selection = DB_GourmetBuddy.COLUMN_TEN_DANG_NHAP + " = ? AND " + DB_GourmetBuddy.COLUMN_MAT_KHAU + " = ?";
        String[] selectionArgs = {username, hashedPassword};

        Cursor cursor = db.query(
                DB_GourmetBuddy.TABLE_USER,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        // Kiểm tra nếu có kết quả trả về
        boolean isAuthenticated = cursor.getCount() > 0;
        cursor.close();
        db.close();

        return isAuthenticated;
    }
}
