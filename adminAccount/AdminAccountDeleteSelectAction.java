package adminAccount;

import bean.AdminAccount;
import dao.AdminAccountDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

//選択したユーザーアカウントの情報を送る
public class AdminAccountDeleteSelectAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		
		String admincode = request.getParameter("admincode");
		
		System.out.println(admincode);
		
		AdminAccountDAO dao = new AdminAccountDAO();
		
		//検索して結果をリストに格納
		
		AdminAccount admin = new AdminAccount();
		try {
			admin = dao.searchCode(admincode);
		}catch (Exception e) {
			return "../admin/AdminAccountDelete-Error.jsp";
		}
		//useraccountが空ならエラーページへ遷移
		if (admin == null) {
			return "../admin/userAccountDelete-Error.jsp";
			
		}
		
		
		//ユーザーのデータをリクエスト属性としてセット
		request.setAttribute("cadmin", admin);
        
		//ユーザーアカウントの削除確認画面に遷移
		return "../admin/AdminAccountDeleteConfirm.jsp";
	}

}
