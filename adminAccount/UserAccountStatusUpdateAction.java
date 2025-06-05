package adminAccount;

import java.net.URLEncoder;

import bean.UserAccount;
import dao.UserAccountDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;


public class UserAccountStatusUpdateAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String usercode = request.getParameter("usercode");
		String username = request.getParameter("username");

		System.out.println(usercode);

		UserAccountDAO dao = new UserAccountDAO();

		//検索して結果をリストに格納

		UserAccount user = new UserAccount();
		
		boolean flag=false;
		
		try {
			user=dao.searchCode(usercode);
			
			if(user.isAccountstatusflag()) {
				flag = dao.OffStatus(user);
				if(flag==true) {user.setAccountstatusflag(false);}
			}
			else{
				flag = dao.OnStatus(user);
				if(flag==true) {user.setAccountstatusflag(true);}
			}
		} catch (Exception e) {
			return "../admin/userAccountEditStatusError.jsp";
		}
		//useraccountが空ならエラーページへ遷移
		if (flag == false) {
			return "../admin/userAccountEditStatusError.jsp";

		}

		System.out.println(user.getUsername());
		System.out.println(user.getUsercode());
		System.out.println(user.getLoginid());
		
		//ユーザーアカウントのステータス変更完了画面に遷移
		return "redirect:../admin/userAccountEditStatusComplete.jsp?username=" +
				URLEncoder.encode(username, "UTF-8") 
				+"&userflag=" + user.isAccountstatusflag()
				+ "&accountname=" + URLEncoder.encode(user.getUsername(),"UTF-8") ;
	}

}
