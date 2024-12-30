package ntu.hieutm.GourmetBuddy;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class FoodForHealthActivity extends AppCompatActivity {

    // Kết nối cơ sở dữ liệu
    private DB_GourmetBuddy dbConnect;

    // Danh sách hiển thị nguyên liệu
    private ListView lvIngredients;

    // Adapter để quản lý danh sách nguyên liệu
    private IngredientAdapter adapter;

    // Danh sách các nguyên liệu
    private List<Ingredient> dsNguyenLieu;

    // HashMap lưu trạng thái chọn của các nguyên liệu (true nếu được chọn)
    private HashMap<Integer, Boolean> nguyenLieuDuocChon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_for_health);

        // Khởi tạo các thành phần
        dbConnect = new DB_GourmetBuddy(this);
        lvIngredients = findViewById(R.id.lv_list_Ingredients);
        dsNguyenLieu = new ArrayList<>();
        nguyenLieuDuocChon = new HashMap<>();

        // Tải danh sách nguyên liệu từ cơ sở dữ liệu
        loadDanhSachNguyenLieu();

        // Cài đặt adapter cho ListView
        adapter = new IngredientAdapter(this, dsNguyenLieu);
        lvIngredients.setAdapter(adapter);

        // Nút tìm món ăn phù hợp dựa trên nguyên liệu
        Button btnTimMonAn = findViewById(R.id.btn_tim_mon_an);
        btnTimMonAn.setOnClickListener(v -> timMonAnPhuHop());
    }

    // Tải danh sách nguyên liệu từ cơ sở dữ liệu SQLite
    private void loadDanhSachNguyenLieu() {
        SQLiteDatabase db = dbConnect.getReadableDatabase();
        Cursor cursor = db.query(DB_GourmetBuddy.TABLE_INGREDIENT, null, null, null, null, null, null);

        // Duyệt qua từng hàng dữ liệu để thêm vào danh sách nguyên liệu
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DB_GourmetBuddy.COLUMN_INGREDIENT_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DB_GourmetBuddy.COLUMN_TEN_NGUYEN_LIEU));

            // Mặc định hình ảnh là một drawable
            Ingredient ingredient = new Ingredient(id, name, R.drawable.list_menu);
            dsNguyenLieu.add(ingredient);

            // Mặc định nguyên liệu chưa được chọn
            nguyenLieuDuocChon.put(id, false);
        }
        cursor.close();
    }

    // Tìm món ăn phù hợp dựa trên nguyên liệu được chọn
    private void timMonAnPhuHop() {
        // Lấy danh sách ID của nguyên liệu được chọn
        List<Integer> ingredientIds = nguyenLieuDuocChon.entrySet().stream()
                .filter(entry -> entry.getValue())
                .map(HashMap.Entry::getKey)
                .collect(Collectors.toList());

        // Nếu không chọn nguyên liệu nào, thông báo lỗi
        if (ingredientIds.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn ít nhất một nguyên liệu!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Truy vấn cơ sở dữ liệu để tìm món ăn phù hợp
        SQLiteDatabase db = dbConnect.getReadableDatabase();
        List<Recipe> matchingRecipes = new ArrayList<>();

        // Truy vấn SQL với các nguyên liệu được chọn
        String query = "SELECT DISTINCT r.* FROM " + DB_GourmetBuddy.TABLE_RECIPE + " r "
                + "INNER JOIN " + DB_GourmetBuddy.TABLE_RECIPE_INGREDIENT + " ri "
                + "ON r." + DB_GourmetBuddy.COLUMN_RECIPE_ID + " = ri." + DB_GourmetBuddy.COLUMN_RECIPE_ID
                + " WHERE ri." + DB_GourmetBuddy.COLUMN_INGREDIENT_ID + " IN ("
                + makePlaceholders(ingredientIds.size()) + ")";

        // Chuyển danh sách ID thành mảng chuỗi cho truy vấn
        String[] args = ingredientIds.stream().map(String::valueOf).toArray(String[]::new);
        Cursor cursor = db.rawQuery(query, args);

        // Thêm kết quả truy vấn vào danh sách món ăn
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DB_GourmetBuddy.COLUMN_RECIPE_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DB_GourmetBuddy.COLUMN_TEN_MON));
            String category = cursor.getString(cursor.getColumnIndexOrThrow(DB_GourmetBuddy.COLUMN_DANH_MUC));
            String difficulty = cursor.getString(cursor.getColumnIndexOrThrow(DB_GourmetBuddy.COLUMN_DO_KHO));
            int time = cursor.getInt(cursor.getColumnIndexOrThrow(DB_GourmetBuddy.COLUMN_THOI_GIAN));
            int servings = cursor.getInt(cursor.getColumnIndexOrThrow(DB_GourmetBuddy.COLUMN_SO_KHAU_PHAN));
            String instructions = cursor.getString(cursor.getColumnIndexOrThrow(DB_GourmetBuddy.COLUMN_HUONG_DAN));
            String healthInfo = cursor.getString(cursor.getColumnIndexOrThrow(DB_GourmetBuddy.COLUMN_PHU_HOP_SUC_KHOE));
            String imageUrl = cursor.getString(cursor.getColumnIndexOrThrow(DB_GourmetBuddy.COLUMN_IMAGE_URL));

            matchingRecipes.add(new Recipe(id, name, category, difficulty, time, servings, instructions, healthInfo, imageUrl));
        }

        cursor.close();

        // Nếu không có món ăn nào phù hợp, hiển thị thông báo
        if (matchingRecipes.isEmpty()) {
            Toast.makeText(this, "Không tìm thấy món ăn phù hợp!", Toast.LENGTH_SHORT).show();
        } else {
            // Chuyển sang màn hình hiển thị danh sách món ăn
            showRecipeList(matchingRecipes);
        }
    }

    // Tạo chuỗi placeholder cho câu truy vấn SQL
    private String makePlaceholders(int count) {
        return new String(new char[count]).replace("\0", "?,").replaceAll(",$", "");
    }

    // Hiển thị danh sách món ăn
    private void showRecipeList(List<Recipe> recipes) {
        Intent intent = new Intent(this, ListRecipes.class);
        intent.putParcelableArrayListExtra("recipes", new ArrayList<>(recipes));
        startActivity(intent);
    }

    // Lớp để biểu diễn một nguyên liệu
    private class Ingredient {
        private int id;
        private String name;
        private int imageResource;

        public Ingredient(int id, String name, int imageResource) {
            this.id = id;
            this.name = name;
            this.imageResource = imageResource;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getImageResource() {
            return imageResource;
        }
    }

    // Adapter quản lý hiển thị danh sách nguyên liệu
    private class IngredientAdapter extends BaseAdapter {
        private Context context;
        private List<Ingredient> ingredients;

        public IngredientAdapter(Context context, List<Ingredient> ingredients) {
            this.context = context;
            this.ingredients = ingredients;
        }

        @Override
        public int getCount() {
            return ingredients.size();
        }

        @Override
        public Object getItem(int position) {
            return ingredients.get(position);
        }

        @Override
        public long getItemId(int position) {
            return ingredients.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.ingredient_list_item, parent, false);
            }

            // Lấy thông tin nguyên liệu
            Ingredient ingredient = ingredients.get(position);

            // Liên kết view với dữ liệu
            ImageView ivImage = convertView.findViewById(R.id.iv_anh_nguyenlieu);
            TextView tvName = convertView.findViewById(R.id.tv_IngredientName);

            ivImage.setImageResource(ingredient.getImageResource());
            tvName.setText(ingredient.getName());

            convertView.setTag(ingredient.getId());

            // Xử lý sự kiện chọn nguyên liệu
            convertView.setOnClickListener(v -> {
                int ingredientId = (int) v.getTag();
                doiMauItem(v, ingredientId);
            });

            return convertView;
        }
    }

    // Đổi màu item khi được chọn
    private void doiMauItem(View convertView, int ingredientId) {
        boolean duocChon = nguyenLieuDuocChon.get(ingredientId);

        if (duocChon) {
            convertView.setBackgroundColor(Color.parseColor("#FFD700")); // Màu vàng khi bỏ chọn
        } else {
            convertView.setBackgroundColor(Color.GREEN); // Màu xanh khi chọn
            convertView.animate().alpha(1f).setDuration(300).start(); // Hiệu ứng chọn
        }

        // Cập nhật trạng thái chọn của nguyên liệu
        nguyenLieuDuocChon.put(ingredientId, !duocChon);
    }
}
