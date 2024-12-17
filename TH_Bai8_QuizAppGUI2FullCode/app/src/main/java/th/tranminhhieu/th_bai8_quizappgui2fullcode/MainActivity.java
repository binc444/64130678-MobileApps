package th.tranminhhieu.th_bai8_quizappgui2fullcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.imageView_enterQuiz).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Navigate to ToanActivity
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });
    }
}