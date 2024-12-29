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

    // Thêm dữ liệu vào bảng `user`
    public long addUser(String tenDangNhap, String matKhau, String hoTen, String ngaySinh, String gioiTinh) {
        ContentValues values = new ContentValues();
        values.put(DB_GourmetBuddy.COLUMN_TEN_DANG_NHAP, tenDangNhap);
        values.put(DB_GourmetBuddy.COLUMN_MAT_KHAU, matKhau);
        values.put(DB_GourmetBuddy.COLUMN_HO_TEN, hoTen);
        values.put(DB_GourmetBuddy.COLUMN_NGAY_SINH, ngaySinh);
        values.put(DB_GourmetBuddy.COLUMN_GIOI_TINH, gioiTinh);

        return insertData(DB_GourmetBuddy.TABLE_USER, values);
    }


    // Thêm dữ liệu vào bảng `recipe`
    public long addRecipe(String tenMon, String danhMuc, String doKho, int thoiGian, int soKhauPhan, String huongDan, String phuHopSucKhoe, String imageUrl) {
        ContentValues values = new ContentValues();
        values.put(DB_GourmetBuddy.COLUMN_TEN_MON, tenMon);
        values.put(DB_GourmetBuddy.COLUMN_DANH_MUC, danhMuc);
        values.put(DB_GourmetBuddy.COLUMN_DO_KHO, doKho);
        values.put(DB_GourmetBuddy.COLUMN_THOI_GIAN, thoiGian);
        values.put(DB_GourmetBuddy.COLUMN_SO_KHAU_PHAN, soKhauPhan);
        values.put(DB_GourmetBuddy.COLUMN_HUONG_DAN, huongDan);
        values.put(DB_GourmetBuddy.COLUMN_PHU_HOP_SUC_KHOE, phuHopSucKhoe);
        values.put(DB_GourmetBuddy.COLUMN_IMAGE_URL, imageUrl);

        return insertData(DB_GourmetBuddy.TABLE_RECIPE, values);
    }


    // Thêm nguyên liệu
    public long addIngredient(String tenNguyenLieu, String donVi) {
        ContentValues values = new ContentValues();
        values.put(DB_GourmetBuddy.COLUMN_TEN_NGUYEN_LIEU, tenNguyenLieu);
        values.put(DB_GourmetBuddy.COLUMN_DON_VI, donVi);

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
    public long addUserRecipe(int userId, String tenMon, String nguyenLieu, String huongDan) {
        ContentValues values = new ContentValues();
        values.put(DB_GourmetBuddy.COLUMN_USER_ID_FK, userId);
        values.put(DB_GourmetBuddy.COLUMN_TEN_MON_USER, tenMon);
        values.put(DB_GourmetBuddy.COLUMN_NGUYEN_LIEU, nguyenLieu);
        values.put(DB_GourmetBuddy.COLUMN_HUONG_DAN_USER, huongDan);

        return insertData(DB_GourmetBuddy.TABLE_USER_RECIPE, values);
    }

    // Lấy thông tin người dùng theo ID
    public Cursor getUserInfo(int userId) {
        String selection = DB_GourmetBuddy.COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        // Truy vấn thông tin người dùng từ bảng user
        return database.query(
                DB_GourmetBuddy.TABLE_USER,
                new String[]{DB_GourmetBuddy.COLUMN_TEN_DANG_NHAP, DB_GourmetBuddy.COLUMN_HO_TEN, DB_GourmetBuddy.COLUMN_NGAY_SINH, DB_GourmetBuddy.COLUMN_GIOI_TINH},
                selection,
                selectionArgs,
                null,
                null,
                null
        );
    }
}
