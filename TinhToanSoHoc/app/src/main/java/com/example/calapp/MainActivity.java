package com.example.calapp;

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

    //1. Khai báo các điều khiển
    Button btnAdd, btnSub, btnMul, btnDiv;
    EditText edtA, edtB;
    TextView tvResult;

    //2. Hàm tìm các điều khiển, cất vào biến ở trên
    void getControls() {
        btnAdd = findViewById(R.id.btnAdd);
        btnSub = findViewById(R.id.btnSub);
        btnMul = findViewById(R.id.btnMul);
        btnDiv = findViewById(R.id.btnDiv);
        edtA = findViewById(R.id.edtA);
        edtB = findViewById(R.id.edtB);
        tvResult = findViewById(R.id.tvResultValue);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Hàm này được gọi đầu tiên trước khi App hiện ra
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Gắn với giao diện
        // Gọi hàm lấy các điều khiển
        getControls();

        // Gán sự kiện cho các nút bấm
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                langNgheCong();
            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                langNgheTru();
            }
        });

        btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                langNgheNhan();
            }
        });

        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                langNgheChia();
            }
        });
    }

    // Xử lý phép cộng
    private void langNgheCong() {
        double a = Double.parseDouble(edtA.getText().toString());
        double b = Double.parseDouble(edtB.getText().toString());
        double result = a + b;
        tvResult.setText(String.valueOf(result));
    }

    // Xử lý phép trừ
    private void langNgheTru() {
        double a = Double.parseDouble(edtA.getText().toString());
        double b = Double.parseDouble(edtB.getText().toString());
        double result = a - b;
        tvResult.setText(String.valueOf(result));
    }

    // Xử lý phép nhân
    private void langNgheNhan() {
        double a = Double.parseDouble(edtA.getText().toString());
        double b = Double.parseDouble(edtB.getText().toString());
        double result = a * b;
        tvResult.setText(String.valueOf(result));
    }

    // Xử lý phép chia
    private void langNgheChia() {
        double a = Double.parseDouble(edtA.getText().toString());
        double b = Double.parseDouble(edtB.getText().toString());
        if (b != 0) {
            double result = a / b;
            tvResult.setText(String.valueOf(result));
        } else {
            tvResult.setText("Lỗi: Không thể chia cho 0");
        }
    }

}