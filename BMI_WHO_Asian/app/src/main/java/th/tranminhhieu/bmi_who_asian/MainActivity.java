package th.tranminhhieu.bmi_who_asian;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText edtHeight, edtWeight;
    private RadioButton radioButton;
    private Button btnCalculate;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtHeight = findViewById(R.id.edtHeight);
        edtWeight = findViewById(R.id.edtWeight);
        radioButton = findViewById(R.id.radioButton);
        btnCalculate = findViewById(R.id.btnCalculate);
        tvResult = findViewById(R.id.tvResult);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    double height = Double.parseDouble(edtHeight.getText().toString()) / 100; // cm -> m
                    double weight = Double.parseDouble(edtWeight.getText().toString());

                    // Tính BMI
                    double bmi = weight / (height * height);
                    String classification;

                    //phân loại BMI
                    if (radioButton.isChecked()) {
                        // Tiêu chuẩn châu Á
                        if (bmi < 17.5) {
                            classification = "Underweight";
                        } else if (bmi <= 22.99) {
                            classification = "Normal Weight";
                        } else if (bmi <= 27.99) {
                            classification = "Over Weight";
                        } else {
                            classification = "Obese";
                        }
                    } else {
                        // Tiêu chuẩn WHO
                        if (bmi < 18.5) {
                            classification = "Underweight";
                        } else if (bmi <= 24.99) {
                            classification = "Normal Weight";
                        } else if (bmi <= 29.99) {
                            classification = "Over Weight";
                        } else {
                            classification = "Obese";
                        }
                    }

                    // Hiển thị kết quả
                    tvResult.setText(String.format("Kết quả: %.2f (%s)", bmi, classification));
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ chiều cao và cân nặng!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}