package eventInfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/image/*")
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024,     // 1MB
	    maxFileSize = 5 * 1024 * 1024,       // 5MB
	    maxRequestSize = 10 * 1024 * 1024    // 10MB
	)
public class TempImageServlet2 extends HttpServlet {
	
	 @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	        // リクエストパスから画像ファイル名を取得
	        String filename = request.getPathInfo().substring(1); // 例: "abc.jpg"
	        String tempPath = getServletContext().getRealPath("/image"); // image フォルダの実際のパスを取得
	        File file = new File(tempPath, filename);

	        if (!file.exists()) {
	            response.sendError(HttpServletResponse.SC_NOT_FOUND);
	            return;
	        }
	        

	        // MIMEタイプを設定してファイルをレスポンスとして送信
	        response.setContentType(getServletContext().getMimeType(filename));
	        Files.copy(file.toPath(), response.getOutputStream());
	    }

}
