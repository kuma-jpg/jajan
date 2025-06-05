package reviewInfo;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import bean.ReviewInfo;
import dao.ReviewInfoDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ReviewInfoUpdateAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		
		//セッション情報を取得
		HttpSession session = request.getSession();
		

		//リクエストで受け取ったパラメータをreviewInfoのbeanに格納
		ReviewInfo reviewInfo = new ReviewInfo();
		reviewInfo.setReviewcode(Integer.parseInt(request.getParameter("reviewcode")));
		reviewInfo.setEventcode(Integer.parseInt(request.getParameter("eventcode")));
		reviewInfo.setUsercode(Integer.parseInt(request.getParameter("usercode")));
		reviewInfo.setReviewtext(request.getParameter("reviewtext"));
		reviewInfo.setReviewstatusflag(Boolean.parseBoolean(request.getParameter("reviewstatusflag")));

		try {
			//reviewInfoDAOのinsertメソッド実行
			//返り値0:失敗、返り値1:成功　としてint型のsuccessで受け取る。
			ReviewInfoDAO dao = new ReviewInfoDAO();
			int success = dao.update(reviewInfo);

			//successが1だったら口コミ登録成功画面(仮)へ遷移
			if (success == 1) {
				return "redirect:../ReviewInfo/reveiwupdate-success.jsp";
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			// ここで重複エラーをキャッチ！
			//System.out.println("ログインIDが既に使われています: " + e.getMessage());
			//ユーザーアカウント登録失敗画面(仮)へ遷移
			return "../ReviewInfo/reveiwupdate-error.jsp";

		} catch (SQLException e) {

		}

		//成功でない場合失敗ページ(仮)へ遷移
		return "../ReviewInfo/reveiwupdate-error.jsp";
	}

}

		
		
