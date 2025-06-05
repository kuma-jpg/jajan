package adminAccount;

import java.util.ArrayList;
import java.util.List;

import bean.EventInfo;
import dao.EventInfoDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class AdminEventSearchAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws Exception 
	{
		HttpSession session = request.getSession();
		
		String keyword = request.getParameter("keyword");
		
		//keyword = Sanitize(keyword);
		
		EventInfoDAO dao = new EventInfoDAO();
		
		//検索して結果をリストに格納
		List<EventInfo> eventlist = new ArrayList<>();
		
		eventlist = dao.adminSearch(keyword);
		
		session.setAttribute("eventlist", eventlist);
		
		return "../admin/adminEventSearch.jsp";
	}
}
