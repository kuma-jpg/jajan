package adminAccount;

import java.net.URLEncoder;

import bean.AdminAccount;
import dao.AdminAccountDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;


public class AdminAccountStatusUpdateAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String admincode = request.getParameter("admincode");
		String adminname = request.getParameter("adminname");

		System.out.println(admincode);

		AdminAccountDAO dao = new AdminAccountDAO();

		//検索して結果をリストに格納

		AdminAccount admin = new AdminAccount();
		
		boolean flag=false;
		
		try {
			admin = dao.searchCode(admincode);
			
			if(admin.isAccountstatusflag()) {
				flag = dao.OffStatus(admin);
				if(flag==true) {admin.setAccountstatusflag(false);}
			}
			else{
				flag = dao.OnStatus(admin);
				if(flag==true) {admin.setAccountstatusflag(true);}
			}
		} catch (Exception e) {
			return "../admin/AccountEditStatusError.jsp";
		}
		//useraccountが空ならエラーページへ遷移
		if (flag == false) {
			return "../admin/userAccountEditStatusError.jsp";

		}

		System.out.println(admin.getAdminname());
		System.out.println(admin.getAdmincode());
		System.out.println(admin.getLoginid());
		
		//ユーザーアカウントのステータス変更完了画面に遷移
		return "redirect:../admin/adminAccountEditStatusComplete.jsp?adminname=" +
				URLEncoder.encode(adminname, "UTF-8") 
				+"&accountflag=" + admin.isAccountstatusflag()
				+ "&accountname=" + URLEncoder.encode(admin.getAdminname(),"UTF-8") ;
	}

}
