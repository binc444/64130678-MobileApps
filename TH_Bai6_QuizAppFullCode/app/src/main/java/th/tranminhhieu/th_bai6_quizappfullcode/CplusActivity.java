package th.tranminhhieu.th_bai6_quizappfullcode;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class CplusActivity extends AppCompatActivity {

    private TextView questionText;
    private TextView optionA, optionB, optionC, optionD;
    private CardView cardA, cardB, cardC, cardD;
    private ArrayList<Question> dsCauHoi;
    private int cauHoiHienTai = 0;
    private int diem = 0;
    private boolean laDapAnDung = false; // Biến lưu trạng thái đáp án

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cplus);

        // Liên kết UI
        questionText = findViewById(R.id.question_Cplus);
        optionA = findViewById(R.id.answerA_Cplus);
        optionB = findViewById(R.id.answerB_Cplus);
        optionC = findViewById(R.id.answerC_Cplus);
        optionD = findViewById(R.id.answerD_Cplus);

        cardA = findViewById(R.id.answerCardA);
        cardB = findViewById(R.id.answerCardB);
        cardC = findViewById(R.id.answerCardC);
        cardD = findViewById(R.id.answerCardD);

        // đọc các câu hỏi từ file JSON
        dsCauHoi = loadCauHoiJSON();

        // Hiển thị câu hỏi đầu tiên nếu không rỗng
        if (dsCauHoi != null && !dsCauHoi.isEmpty()) {
            hienThiCauHoi(dsCauHoi.get(cauHoiHienTai));
        }

        // Sự kiện chọn đáp án
        cardA.setOnClickListener(v -> xuLyChonDapAn(cardA, optionA.getText().toString()));
        cardB.setOnClickListener(v -> xuLyChonDapAn(cardB, optionB.getText().toString()));
        cardC.setOnClickListener(v -> xuLyChonDapAn(cardC, optionC.getText().toString()));
        cardD.setOnClickListener(v -> xuLyChonDapAn(cardD, optionD.getText().toString()));

        // Sự kiện khi người dùng nhấn nút next
        findViewById(R.id.next_Cplus).setOnClickListener(v -> {
            // Chỉ cộng điểm khi đáp án đúng
            if (laDapAnDung) {
                diem += 10;
            }

            // Chuyển đến câu hỏi tiếp theo hoặc kết thúc quiz
            cauHoiHienTai++;
            if (cauHoiHienTai < dsCauHoi.size()) {
                hienThiCauHoi(dsCauHoi.get(cauHoiHienTai));
            } else {
                // Chuyển sang màn hình kết quả
                Intent intent = new Intent(CplusActivity.this, ResultActivity.class);
                intent.putExtra("score", diem);
                startActivity(intent);
                finish();
            }
        });
    }

    private ArrayList<Question> loadCauHoiJSON() {
        ArrayList<Question> questions = new ArrayList<>();
        try {
            InputStream is = getResources().openRawResource(R.raw.questions_cplus);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();

            String json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String question = obj.getString("question");
                JSONArray optionsArray = obj.getJSONArray("options");
                String correctAnswer = obj.getString("correct_answer");

                ArrayList<String> options = new ArrayList<>();
                for (int j = 0; j < optionsArray.length(); j++) {
                    options.add(optionsArray.getString(j));
                }

                questions.add(new Question(question, options, correctAnswer));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questions;
    }

    private void hienThiCauHoi(Question question) {
        // Đặt lại câu hỏi và đáp án
        questionText.setText(question.getQuestion());
        optionA.setText(question.getOptions().get(0));
        optionB.setText(question.getOptions().get(1));
        optionC.setText(question.getOptions().get(2));
        optionD.setText(question.getOptions().get(3));

        // Đặt lại màu sắc của các thẻ đáp án
        resetMauDapAn();

        // Đặt lại trạng thái đáp án
        laDapAnDung = false;
    }

    private void resetMauDapAn() {
        cardA.setCardBackgroundColor(Color.WHITE);
        cardB.setCardBackgroundColor(Color.WHITE);
        cardC.setCardBackgroundColor(Color.WHITE);
        cardD.setCardBackgroundColor(Color.WHITE);
    }

    // Biến để lưu thẻ đáp án đã chọn trước đó
    private CardView dapAnChonTruocDo = null;

    private void xuLyChonDapAn(CardView selectedCard, String selectedAnswer) {
        // Reset màu của thẻ đáp án đã chọn trước đó (nếu có)
        if (dapAnChonTruocDo != null) {
            dapAnChonTruocDo.setCardBackgroundColor(Color.WHITE);
        }

        // Đổi màu thẻ được chọn thành màu xanh lá
        selectedCard.setCardBackgroundColor(Color.GREEN);

        // Cập nhật thẻ được chọn hiện tại vào biến lưu trữ
        dapAnChonTruocDo = selectedCard;

        // Kiểm tra đáp án đúng nhưng chưa cộng điểm
        Question currentQuestion = dsCauHoi.get(cauHoiHienTai);
        laDapAnDung = selectedAnswer.equals(currentQuestion.getCorrectAnswer());
    }
}
