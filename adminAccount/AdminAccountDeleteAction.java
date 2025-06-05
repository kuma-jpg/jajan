package adminAccount;

import bean.AdminAccount;
import dao.AdminAccountDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

//ユーザーアカウント更新アクション
public class AdminAccountDeleteAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String admincode = request.getParameter("admincode");
		
		System.out.println("削除のメソッドのコード:" + admincode);
		
		//ユーザーアカウントの存在を検索
		AdminAccountDAO dao = new AdminAccountDAO();
		
		//useraccountのBeanに検索結果を収納
		AdminAccount adminaccount = dao.searchCode(admincode);
		
		
		//useraccountが空ならエラーページへ遷移
		if (adminaccount == null) {
			System.out.println("検索に引っかからないよ！");
			return "redirect:../admin/userAccountEditMyPageDelete-Error.jsp";
		}
		
		if(!dao.delete(adminaccount)) {
			System.out.println("削除のメソッドでエラーだったよ！");
			return "redirect:../admin/userAccountEditMyPageDelete-Error.jsp";
		}
		
		//ユーザーアカウントの削除完了画面に遷移
		return "redirect:../admin/AdminAccountDelete-complete.jsp";
	}

}
