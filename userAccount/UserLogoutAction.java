package userAccount;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class UserLogoutAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws Exception 
	{
		HttpSession session = request.getSession();
		
		if(session.getAttribute("useraccount") != null) {
			session.removeAttribute("useraccount");
			return "redirect:../user/top.jsp";
		}
		
		return "../user/logout-error.jsp";
	}

}
