package adminAccount;

import bean.AdminAccount;
import dao.AdminAccountDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

//選択したユーザーアカウントの情報を送る
public class AdminAccountStatusSelectAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String admincode = request.getParameter("admincode");

		System.out.println(admincode);

		AdminAccountDAO dao = new AdminAccountDAO();

		//検索して結果をリストに格納

		AdminAccount admin = new AdminAccount();
		try {
			admin = dao.searchCode(admincode);
		} catch (Exception e) {
			return "../admin/adminAccountEditStatusError.jsp";
		}
		//useraccountが空ならエラーページへ遷移
		if (admin == null) {
			return "../admin/adminAccountEditStatusError.jsp";

		}

		System.out.println(admin.getAdminname());
		System.out.println(admin.getAdmincode());
		System.out.println(admin.getLoginid());

		//管理者のデータをリクエスト属性としてセット
		request.setAttribute("cadmin", admin);

		//ユーザーアカウントのステータス変更確認画面に遷移
		return "../admin/adminAccountEditStatusConfirm.jsp";
	}

}
