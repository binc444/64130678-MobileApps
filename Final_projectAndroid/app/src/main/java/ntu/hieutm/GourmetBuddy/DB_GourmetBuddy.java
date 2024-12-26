package ntu.hieutm.GourmetBuddy;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB_GourmetBuddy extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SmartRecipeApp.db";
    private static final int DATABASE_VERSION = 1;

    // Các bảng
    public static final String TABLE_USER = "user";
    public static final String TABLE_HEALTH_PROFILE = "health_profile";
    public static final String TABLE_RECIPE = "recipe";
    public static final String TABLE_INGREDIENT = "ingredient";
    public static final String TABLE_RECIPE_INGREDIENT = "recipe_ingredient";
    public static final String TABLE_USER_RECIPE = "user_recipe";
    public static final String TABLE_SHARED_RECIPE = "shared_recipe";

    // Các cột của bảng `user`
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_TEN_DANG_NHAP = "ten_dang_nhap";
    public static final String COLUMN_MAT_KHAU = "mat_khau";
    public static final String COLUMN_HO_TEN = "ho_ten";

    // Các cột của bảng `health_profile`
    public static final String COLUMN_PROFILE_ID = "profile_id";
    public static final String COLUMN_USER_ID_FK = "user_id";
    public static final String COLUMN_CHIEU_CAO = "chieu_cao";
    public static final String COLUMN_CAN_NANG = "can_nang";
    public static final String COLUMN_TUOI = "tuoi";
    public static final String COLUMN_GIOI_TINH = "gioi_tinh";
    public static final String COLUMN_MUC_TIEU = "muc_tieu";
    public static final String COLUMN_TINH_TRANG_SUC_KHOE = "tinh_trang_suc_khoe";

    // Các cột của bảng `recipe`
    public static final String COLUMN_RECIPE_ID = "recipe_id";
    public static final String COLUMN_TEN_MON = "ten_mon";
    public static final String COLUMN_DANH_MUC = "danh_muc";
    public static final String COLUMN_DO_KHO = "do_kho";
    public static final String COLUMN_THOI_GIAN = "thoi_gian";
    public static final String COLUMN_SO_KHAU_PHAN = "so_khau_phan";
    public static final String COLUMN_HUONG_DAN = "huong_dan";
    public static final String COLUMN_CALORIES = "calories";
    public static final String COLUMN_PROTEIN = "protein";
    public static final String COLUMN_CARBS = "carbs";
    public static final String COLUMN_FATS = "fats";
    public static final String COLUMN_PHU_HOP_SUC_KHOE = "phu_hop_suc_khoe";
    public static final String COLUMN_IMAGE_URL = "image_url";

    // Các cột của bảng `ingredient`
    public static final String COLUMN_INGREDIENT_ID = "ingredient_id";
    public static final String COLUMN_TEN_NGUYEN_LIEU = "ten_nguyen_lieu";
    public static final String COLUMN_DON_VI = "don_vi";
    public static final String COLUMN_INGREDIENT_CALORIES = "calories";
    public static final String COLUMN_INGREDIENT_PROTEIN = "protein";
    public static final String COLUMN_INGREDIENT_CARBS = "carbs";
    public static final String COLUMN_INGREDIENT_FATS = "fats";

    // Các cột của bảng `recipe_ingredient`
    public static final String COLUMN_SO_LUONG = "so_luong";

    // Các cột của bảng `user_recipe`
    public static final String COLUMN_USER_RECIPE_ID = "user_recipe_id";
    public static final String COLUMN_TEN_MON_USER = "ten_mon";
    public static final String COLUMN_NGUYEN_LIEU = "nguyen_lieu";
    public static final String COLUMN_HUONG_DAN_USER = "huong_dan";
    public static final String COLUMN_QR_CODE = "qr_code";

    // Các cột của bảng `shared_recipe`
    public static final String COLUMN_SHARED_ID = "shared_id";
    public static final String COLUMN_SHARED_BY = "shared_by";

    // SQL tạo các bảng
    private static final String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + " ("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TEN_DANG_NHAP + " TEXT NOT NULL UNIQUE, "
            + COLUMN_MAT_KHAU + " TEXT NOT NULL, "
            + COLUMN_HO_TEN + " TEXT);";

    private static final String CREATE_HEALTH_PROFILE_TABLE = "CREATE TABLE " + TABLE_HEALTH_PROFILE + " ("
            + COLUMN_PROFILE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USER_ID_FK + " INTEGER, "
            + COLUMN_CHIEU_CAO + " FLOAT, "
            + COLUMN_CAN_NANG + " FLOAT, "
            + COLUMN_TUOI + " INTEGER, "
            + COLUMN_GIOI_TINH + " TEXT, "
            + COLUMN_MUC_TIEU + " TEXT, "
            + COLUMN_TINH_TRANG_SUC_KHOE + " TEXT, "
            + "FOREIGN KEY(" + COLUMN_USER_ID_FK + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "));";

    private static final String CREATE_RECIPE_TABLE = "CREATE TABLE " + TABLE_RECIPE + " ("
            + COLUMN_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TEN_MON + " TEXT NOT NULL, "
            + COLUMN_DANH_MUC + " TEXT, "
            + COLUMN_DO_KHO + " TEXT, "
            + COLUMN_THOI_GIAN + " INTEGER, "
            + COLUMN_SO_KHAU_PHAN + " INTEGER, "
            + COLUMN_HUONG_DAN + " TEXT, "
            + COLUMN_CALORIES + " FLOAT, "
            + COLUMN_PROTEIN + " FLOAT, "
            + COLUMN_CARBS + " FLOAT, "
            + COLUMN_FATS + " FLOAT, "
            + COLUMN_PHU_HOP_SUC_KHOE + " TEXT, "
            + COLUMN_IMAGE_URL + " TEXT);";

    private static final String CREATE_INGREDIENT_TABLE = "CREATE TABLE " + TABLE_INGREDIENT + " ("
            + COLUMN_INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TEN_NGUYEN_LIEU + " TEXT NOT NULL, "
            + COLUMN_DON_VI + " TEXT, "
            + COLUMN_INGREDIENT_CALORIES + " FLOAT, "
            + COLUMN_INGREDIENT_PROTEIN + " FLOAT, "
            + COLUMN_INGREDIENT_CARBS + " FLOAT, "
            + COLUMN_INGREDIENT_FATS + " FLOAT);";

    private static final String CREATE_RECIPE_INGREDIENT_TABLE = "CREATE TABLE " + TABLE_RECIPE_INGREDIENT + " ("
            + COLUMN_RECIPE_ID + " INTEGER, "
            + COLUMN_INGREDIENT_ID + " INTEGER, "
            + COLUMN_SO_LUONG + " FLOAT, "
            + "PRIMARY KEY(" + COLUMN_RECIPE_ID + ", " + COLUMN_INGREDIENT_ID + "), "
            + "FOREIGN KEY(" + COLUMN_RECIPE_ID + ") REFERENCES " + TABLE_RECIPE + "(" + COLUMN_RECIPE_ID + "), "
            + "FOREIGN KEY(" + COLUMN_INGREDIENT_ID + ") REFERENCES " + TABLE_INGREDIENT + "(" + COLUMN_INGREDIENT_ID + "));";

    private static final String CREATE_USER_RECIPE_TABLE = "CREATE TABLE " + TABLE_USER_RECIPE + " ("
            + COLUMN_USER_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USER_ID_FK + " INTEGER, "
            + COLUMN_TEN_MON_USER + " TEXT, "
            + COLUMN_NGUYEN_LIEU + " TEXT, "
            + COLUMN_HUONG_DAN_USER + " TEXT, "
            + COLUMN_QR_CODE + " TEXT, "
            + "FOREIGN KEY(" + COLUMN_USER_ID_FK + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "));";

    private static final String CREATE_SHARED_RECIPE_TABLE = "CREATE TABLE " + TABLE_SHARED_RECIPE + " ("
            + COLUMN_SHARED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_RECIPE_ID + " INTEGER, "
            + COLUMN_SHARED_BY + " INTEGER, "
            + COLUMN_QR_CODE + " TEXT, "
            + "FOREIGN KEY(" + COLUMN_RECIPE_ID + ") REFERENCES " + TABLE_RECIPE + "(" + COLUMN_RECIPE_ID + "), "
            + "FOREIGN KEY(" + COLUMN_SHARED_BY + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "));";

    public DB_GourmetBuddy(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_HEALTH_PROFILE_TABLE);
        db.execSQL(CREATE_RECIPE_TABLE);
        db.execSQL(CREATE_INGREDIENT_TABLE);
        db.execSQL(CREATE_RECIPE_INGREDIENT_TABLE);
        db.execSQL(CREATE_USER_RECIPE_TABLE);
        db.execSQL(CREATE_SHARED_RECIPE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHARED_RECIPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_RECIPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_INGREDIENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HEALTH_PROFILE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }
}
