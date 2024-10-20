package com.example.btvn_caro3x3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button[] buttons = new Button[9];
    private boolean isXTurn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo nút
        buttons[0] = findViewById(R.id.o1);
        buttons[1] = findViewById(R.id.o2);
        buttons[2] = findViewById(R.id.o3);
        buttons[3] = findViewById(R.id.o4);
        buttons[4] = findViewById(R.id.o5);
        buttons[5] = findViewById(R.id.o6);
        buttons[6] = findViewById(R.id.o7);
        buttons[7] = findViewById(R.id.o8);
        buttons[8] = findViewById(R.id.o9);

        // Gán sự kiện cho các nút
        for (Button button : buttons) {
            button.setOnClickListener(this::onButtonClick);
        }

        findViewById(R.id.btnRefresh).setOnClickListener(v -> resetGame());
        findViewById(R.id.btnClose).setOnClickListener(v -> finish());
    }

    private void onButtonClick(View view) {
        Button button = (Button) view;
        if (button.getText().toString().isEmpty()) {
            button.setText(isXTurn ? "X" : "O");
            if (checkWin()) {
                showAlert((isXTurn ? "X" : "O") + " thắng!");
            } else if (isBoardFull()) {
                showAlert("Hòa!");
            }
            isXTurn = !isXTurn; // Đổi lượt chơi
        }
    }

    private boolean checkWin() {
        return checkLine(buttons[0], buttons[1], buttons[2]) || // Hàng đầu
                checkLine(buttons[3], buttons[4], buttons[5]) || // Hàng giữa
                checkLine(buttons[6], buttons[7], buttons[8]) || // Hàng dưới
                checkLine(buttons[0], buttons[3], buttons[6]) || // Cột trái
                checkLine(buttons[1], buttons[4], buttons[7]) || // Cột giữa
                checkLine(buttons[2], buttons[5], buttons[8]) || // Cột phải
                checkLine(buttons[0], buttons[4], buttons[8]) || // Chéo
                checkLine(buttons[2], buttons[4], buttons[6]);   // Chéo
    }

    private boolean checkLine(Button a, Button b, Button c) {
        return !a.getText().toString().isEmpty() && a.getText().equals(b.getText()) && a.getText().equals(c.getText());
    }

    private boolean isBoardFull() {
        for (Button button : buttons) {
            if (button.getText().toString().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void showAlert(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Kết quả")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    private void resetGame() {
        for (Button button : buttons) {
            button.setText("");
        }
        isXTurn = true;
    }
}