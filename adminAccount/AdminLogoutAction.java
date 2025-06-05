package adminAccount;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class AdminLogoutAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws Exception 
	{
		HttpSession session = request.getSession();
		
		if(session.getAttribute("adminaccount") != null) {
		
			return "redirect:../admin/adminLogin.jsp";
		}
		
		return "../admin/adminLogoutError.jsp";
	}

}
