package tool;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Validation {
	
	 //空データかどうかを判定
	 public boolean isValidKeyword(String keyword) {
	        return keyword != null && !keyword.trim().isEmpty();
	    }
	 //英数字かどうかを判定
	 public boolean isAlphanumeric(String keyword) {
	        return keyword != null && keyword.matches("^[a-zA-Z0-9 ]+$");
	 }
	 
	 //入力文字数を判定
	 public boolean isWithinLength(String keyword,int minLength , int maxLength) 
	 {
		 return keyword != null && keyword.length() >= minLength && keyword.length() <= maxLength;
	 }
	
	 //サニタイズ処理
	 public String Sanitize(String input) 
	 {
		    if (input == null) return null;
		    return input
		        .replace("&", "&amp;")
		        .replace("<", "&lt;")
		        .replace(">", "&gt;")
		        .replace("\"", "&quot;")
		        .replace("'", "&#x27;");
	}
	 
	//ファイルの拡張子の判定
	public boolean hasAllowedExtension(String filename) 
	{
	    String lower = filename.toLowerCase();
	    return lower.endsWith(".jpg") || lower.endsWith(".jpeg") ||
	           lower.endsWith(".png") || lower.endsWith(".gif");
    }
	
	//バイナリー形式でファイルを判定
	public boolean isImageByMagicNumber(File file) throws IOException {
		
	    try (var is = Files.newInputStream(file.toPath())) {
	        byte[] header = new byte[8];
	        int read = is.read(header);
	        if (read < 3) return false;

	        // JPEGならここ
	        if (header[0] == (byte)0xFF && header[1] == (byte)0xD8 && header[2] == (byte)0xFF) {
	            return true;
	        }
	        // PNGならここ
	        if (read >= 8 &&
	            header[0] == (byte)0x89 && header[1] == 0x50 &&
	            header[2] == 0x4E && header[3] == 0x47 &&
	            header[4] == 0x0D && header[5] == 0x0A &&
	            header[6] == 0x1A && header[7] == 0x0A) {
	            return true;
	        }
	        // GIFデータならここ
	        if (read >= 6 &&
	            header[0] == 0x47 && header[1] == 0x49 && header[2] == 0x46 &&
	            header[3] == 0x38 &&
	            (header[4] == 0x39 || header[4] == 0x37) &&
	            header[5] == 0x61) {
	            return true;
	        }

	        return false;
	    }
	}

}
