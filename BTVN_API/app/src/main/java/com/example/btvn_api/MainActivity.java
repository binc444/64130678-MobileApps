package com.example.btvn_api;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText txtInput;
    private TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtInput = findViewById(R.id.txt_input);
        txtResult = findViewById(R.id.txt_result);
        Button btnSearch = findViewById(R.id.btn_search);
        Button btnClose = findViewById(R.id.btn_close);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL_input = txtInput.getText().toString().trim();
                if (!URL_input.isEmpty()) {
                    getAPI(URL_input);
                } else {
                    txtResult.setText("Vui lòng nhập vào URL API!!!!!!!!");
                }
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Đóng ứng dụng
            }
        });
    }
    private void getAPI(String URL_input) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(URL_input);
                    HttpURLConnection cnect = (HttpURLConnection) url.openConnection();
                    cnect.setRequestMethod("GET");

                    int responseCode = cnect.getResponseCode();
                    if (responseCode == 200) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(cnect.getInputStream()));
                        String inputLine;
                        StringBuilder response = new StringBuilder();

                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();

                        // Hiển thị kết quả
                        runOnUiThread(() -> txtResult.setText(response.toString()));
                    } else {
                        runOnUiThread(() -> txtResult.setText("Lỗi: " + responseCode));
                    }
                } catch (Exception e) {
                    runOnUiThread(() -> txtResult.setText("Lỗi khi gọi API: " + e.getMessage()));
                }
            }
        }).start();
    }

}