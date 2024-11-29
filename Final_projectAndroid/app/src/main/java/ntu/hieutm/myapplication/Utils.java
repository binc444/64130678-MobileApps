package ntu.hieutm.myapplication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    // Phương thức mã hóa mật khẩu với MD5
    public static String md5(String input) {
        try {
            // Tạo đối tượng MessageDigest với thuật toán MD5
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            // Tính toán băm của chuỗi đầu vào
            byte[] hashBytes = messageDigest.digest(input.getBytes());

            // Chuyển đổi kết quả thành chuỗi hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b)); // chuyển đổi thành chuỗi hex
            }

            return hexString.toString(); // Trả về mật khẩu đã mã hóa
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
