package userAccount;

import bean.UserAccount;
import dao.UserAccountDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

//ユーザー退会処理アクション
public class UserAccountLeaveAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		
		//セッション情報を取得
		HttpSession session = request.getSession();
		UserAccount useraccount2 =(UserAccount)session.getAttribute("useraccount");

		
		//リクエストで受け取ったパラメーターをuseraccountのBeanに格納
		UserAccount useraccount = new UserAccount();
		//boolean型に変換して格納
		//useraccount.setAccountstatusflag(Boolean.parseBoolean(request.getParameter("accountstatusflag")));
		//int型に変換して格納
		//useraccount.setUsercode(Integer.parseInt(request.getParameter("usercode")));
		useraccount.setUsercode(useraccount2.getUsercode());
		
		System.out.println("usercode:"+useraccount.getUsercode());
		
		//userAccountDAOのleaveメソッド実行
		//返り値0:失敗、返り値1:成功　としてint型のsuccessで受け取る。
		UserAccountDAO dao = new UserAccountDAO();
		int success = dao.leave(useraccount);
		
		//successが1だったらユーザーアカウント退会成功画面(仮)へ遷移
		if (success == 1) {
			//セッションのユーザ情報を空にする
			//UserAccount useraccount3 = new UserAccount();
			//session.setAttribute("useraccount", useraccount3);
			session.removeAttribute("useraccount");
			
			return "redirect:../user/deleteAccountSuccess.jsp";
		}
		
		//ユーザーアカウント退会失敗画面(仮)へ遷移
		return "../user/mypage.jsp";
	}

}
