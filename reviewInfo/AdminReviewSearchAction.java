package reviewInfo;

import java.util.ArrayList;
import java.util.List;

import bean.ReviewInfo;
import dao.ReviewInfoDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class AdminReviewSearchAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws Exception 
	{
		HttpSession session = request.getSession();
		
		String eventcode = request.getParameter("eventcode");
		
		//keyword = Sanitize(keyword);
		
		ReviewInfoDAO dao = new ReviewInfoDAO();
		
		//検索して結果をリストに格納
		List<ReviewInfo> reviewlist = new ArrayList<>();
		
		reviewlist = dao.UserSearch(eventcode);
		
		session.setAttribute("reviewlist", reviewlist);
		
		return "../admin/Reviews.jsp?eventcode=eventcode";
	}

}
