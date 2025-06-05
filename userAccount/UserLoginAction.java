package userAccount;

import bean.UserAccount;
import dao.UserAccountDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class UserLoginAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws Exception 
	{
		HttpSession session = request.getSession();
		
		//ログインの情報を格納
		String login = request.getParameter("loginid");
		String password = request.getParameter("password");
		UserAccountDAO dao = new UserAccountDAO();
		
		//ログイン認証
		UserAccount useraccount = dao.login(login, password);
		
		if(useraccount != null) {
			session.setAttribute("useraccount", useraccount);
			
			// セッション有効時間を60分に延長（秒単位）
	        session.setMaxInactiveInterval(60 * 60);
			
	        //ログイン完了画面へ遷移
	        return "redirect:../user/guestLoginSuccess.jsp";
		}
		
		return "redirect:../user/loginError.jsp";
	}

}
