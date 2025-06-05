package reviewInfo;

import bean.ReviewInfo;
import dao.ReviewInfoDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;
import tool.Validation;

public class ReviewInfoInsertAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

		//セッション情報を取得
		HttpSession session = request.getSession();

		//リクエストで受け取ったパラメータをreviewInfoのbeanに格納
		//ここに記載のない変数はDAOのinsertメソッドで自動生成
		ReviewInfo reviewInfo = new ReviewInfo();
		String reviewtext = request.getParameter("reviewtext");
		int eventcode = Integer.parseInt(request.getParameter("eventcode"));
		int usercode = Integer.parseInt(request.getParameter("usercode"));
		
		Validation v = new Validation();
		if(!(v.isValidKeyword(reviewtext) && v.isWithinLength(reviewtext, 1, 140))) 
		{
			//エラー内容をリクエストにセット
			request.setAttribute("error", "レビューは1文字以上140文字以内に入力してください。");
			request.setAttribute("eventcode",eventcode);
			return "../user/reviewinsert.jsp";
		}
		
		reviewInfo.setEventcode(eventcode);
		
		reviewInfo.setUsercode(usercode);	
		
		reviewInfo.setReviewtext(reviewtext);

		//reviewInfoDAOのinsertメソッド実行
		//返り値0:失敗、返り値1:成功　としてint型のsuccessで受け取る。
		ReviewInfoDAO dao = new ReviewInfoDAO();
		int success = dao.insert(reviewInfo);
		//successが1だったら口コミ登録成功画面(仮)へ遷移
		if (success == 1)
		{
			return "redirect:../user/reveiwinsert-success.jsp";
		}
		
		//成功でない場合失敗ページ(仮)へ遷移
		return "../user/reveiwinsert-error.jsp";
	}

}
