package ntu.hieutm.GourmetBuddy;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    // Định nghĩa tên cơ sở dữ liệu và phiên bản
    private static final String DATABASE_NAME = "GourmetBuddy_app.db";
    private static final int DATABASE_VERSION = 2;

    // Định nghĩa tên bảng công thức món ăn
    public static final String TABLE_RECIPE = "recipe";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    // lấy tất cả các công thức món ăn từ cơ sở dữ liệu
    public List<Recipe> getAllRecipes() {
        // Tạo danh sách để lưu trữ các công thức món ăn
        List<Recipe> recipes = new ArrayList<>();
        // Mở cơ sở dữ liệu ở chế độ đọc
        SQLiteDatabase db = this.getReadableDatabase();

        // truy vấn để lấy tất cả các dòng từ bảng công thức món ăn
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_RECIPE, null);
        if (cursor.moveToFirst()) {
            do {
                // Lấy chỉ số cột cho từng trường
                int idIndex = cursor.getColumnIndex("recipe_id");
                int nameIndex = cursor.getColumnIndex("ten_mon");
                int categoryIndex = cursor.getColumnIndex("danh_muc");
                int difficultyIndex = cursor.getColumnIndex("do_kho");
                int timeIndex = cursor.getColumnIndex("thoi_gian");
                int servingsIndex = cursor.getColumnIndex("so_khau_phan");
                int instructionsIndex = cursor.getColumnIndex("huong_dan");
                int healthInfoIndex = cursor.getColumnIndex("phu_hop_suc_khoe");
                int imageUrlIndex = cursor.getColumnIndex("image_url");

                // Kiểm tra xem tất cả các chỉ số cột có hợp lệ không
                if (idIndex != -1 && nameIndex != -1 && categoryIndex != -1 && difficultyIndex != -1 &&
                        timeIndex != -1 && servingsIndex != -1 && instructionsIndex != -1 &&
                        healthInfoIndex != -1 && imageUrlIndex != -1) {

                    // Tạo một đối tượng Recipe và thêm vào danh sách
                    Recipe recipe = new Recipe(
                            cursor.getInt(idIndex),
                            cursor.getString(nameIndex),
                            cursor.getString(categoryIndex),
                            cursor.getString(difficultyIndex),
                            cursor.getInt(timeIndex),
                            cursor.getInt(servingsIndex),
                            cursor.getString(instructionsIndex),
                            cursor.getString(healthInfoIndex),
                            cursor.getString(imageUrlIndex)
                    );
                    recipes.add(recipe);
                }
            } while (cursor.moveToNext());
        }
        // Đóng con trỏ và cơ sở dữ liệu
        cursor.close();
        db.close();
        // Trả về danh sách các công thức món ăn
        return recipes;
    }
}
