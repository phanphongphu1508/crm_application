package util;

import java.util.Base64;

public class CookieUtils {
	// Mã hóa giá trị cookie thành Base64
	public static String encode(String value) {
		return Base64.getEncoder().encodeToString(value.getBytes());
	}

	// Giải mã Base64 thành giá trị ban đầu
	public static String decode(String encodedValue) {
		byte[] decodedBytes = Base64.getDecoder().decode(encodedValue);
		return new String(decodedBytes);
	}
}
