package com.example.bmi_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText edtWeight, edtHeight;
    TextView tvResult;
    Button btnTinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtWeight = findViewById(R.id.edtWeight);
        edtHeight = findViewById(R.id.edtHeight);
        tvResult = findViewById(R.id.tvResult);
        btnTinh = findViewById(R.id.btnTinh);

        // Set sự kiện khi nhấn nút "Calculate BMI"
        btnTinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();
            }
        });
    }

    // Hàm tính toán BMI
    private void calculateBMI() {
        // Lấy dữ liệu từ EditText
        String weightStr = edtWeight.getText().toString();
        String heightStr = edtHeight.getText().toString();

        // Kiểm tra nếu người dùng không nhập gì
        if (weightStr.isEmpty() || heightStr.isEmpty()) {
            tvResult.setText("Please enter both weight and height!");
            return;
        }

        // Chuyển đổi giá trị từ String sang số thực
        float weight = Float.parseFloat(weightStr);
        float height = Float.parseFloat(heightStr);

        // Tính chỉ số BMI
        float bmi = weight / (height * height);

        // Hiển thị kết quả
        tvResult.setText(String.format("BMI: %.2f", bmi));
    }
}