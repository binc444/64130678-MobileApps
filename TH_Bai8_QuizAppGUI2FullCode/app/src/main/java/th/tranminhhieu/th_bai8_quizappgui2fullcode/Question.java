package th.tranminhhieu.th_bai8_quizappgui2fullcode;

import java.util.ArrayList;

public class Question {
    private String question;
    private ArrayList<String> options;
    private String correctAnswer;

    public Question(String question, ArrayList<String> options, String correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
