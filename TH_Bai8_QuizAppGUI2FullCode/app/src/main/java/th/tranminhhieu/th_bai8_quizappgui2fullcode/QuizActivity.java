package th.tranminhhieu.th_bai8_quizappgui2fullcode;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    private TextView questionText;
    private TextView optionA, optionB, optionC, optionD;
    private TextView resultText;
    private CardView cardA, cardB, cardC, cardD;
    private ArrayList<Question> dsCauHoi;
    private int cauHoiHienTai = 0;
    private boolean laDapAnDung = false; // Biến lưu trạng thái đáp án
    private boolean daChonDapAn = false; // Biến lưu trạng thái đã chọn đáp án

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Liên kết UI
        questionText = findViewById(R.id.question_quiz);
        optionA = findViewById(R.id.answerA);
        optionB = findViewById(R.id.answerB);
        optionC = findViewById(R.id.answerC);
        optionD = findViewById(R.id.answerD);
        resultText = findViewById(R.id.resultText);

        cardA = findViewById(R.id.answerCardA);
        cardB = findViewById(R.id.answerCardB);
        cardC = findViewById(R.id.answerCardC);
        cardD = findViewById(R.id.answerCardD);

        // Đặt màu mặc định cho các thẻ đáp án
        resetMauDapAn();

        // Đọc các câu hỏi từ file JSON
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
        findViewById(R.id.btn_next).setOnClickListener(v -> {
            // Chuyển đến câu hỏi tiếp theo hoặc kết thúc quiz
            cauHoiHienTai++;
            if (cauHoiHienTai < dsCauHoi.size()) {
                hienThiCauHoi(dsCauHoi.get(cauHoiHienTai));
            } else {
                // Kết thúc quiz và chuyển về màn hình chính
                Intent intent = new Intent(QuizActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            // Reset trạng thái đã chọn sau mỗi câu hỏi
            daChonDapAn = false;
            resultText.setVisibility(View.INVISIBLE); // Ẩn kết quả khi chuyển câu hỏi mới
        });
    }

    private ArrayList<Question> loadCauHoiJSON() {
        ArrayList<Question> questions = new ArrayList<>();
        try {
            InputStream is = getResources().openRawResource(R.raw.questions);
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
        resultText.setVisibility(View.INVISIBLE);
    }

    private void resetMauDapAn() {
        cardA.setCardBackgroundColor(Color.parseColor("#BF5D9BA4"));
        cardB.setCardBackgroundColor(Color.parseColor("#BF5D9BA4"));
        cardC.setCardBackgroundColor(Color.parseColor("#BF5D9BA4"));
        cardD.setCardBackgroundColor(Color.parseColor("#BF5D9BA4"));
    }

    // Biến để lưu thẻ đáp án đã chọn trước đó
    private CardView dapAnChonTruocDo = null;

    private void xuLyChonDapAn(CardView selectedCard, String selectedAnswer) {
        if (daChonDapAn) {
            // Nếu người dùng đã chọn một đáp án, không cho phép chọn lại
            return;
        }

        // Reset màu của thẻ đáp án đã chọn trước đó (nếu có)
        if (dapAnChonTruocDo != null) {
            dapAnChonTruocDo.setCardBackgroundColor(Color.parseColor("#BF5D9BA4"));
        }

        // Đổi màu thẻ được chọn thành màu xanh lá
        selectedCard.setCardBackgroundColor(Color.GREEN);

        // Cập nhật thẻ được chọn hiện tại vào biến lưu trữ
        dapAnChonTruocDo = selectedCard;

        // Kiểm tra đáp án đúng
        Question currentQuestion = dsCauHoi.get(cauHoiHienTai);
        laDapAnDung = selectedAnswer.equals(currentQuestion.getCorrectAnswer());

        if (laDapAnDung) {
            // Đổi màu thẻ được chọn thành màu xanh lá khi đúng
            selectedCard.setCardBackgroundColor(Color.GREEN);
            resultText.setText("Correct");
            resultText.setTextColor(Color.GREEN);
        } else {
            // Đổi màu thẻ được chọn thành màu đỏ khi sai
            selectedCard.setCardBackgroundColor(Color.RED);
            resultText.setText("Wrong");
            resultText.setTextColor(Color.RED);
        }

        resultText.setVisibility(View.VISIBLE);

        // Đặt trạng thái đã chọn đáp án
        daChonDapAn = true;
    }
}
