package ntu.hieutm.GourmetBuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DAO {
    private SQLiteDatabase database;
    private DB_GourmetBuddy dbHelper;

    public DAO(Context context) {
        dbHelper = new DB_GourmetBuddy(context);
    }

    // Mở kết nối cơ sở dữ liệu
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    // Đóng kết nối cơ sở dữ liệu
    public void close() {
        dbHelper.close();
    }

    //Phương thức để xác thực người dùng
    public boolean xacThucNguoiDung(String username, String hashedPassword) {
        String selection = DB_GourmetBuddy.COLUMN_TEN_DANG_NHAP + " = ? AND " + DB_GourmetBuddy.COLUMN_MAT_KHAU + " = ?";
        String[] selectionArgs = {username, hashedPassword};

        Cursor cursor = database.query(
                DB_GourmetBuddy.TABLE_USER,
                new String[]{DB_GourmetBuddy.COLUMN_USER_ID},
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean daXacThuc = cursor.getCount() > 0;
        cursor.close();
        return daXacThuc;
    }


    // Phương thức chung để thêm dữ liệu vào bảng
    public long insertData(String tableName, ContentValues values) {
        return database.insert(tableName, null, values);
    }

    //thêm dữ liệu vào bảng `user`
    public long addUser(String tenDangNhap, String matKhau, String hoTen) {
        ContentValues values = new ContentValues();
        values.put(DB_GourmetBuddy.COLUMN_TEN_DANG_NHAP, tenDangNhap);
        values.put(DB_GourmetBuddy.COLUMN_MAT_KHAU, matKhau);
        values.put(DB_GourmetBuddy.COLUMN_HO_TEN, hoTen);

        return insertData(DB_GourmetBuddy.TABLE_USER, values);
    }

    //thêm dữ liệu vào bảng `health_profile`
    public long addHealthProfile(int userId, float chieuCao, float canNang, int tuoi, String gioiTinh, String mucTieu, String tinhTrangSucKhoe) {
        ContentValues values = new ContentValues();
        values.put(DB_GourmetBuddy.COLUMN_USER_ID_FK, userId);
        values.put(DB_GourmetBuddy.COLUMN_CHIEU_CAO, chieuCao);
        values.put(DB_GourmetBuddy.COLUMN_CAN_NANG, canNang);
        values.put(DB_GourmetBuddy.COLUMN_TUOI, tuoi);
        values.put(DB_GourmetBuddy.COLUMN_GIOI_TINH, gioiTinh);
        values.put(DB_GourmetBuddy.COLUMN_MUC_TIEU, mucTieu);
        values.put(DB_GourmetBuddy.COLUMN_TINH_TRANG_SUC_KHOE, tinhTrangSucKhoe);

        return insertData(DB_GourmetBuddy.TABLE_HEALTH_PROFILE, values);
    }

    //thêm dữ liệu vào bảng `recipe`
    public long addRecipe(String tenMon, String danhMuc, String doKho, int thoiGian, int soKhauPhan, String huongDan, float calories, float protein, float carbs, float fats, String phuHopSucKhoe, String imageUrl) {
        ContentValues values = new ContentValues();
        values.put(DB_GourmetBuddy.COLUMN_TEN_MON, tenMon);
        values.put(DB_GourmetBuddy.COLUMN_DANH_MUC, danhMuc);
        values.put(DB_GourmetBuddy.COLUMN_DO_KHO, doKho);
        values.put(DB_GourmetBuddy.COLUMN_THOI_GIAN, thoiGian);
        values.put(DB_GourmetBuddy.COLUMN_SO_KHAU_PHAN, soKhauPhan);
        values.put(DB_GourmetBuddy.COLUMN_HUONG_DAN, huongDan);
        values.put(DB_GourmetBuddy.COLUMN_CALORIES, calories);
        values.put(DB_GourmetBuddy.COLUMN_PROTEIN, protein);
        values.put(DB_GourmetBuddy.COLUMN_CARBS, carbs);
        values.put(DB_GourmetBuddy.COLUMN_FATS, fats);
        values.put(DB_GourmetBuddy.COLUMN_PHU_HOP_SUC_KHOE, phuHopSucKhoe);
        values.put(DB_GourmetBuddy.COLUMN_IMAGE_URL, imageUrl);

        return insertData(DB_GourmetBuddy.TABLE_RECIPE, values);
    }

    // Thêm nguyên liệu
    public long addIngredient(String tenNguyenLieu, String donVi, float calories, float protein, float carbs, float fats) {
        ContentValues values = new ContentValues();
        values.put(DB_GourmetBuddy.COLUMN_TEN_NGUYEN_LIEU, tenNguyenLieu);
        values.put(DB_GourmetBuddy.COLUMN_DON_VI, donVi);
        values.put(DB_GourmetBuddy.COLUMN_INGREDIENT_CALORIES, calories);
        values.put(DB_GourmetBuddy.COLUMN_INGREDIENT_PROTEIN, protein);
        values.put(DB_GourmetBuddy.COLUMN_INGREDIENT_CARBS, carbs);
        values.put(DB_GourmetBuddy.COLUMN_INGREDIENT_FATS, fats);

        return insertData(DB_GourmetBuddy.TABLE_INGREDIENT, values);
    }

    // Thêm liên kết giữa công thức và nguyên liệu
    public long addRecipeIngredient(int recipeId, int ingredientId, float soLuong) {
        ContentValues values = new ContentValues();
        values.put(DB_GourmetBuddy.COLUMN_RECIPE_ID, recipeId);
        values.put(DB_GourmetBuddy.COLUMN_INGREDIENT_ID, ingredientId);
        values.put(DB_GourmetBuddy.COLUMN_SO_LUONG, soLuong);

        return insertData(DB_GourmetBuddy.TABLE_RECIPE_INGREDIENT, values);
    }

    // Thêm công thức của người dùng
    public long addUserRecipe(int userId, String tenMon, String nguyenLieu, String huongDan, String qrCode) {
        ContentValues values = new ContentValues();
        values.put(DB_GourmetBuddy.COLUMN_USER_ID_FK, userId);
        values.put(DB_GourmetBuddy.COLUMN_TEN_MON_USER, tenMon);
        values.put(DB_GourmetBuddy.COLUMN_NGUYEN_LIEU, nguyenLieu);
        values.put(DB_GourmetBuddy.COLUMN_HUONG_DAN_USER, huongDan);

        return insertData(DB_GourmetBuddy.TABLE_USER_RECIPE, values);
    }

}
