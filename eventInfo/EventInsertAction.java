package eventInfo;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import bean.EventInfo;
import dao.EventInfoDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;
import tool.Validation;

public class EventInsertAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws Exception 
	{ 
		 // フォームデータを取得
		// フォームから送信されたデータを取得
        String eventname = request.getParameter("eventname");
        String eventdate = request.getParameter("eventdate");
        String eventlocation = request.getParameter("eventlocation");
        String eventtext = request.getParameter("eventtext");
        
        // imageFileName（hiddenから）を取得
        String imageFileName = request.getParameter("imageFileName");

        // 一時保存先のパス
        String tempPath = request.getServletContext().getRealPath("/temp");
        File tempFile = new File(tempPath, imageFileName);

        
        
		//エラー格納するマップを生成
		Map<String, String> errors = new HashMap<>();
		
		 //バリデーションチェックのメソッド呼び出し
        Validation v = new Validation();

		//画像データのバリデーションチェック
		if (!v.hasAllowedExtension(imageFileName) && !v.isImageByMagicNumber(tempFile)) {
			errors.put("image", "不正な画像ファイルです。JPEG/PNG/GIFのみ使用可能です。");
		}

		//イベント名のバリデーションチェック
		if (eventname == null || eventname.trim().isEmpty()) {
			errors.put("eventname", "イベント名は必須です");
		} else if (!v.isWithinLength(eventname, 1, 50)) {
			errors.put("eventname", "イベント名は1～50文字以内で入力してください。");
		}

		//イベント日程のバリデーションチェック
		if (eventdate == null || eventdate.trim().isEmpty()) {
			errors.put("eventdate", "イベント開始日時を入力してください。");
		}

		//イベント開催場所のバリデーションチェック
		if (eventlocation == null || eventlocation.trim().isEmpty()) {
			errors.put("eventlocation", "イベント開催場所は必須です");
		} else if (!v.isWithinLength(eventlocation, 1, 50)) {
			errors.put("eventlocation", "イベント開催場所は50文字以内です");
		}

		//イベント内容のバリデーションチェック
		if (eventtext == null || eventtext.trim().isEmpty()) {
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
        
		
		// 本保存先のパス
        String imagePath = request.getServletContext().getRealPath("/image");
        File imageDir = new File(imagePath);
        if (!imageDir.exists()) imageDir.mkdir();

        File savedFile = new File(imagePath, imageFileName);
        
        if(imageFileName != "noImage.jpg") {
        	// ファイルを移動
        	try {
        		Files.move(tempFile.toPath(), savedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        	}
	        catch (Exception e) {
	        	return "../admin/eventRegisterError.jsp";
			}
        }
        
        EventInfo event = new EventInfo();
        
        event.setEventname(eventname);
		event.setEventdate(eventdate);
		event.setEventlocation(eventlocation);
		event.setEventtext(eventtext);
		event.setImage(request.getContextPath() + "/image/" + imageFileName);
        
		EventInfoDAO dao = new EventInfoDAO();
		int success = dao.insert(event);

		//successが1だったらイベント登録成功画面へ遷移
		if (success == 1) {
			return "redirect:../admin/eventRegisterDone.jsp";
		}
		
		return "../admin/eventRegisterError.jsp";
	
	}


	
}
