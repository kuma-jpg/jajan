package adminAccount;

import bean.AdminAccount;
import dao.AdminAccountDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class AdminLoginAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws Exception 
	{
		HttpSession session = request.getSession();
		
		String login = request.getParameter("loginid");
		String password = request.getParameter("password");
		AdminAccountDAO dao = new AdminAccountDAO();
		
		//ログイン認証
		AdminAccount adminaccount = dao.login(login, password);
		
		if(adminaccount != null) {
			session.setAttribute("adminaccount", adminaccount);
			
			// セッション有効時間を60分に延長（秒単位）
	        session.setMaxInactiveInterval(60 * 60);
			
			return "redirect:../admin/adminMyPage.jsp";
		}
		
		return "../admin/adminLoginError.jsp";
	}

}
