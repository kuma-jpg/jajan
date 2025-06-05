package adminAccount;

import java.util.ArrayList;
import java.util.List;

import bean.UserAccount;
import dao.UserAccountDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class AdminUserSearchAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws Exception 
	{
		HttpSession session = request.getSession();
		
		String username = request.getParameter("username");
		UserAccountDAO dao = new UserAccountDAO();
		
		//検索して結果をリストに格納
		List<UserAccount> usersearchlist = new ArrayList<>();
		
		usersearchlist = dao.search(username);
		
		session.setAttribute("usersearchlist", usersearchlist);
		
		return "../admin/userAccountEdit.jsp";
	}

}
