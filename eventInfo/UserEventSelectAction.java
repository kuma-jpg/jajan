package eventInfo;

import bean.EventInfo;
import dao.EventInfoDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

//選択したユーザーアカウントの情報を送る
public class UserEventSelectAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		
		HttpSession session = request.getSession();
		
		String eventcode = request.getParameter("eventcode");
		
		
		EventInfoDAO dao = new EventInfoDAO();
		
		//検索して結果をリストに格納
		
		EventInfo selectevent = new EventInfo();
		selectevent = dao.searchCode(eventcode);
		
		session.setAttribute("selectevent", selectevent);
		
        return "../user/EventDeteail.jsp";
	}

}
