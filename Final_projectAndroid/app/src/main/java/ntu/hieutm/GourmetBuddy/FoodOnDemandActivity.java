package ntu.hieutm.GourmetBuddy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class FoodOnDemandActivity extends AppCompatActivity {

    // Khai báo các thành phần giao diện
    private RadioGroup radioGroupDoKho, radioGroupDanhMuc;
    private Button btnSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_on_demand);

        radioGroupDoKho = findViewById(R.id.rg_difficulty);
        radioGroupDanhMuc = findViewById(R.id.rg_health_category);
        btnSelect = findViewById(R.id.btn_select);

        // Đặt sự kiện khi người dùng nhấn nút "Select"
        btnSelect.setOnClickListener(v -> {
            String doKhoDuocChon = null;
            String danhMucDuocChon = null;

            // Lấy ID của RadioButton được chọn trong RadioGroup "Độ Khó"
            int doKhoDuocChonId = radioGroupDoKho.getCheckedRadioButtonId();
            // Lấy ID của RadioButton được chọn trong RadioGroup "Danh Mục"
            int danhMucDuocChonId = radioGroupDanhMuc.getCheckedRadioButtonId();

            // Kiểm tra xem RadioButton nào được chọn và lấy giá trị của nó
            if (doKhoDuocChonId != -1) {
                RadioButton rbDifficulty = findViewById(doKhoDuocChonId);
                doKhoDuocChon = rbDifficulty.getText().toString();
            }

            if (danhMucDuocChonId != -1) {
                RadioButton rbCategory = findViewById(danhMucDuocChonId);
                danhMucDuocChon = rbCategory.getText().toString();
            }

            // Kiểm tra xem người dùng có chọn ít nhất một tùy chọn hay không
            if (doKhoDuocChon == null && danhMucDuocChon == null) {
                Toast.makeText(this, "Vui lòng chọn ít nhất một tùy chọn!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Lấy danh sách món ăn phù hợp từ database
            List<Recipe> filteredRecipes = filterMonAn(doKhoDuocChon, danhMucDuocChon);

            // Kiểm tra xem có món ăn nào phù hợp hay không
            if (filteredRecipes.isEmpty()) {
                Toast.makeText(this, "Không tìm thấy món ăn phù hợp!", Toast.LENGTH_SHORT).show();
            } else {
                // Chuyển sang ListRecipes activity và truyền danh sách món ăn
                Intent intent = new Intent(FoodOnDemandActivity.this, ListRecipes.class);
                intent.putParcelableArrayListExtra("recipes", new ArrayList<>(filteredRecipes));
                startActivity(intent);
            }
        });
    }

    // Phương thức lọc món ăn dựa trên độ khó và danh mục
    private List<Recipe> filterMonAn(String difficulty, String category) {
        // Khởi tạo DBHelper để truy cập cơ sở dữ liệu
        DBHelper dbHelper = new DBHelper(this);
        // Lấy tất cả các công thức món ăn từ cơ sở dữ liệu
        List<Recipe> allRecipes = dbHelper.getAllRecipes();

        // Tạo danh sách lưu trữ các công thức món ăn phù hợp
        List<Recipe> dsMonAnDaLoc = new ArrayList<>();
        for (Recipe recipe : allRecipes) {
            // Kiểm tra điều kiện phù hợp về độ khó và danh mục
            boolean matchesDifficulty = difficulty == null || recipe.getDifficulty().equalsIgnoreCase(difficulty);
            boolean matchesCategory = category == null || recipe.getCategory().equalsIgnoreCase(category);

            // Thêm công thức món ăn vào danh sách nếu phù hợp
            if (matchesDifficulty && matchesCategory) {
                dsMonAnDaLoc.add(recipe);
            }
        }
        return dsMonAnDaLoc;
    }
}
