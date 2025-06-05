package adminAccount;

import java.util.ArrayList;
import java.util.List;

import bean.AdminAccount;
import dao.AdminAccountDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class AdminAccountSearchAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws Exception 
	{
		HttpSession session = request.getSession();
		
		String adminname = request.getParameter("adminname");
		AdminAccountDAO dao = new AdminAccountDAO();
		
		//検索して結果をリストに格納
		List<AdminAccount> adminsearchlist = new ArrayList<>();
		
		adminsearchlist = dao.search(adminname);
		
		session.setAttribute("adminsearchlist", adminsearchlist);
		
		return "../admin/adminAccountEdit.jsp";
	}

}
