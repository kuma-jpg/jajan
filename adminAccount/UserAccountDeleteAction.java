package adminAccount;

import bean.UserAccount;
import dao.UserAccountDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

//ユーザーアカウント更新アクション
public class UserAccountDeleteAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String usercode = request.getParameter("usercode");
		
		System.out.println("削除のメソッドのコード:" + usercode);
		
		//ユーザーアカウントの存在を検索
		UserAccountDAO dao = new UserAccountDAO();
		
		//useraccountのBeanに検索結果を収納
		UserAccount useraccount = dao.searchCode(usercode);
		
		
		//useraccountが空ならエラーページへ遷移
		if (useraccount == null) {
			System.out.println("検索に引っかからないよ！");
			return "redirect:../admin/userAccountEditMyPageDelete-Error.jsp";
		}
		
		if(!dao.delete(useraccount)) {
			System.out.println("削除のメソッドでエラーだったよ！");
			return "redirect:../admin/userAccountEditMyPageDelete-Error.jsp";
		}
		
		//ユーザーアカウントの削除完了画面に遷移
		return "redirect:../admin/userAccountEditMyPageDelete-Error.jsp";
	}

}
