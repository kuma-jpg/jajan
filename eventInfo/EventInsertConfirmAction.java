package eventInfo;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import tool.Action;
import tool.Validation;

public class EventInsertConfirmAction extends Action {

	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws Exception 
	{
		
		// フォームから送信されたデータを取得
        String eventname = request.getParameter("eventname");
        String eventdate = request.getParameter("eventdate");
        String eventlocation = request.getParameter("eventlocation");
        String eventtext = request.getParameter("eventtext");
        
        // 画像ファイルを保存する処理（ファイル名の取得）
        Part imagePart = request.getPart("image");

        //バリデーションチェックのメソッド呼び出し
         Validation v = new Validation();
	     
	     // imagePartがnullの場合、ファイルが送信されていない
	     if (imagePart == null) {
	         System.out.println("No file uploaded.");
	     } 
	     else {
	    	 
	         System.out.println("imagePart: " + imagePart);
	     }
        
        
        String imageFileName = imagePart != null ? getFileName(imagePart) : null;
        
        //エラー格納するマップを生成
		Map<String, String> errors = new HashMap<>();
        
        
        // 一時保存フォルダを用意（/temp）
        String tempPath =request.getServletContext().getRealPath("/temp");
        File tempDir = new File(tempPath);
        if (!tempDir.exists()) tempDir.mkdir();
        
        // ファイル名の重複防止（UUIDなど使う）
        String savedFileName = UUID.randomUUID().toString() + "_" + imageFileName;
        File tempFile = new File(tempPath, savedFileName);
        if (imageFileName != null && !imageFileName.isEmpty()) {
            imagePart.write(tempPath + File.separator + savedFileName);
          //画像データのバリデーションチェック
    		if (imagePart != null && imagePart.getSize() >10 * 1024 * 1024) {
    			errors.put("image", "エラー：ファイルサイズは10MB以内にしてください。");
    	
    		} else if (!v.hasAllowedExtension(imageFileName) && !v.isImageByMagicNumber(tempFile)) {
    			errors.put("image", "不正な画像ファイルです。JPEG/PNG/GIFのみ使用可能です。");
    		}
        }else {
        	savedFileName="noImage.jpg";
        }

        
       
        //イベント名のバリデーションチェック
		if (!v.isValidKeyword(eventname)) {
			errors.put("eventname", "イベント名は必須です");
		} else if (!v.isWithinLength(eventname, 1, 50)) {
			errors.put("eventname", "イベント名は1～50文字以内で入力してください。");
		} 

		//イベント日程のバリデーションチェック
		if (!v.isValidKeyword(eventdate)) {
			errors.put("eventdate", "イベント開始日時を入力してください。");
		}else {
			LocalDateTime dateTime = LocalDateTime.parse(eventdate);
	        // 日付フォーマットを指定
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	        // LocalDateTime を String に変換
	        eventdate=dateTime.format(formatter);
			
		}
		
		//イベント開催場所のバリデーションチェック
		if (!v.isValidKeyword(eventlocation)) {
			errors.put("eventlocation", "イベント開催場所は必須です");
		} 
		else if(!v.isWithinLength(eventlocation, 1, 50)) {
			errors.put("eventlocation", "イベント開催場所は50文字以内です");
		}
		
		//イベント内容のバリデーションチェック
		if (!v.isValidKeyword(eventtext)) {
			errors.put("eventtext", "イベント説明文は必須です。");
		} else if (!v.isWithinLength(eventtext, 1, 500)) {
			errors.put("eventtext", "イベント説明文は500字以内です");
		}
		
		// フォームから送信されたデータをリクエスト属性としてセット
        request.setAttribute("eventname", eventname);
        request.setAttribute("eventdate", eventdate);
        request.setAttribute("eventlocation", eventlocation);
        request.setAttribute("eventtext", eventtext);
        
		//エラーがあれば、ユーザーアカウント登録ページへ遷移
		if (!errors.isEmpty()) {
			//リクエストにセットする
			request.setAttribute("errors", errors);
			
			return "../admin/eventRegister.jsp";
		}
		
        
        // request属性に一時ファイル名も渡す
        request.setAttribute("imageFileName", savedFileName);
        request.setAttribute("imageTempPath", request.getContextPath() + "/temp/" + savedFileName); // JSP側で表示用
        
//        System.out.println("保存先: " + tempPath + File.separator + savedFileName);
//        System.out.println("eventname: " + eventname);
//        System.out.println("eventdate: " + eventdate);
//        System.out.println("eventlocation: " + eventlocation);
//        System.out.println("eventtext: " + eventtext);
//        System.out.println("imageTempPath: "+request.getContextPath() + "/temp/" + savedFileName);
//        

        // 確認画面へ転送
        return "/admin/eventRegisterConfirm.jsp";
    }
	
	
	
	//ファイル名の取得
	private String getFileName(Part part) {
        String contentDisposition = part.getHeader("Content-Disposition");
        for (String cd : contentDisposition.split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 2, cd.length() - 1);
            }
        }
        return null;
    }

}

