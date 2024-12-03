package th.tranminhhieu.pheptoanab;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText edtA, edtB;
    private RadioGroup radioGroup;
    private RadioButton rbCong, rbTru, rbNhan, rbChia;
    private Button btnTinhToan;
    private TextView tvKetQua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtA = findViewById(R.id.edtA);
        edtB = findViewById(R.id.edtB);
        radioGroup = findViewById(R.id.radioGroup);
        rbCong = findViewById(R.id.cong);
        rbTru = findViewById(R.id.tru);
        rbNhan = findViewById(R.id.nhan);
        rbChia = findViewById(R.id.chia);
        btnTinhToan = findViewById(R.id.btnTinhToan);
        tvKetQua = findViewById(R.id.tvKetQua);

        // Xử lý sự kiện nút Tính Toán
        btnTinhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Lấy giá trị số A và B
                    String strA = edtA.getText().toString().trim();
                    String strB = edtB.getText().toString().trim();

                    if (strA.isEmpty() || strB.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ số A và số B", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    double numberA = Double.parseDouble(strA);
                    double numberB = Double.parseDouble(strB);

                    // Kiểm tra phép toán được chọn
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    double result = 0;

                    if (selectedId == R.id.cong) {
                        result = numberA + numberB;
                    } else if (selectedId == R.id.tru) {
                        result = numberA - numberB;
                    } else if (selectedId == R.id.nhan) {
                        result = numberA * numberB;
                    } else if (selectedId == R.id.chia) {
                        if (numberB == 0) {
                            Toast.makeText(MainActivity.this, "Không thể chia cho 0", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        result = numberA / numberB;
                    } else {
                        Toast.makeText(MainActivity.this, "Vui lòng chọn một phép toán", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Hiển thị kết quả
                    tvKetQua.setText("Kết quả: " + result);

                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Định dạng số không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}