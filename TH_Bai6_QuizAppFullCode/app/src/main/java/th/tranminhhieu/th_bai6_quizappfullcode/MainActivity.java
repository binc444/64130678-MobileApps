package th.tranminhhieu.th_bai6_quizappfullcode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.cardView_Cplus).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Navigate to ToanActivity
                Intent intent = new Intent(MainActivity.this, CplusActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.cardView_Java).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Navigate to ToanActivity
                Intent intent = new Intent(MainActivity.this, JavaActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.cardView_Python).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Navigate to ToanActivity
                Intent intent = new Intent(MainActivity.this, PythonActivity.class);
                startActivity(intent);
            }
        });
    }
}