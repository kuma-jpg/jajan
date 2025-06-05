package adminAccount;

import bean.UserAccount;
import dao.UserAccountDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

//選択したユーザーアカウントの情報を送る
public class UserAccountStatusSelectAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String usercode = request.getParameter("usercode");

		System.out.println(usercode);

		UserAccountDAO dao = new UserAccountDAO();

		//検索して結果をリストに格納

		UserAccount user = new UserAccount();
		try {
			user = dao.searchCode(usercode);
		} catch (Exception e) {
			return "../admin/userAccountEditStatusError.jsp";
		}
		//useraccountが空ならエラーページへ遷移
		if (user == null) {
			return "../admin/userAccountEditStatusError.jsp";

		}

		System.out.println(user.getUsername());
		System.out.println(user.getUsercode());
		System.out.println(user.getLoginid());

		//ユーザーのデータをリクエスト属性としてセット
		request.setAttribute("cuser", user);

		//ユーザーアカウントのステータス変更確認画面に遷移
		return "../admin/userAccountEditStatusConfirm.jsp";
	}

}
