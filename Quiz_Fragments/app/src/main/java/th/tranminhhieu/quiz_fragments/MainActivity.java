package th.tranminhhieu.quiz_fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnEnglish, btnMath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEnglish = findViewById(R.id.btnEnglish);
        btnMath = findViewById(R.id.btnMath);

        // Xử lý sự kiện khi nhấn nút Tiếng Anh
        btnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity_QuizEnglish.class);
                startActivity(intent);
            }
        });

        // Xử lý sự kiện khi nhấn nút Toán
        btnMath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity_QuizMath.class);
                startActivity(intent);
            }
        });
    }
}