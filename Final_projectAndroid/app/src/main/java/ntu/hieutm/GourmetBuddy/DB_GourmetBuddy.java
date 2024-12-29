package ntu.hieutm.GourmetBuddy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB_GourmetBuddy extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "GourmetBuddy_app.db";
    private static final int DATABASE_VERSION = 2;

    // Các bảng
    public static final String TABLE_USER = "user";
    public static final String TABLE_RECIPE = "recipe";
    public static final String TABLE_INGREDIENT = "ingredient";
    public static final String TABLE_RECIPE_INGREDIENT = "recipe_ingredient";
    public static final String TABLE_USER_RECIPE = "user_recipe";

    // Các cột của bảng `user`
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_TEN_DANG_NHAP = "ten_dang_nhap";
    public static final String COLUMN_MAT_KHAU = "mat_khau";
    public static final String COLUMN_HO_TEN = "ho_ten";
    public static final String COLUMN_NGAY_SINH = "ngay_sinh"; // Ngày sinh
    public static final String COLUMN_GIOI_TINH = "gioi_tinh"; // Giới tính

    // Các cột của bảng `recipe`
    public static final String COLUMN_RECIPE_ID = "recipe_id";
    public static final String COLUMN_TEN_MON = "ten_mon";
    public static final String COLUMN_DANH_MUC = "danh_muc";
    public static final String COLUMN_DO_KHO = "do_kho";
    public static final String COLUMN_THOI_GIAN = "thoi_gian";
    public static final String COLUMN_SO_KHAU_PHAN = "so_khau_phan";
    public static final String COLUMN_HUONG_DAN = "huong_dan";
    public static final String COLUMN_PHU_HOP_SUC_KHOE = "phu_hop_suc_khoe";
    public static final String COLUMN_IMAGE_URL = "image_url";

    // Các cột của bảng `ingredient`
    public static final String COLUMN_INGREDIENT_ID = "ingredient_id";
    public static final String COLUMN_TEN_NGUYEN_LIEU = "ten_nguyen_lieu";
    public static final String COLUMN_DON_VI = "don_vi";

    // Các cột của bảng `recipe_ingredient`
    public static final String COLUMN_SO_LUONG = "so_luong";

    // Các cột của bảng `user_recipe`
    public static final String COLUMN_USER_RECIPE_ID = "user_recipe_id";
    public static final String COLUMN_USER_ID_FK = "user_id";
    public static final String COLUMN_TEN_MON_USER = "ten_mon";
    public static final String COLUMN_NGUYEN_LIEU = "nguyen_lieu";
    public static final String COLUMN_HUONG_DAN_USER = "huong_dan";

    // SQL tạo các bảng
    private static final String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + " ("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TEN_DANG_NHAP + " TEXT NOT NULL UNIQUE, "
            + COLUMN_MAT_KHAU + " TEXT NOT NULL, "
            + COLUMN_HO_TEN + " TEXT, "
            + COLUMN_NGAY_SINH + " TEXT, " // Thêm ngày sinh
            + COLUMN_GIOI_TINH + " TEXT);"; // Thêm giới tính

    private static final String CREATE_RECIPE_TABLE = "CREATE TABLE " + TABLE_RECIPE + " ("
            + COLUMN_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TEN_MON + " TEXT NOT NULL, "
            + COLUMN_DANH_MUC + " TEXT, "
            + COLUMN_DO_KHO + " TEXT, "
            + COLUMN_THOI_GIAN + " INTEGER, "
            + COLUMN_SO_KHAU_PHAN + " INTEGER, "
            + COLUMN_HUONG_DAN + " TEXT, "
            + COLUMN_PHU_HOP_SUC_KHOE + " TEXT, "
            + COLUMN_IMAGE_URL + " TEXT);"; // Bỏ calories, protein, carbs, fats

    private static final String CREATE_INGREDIENT_TABLE = "CREATE TABLE " + TABLE_INGREDIENT + " ("
            + COLUMN_INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TEN_NGUYEN_LIEU + " TEXT NOT NULL, "
            + COLUMN_DON_VI + " TEXT);"; // Bỏ calories, protein, carbs, fats

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
            + "FOREIGN KEY(" + COLUMN_USER_ID_FK + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "));";

    public DB_GourmetBuddy(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_RECIPE_TABLE);
        db.execSQL(CREATE_INGREDIENT_TABLE);
        db.execSQL(CREATE_RECIPE_INGREDIENT_TABLE);
        db.execSQL(CREATE_USER_RECIPE_TABLE);

        // Thêm món ăn
        db.execSQL("INSERT INTO " + TABLE_RECIPE + " (ten_mon, danh_muc, do_kho, thoi_gian, so_khau_phan, huong_dan, phu_hop_suc_khoe, image_url) VALUES" +
                "('Phở Bò', 'Gia đình', 'Trung bình', 120, 4, 'Ninh xương bò, luộc bò, chuẩn bị rau thơm và bánh phở, trụng bánh phở, đổ nước dùng lên trên, thêm thịt bò, rau thơm.', 'Không phù hợp cho người bị tiểu đường', 'pho_bo')," +
                "('Bánh Mì', 'Ăn chay', 'Dễ', 15, 1, 'Nướng bánh mì, chuẩn bị nhân rau củ như dưa leo, cà chua, xà lách, thêm sốt mayonnaise hoặc bơ.', 'Phù hợp cho mọi người', 'banh_mi')," +
                "('Cơm Tấm', 'Gia đình', 'Trung bình', 60, 2, 'Nấu cơm tấm, chuẩn bị sườn nướng, chả trứng, bì và nước mắm chua ngọt.', 'Không phù hợp cho người ăn chay', 'com_tam')," +
                "('Gỏi Cuốn', 'Giảm cân', 'Dễ', 30, 4, 'Chuẩn bị bánh tráng, tôm, thịt heo luộc, bún, rau thơm. Cuộn tất cả nguyên liệu và ăn kèm nước chấm.', 'Phù hợp cho mọi người', 'goi_cuon')," +
                "('Bún Chả', 'Gia đình', 'Trung bình', 90, 4, 'Ướp thịt heo với gia vị, nướng than, nấu nước chấm, ăn kèm bún và rau sống.', 'Không phù hợp cho người bị tiểu đường', 'bun_cha')," +
                "('Chả Cá Lã Vọng', 'Gia đình', 'Trung bình', 60, 4, 'Ướp cá với nghệ và thì là, chiên cá, ăn kèm bún và mắm tôm.', 'Không phù hợp cho người bị dị ứng hải sản', 'cha_ca')," +
                "('Bánh Xèo', 'Gia đình', 'Trung bình', 45, 4, 'Pha bột bánh xèo, chiên bánh với nhân tôm, thịt, giá đỗ, ăn kèm rau sống và nước mắm.', 'Không phù hợp cho người bị tiểu đường', 'banh_xeo')," +
                "('Cá Kho Tộ', 'Gia đình', 'Trung bình', 60, 4, 'Kho cá với nước mắm, đường, tiêu và hành, ăn kèm cơm trắng.', 'Không phù hợp cho người bị cao huyết áp', 'ca_kho_to')," +
                "('Nem Rán', 'Gia đình', 'Trung bình', 45, 4, 'Gói nem với nhân thịt, mộc nhĩ, miến, cà rốt, chiên vàng và ăn kèm nước chấm.', 'Không phù hợp cho người bị tiểu đường', 'nem_ran')," +
                "('Bánh Cuốn', 'Gia đình', 'Dễ', 30, 4, 'Hấp bột bánh, cuốn nhân thịt băm, hành phi, ăn kèm nước mắm chua ngọt.', 'Phù hợp cho mọi người', 'banh_cuon')," +
                "('Canh Chua Cá', 'Gia đình', 'Trung bình', 40, 4, 'Nấu canh chua với cá, me, cà chua, dứa, và các loại rau.', 'Không phù hợp cho người bị dị ứng hải sản', 'canh_chua_ca')," +
                "('Lẩu Thái', 'Gia đình', 'Nâng cao', 120, 6, 'Chuẩn bị nước lẩu cay, thêm tôm, mực, thịt bò, rau và nấm, ăn kèm bún hoặc mì.', 'Không phù hợp cho người bị dị ứng hải sản', 'lau_thai')," +
                "('Bò Kho', 'Gia đình', 'Trung bình', 90, 4, 'Hầm bò với cà rốt, hành tây, nêm gia vị, ăn kèm bánh mì hoặc cơm.', 'Không phù hợp cho người bị tiểu đường', 'bo_kho')," +
                "('Chè Ba Màu', 'Gia đình', 'Dễ', 30, 4, 'Nấu chè với đậu xanh, đậu đỏ, thạch rau câu, thêm nước cốt dừa và đá.', 'Không phù hợp cho người bị tiểu đường', 'che_ba_mau')," +
                "('Salad Rau Củ', 'Ăn chay', 'Dễ', 15, 2, 'Trộn các loại rau củ như cà chua, dưa leo, xà lách, thêm dầu olive và giấm.', 'Phù hợp cho mọi người', 'salad_rau_cu')," +
                "('Súp Đậu Hủ', 'Ăn chay', 'Dễ', 30, 4, 'Nấu súp với đậu hủ, nấm, rau củ như cà rốt và hành tây, thêm gia vị.', 'Phù hợp cho mọi người', 'sup_dau_hu')," +
                "('Cháo Yến Mạch', 'Giảm cân', 'Dễ', 20, 1, 'Nấu yến mạch với nước, thêm hoa quả và một ít mật ong.', 'Phù hợp cho người muốn giảm cân', 'chao_yen_mach')," +
                "('Gà Nướng Xả', 'Tăng cơ', 'Trung bình', 40, 2, 'Ướp gà với xả, tỏi, tiêu, nướng chín, ăn kèm với rau xanh.', 'Phù hợp cho người muốn tăng cơ', 'ga_nuong_xa')," +
                "('Trứng Luộc', 'Tăng cơ', 'Dễ', 10, 1, 'Luộc trứng, ăn kèm với rau xanh hoặc một ít bánh mì nguyên cám.', 'Phù hợp cho người muốn tăng cơ', 'trung_luoc')," +
                "('Súp Cà Rốt', 'Giảm cân', 'Dễ', 30, 2, 'Nấu cà rốt với nước luộc, thêm gia vị và nêm vừa ăn.', 'Phù hợp cho người muốn giảm cân', 'sup_ca_rot');");

        // Thêm nguyên liệu
        db.execSQL("INSERT INTO " + TABLE_INGREDIENT + " (ten_nguyen_lieu, don_vi) VALUES" +
                "('Thịt bò', 'kg')," +
                "('Xương heo', 'kg')," +
                "('Bánh phở', 'gói')," +
                "('Rau thơm', 'bó')," +
                "('Bún', 'kg')," +
                "('Tôm', 'kg')," +
                "('Thịt heo', 'kg')," +
                "('Bột bánh xèo', 'gói')," +
                "('Giá đỗ', 'bó')," +
                "('Cá', 'kg')," +
                "('Đậu đỏ', 'kg')," +
                "('Nước cốt dừa', 'ml')," +
                "('Đậu hủ', 'kg')," +
                "('Cà rốt', 'kg')," +
                "('Xà lách', 'kg')," +
                "('Dầu olive', 'ml')," +
                "('Yến mạch', 'kg')," +
                "('Gà', 'kg')," +
                "('Tỏi', 'kg')," +
                "('Nấm', 'kg')," +
                "('Trứng', 'quả');");

        // Thêm công thức
        db.execSQL("INSERT INTO " + TABLE_RECIPE_INGREDIENT + " (recipe_id, ingredient_id, so_luong) VALUES" +
                "(1, 1, 0.5)," +
                "(1, 2, 0.5)," +
                "(1, 3, 1)," +
                "(1, 4, 1)," +
                "(2, 5, 0.5)," +
                "(2, 6, 0.2)," +
                "(2, 7, 0.3)," +
                "(14, 12, 0.3), " +
                "(14, 13, 0.2), " +
                "(14, 14, 0.1), " +
                "(14, 15, 0.05), " +
                "(15, 12, 0.2), " +
                "(15, 16, 0.1), " +
                "(15, 17, 0.1), " +
                "(15, 18, 0.05), " +
                "(16, 17, 0.1), " +
                "(16, 19, 0.05), " +
                "(16, 20, 0.05), " +
                "(17, 19, 0.3), " +
                "(17, 20, 0.05), " +
                "(17, 18, 0.05), " +
                "(18, 12, 0.1), " +
                "(18, 13, 0.05), " +
                "(18, 14, 0.05), " +
                "(19, 13, 0.3), " +
                "(19, 12, 0.1);");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_RECIPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_INGREDIENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }
}
