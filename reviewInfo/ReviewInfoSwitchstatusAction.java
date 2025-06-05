package reviewInfo;

import bean.ReviewInfo;
import dao.ReviewInfoDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ReviewInfoSwitchstatusAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		
		//セッション情報を取得
		HttpSession session = request.getSession();
		
		//リクエストで受け取ったパラメーターをreviewInfoのBeanに格納
		ReviewInfo reviewInfo = new ReviewInfo();
		//boolean型に変換して格納
		reviewInfo.setReviewstatusflag(Boolean.parseBoolean(request.getParameter("reviewstatusflag")));
		//int型に変換して格納
		reviewInfo.setReviewcode(Integer.parseInt(request.getParameter("reviewcode")));
		
		
		ReviewInfoDAO dao = new ReviewInfoDAO();
		int success = dao.switchStatus(reviewInfo);
		
		//successが1だったら口コミ有効・無効成功画面(仮)へ遷移
		if (success == 1) {
			return "redirect:reviewinfoswitchstatus-finish.jsp";
		}
		
		//口コミ有効・無効エラーページ(仮)
		return "reviewinfoswithstatus-error.jsp";
	}

}
