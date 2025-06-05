package userAccount;

import bean.UserAccount;
import dao.UserAccountDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

//ユーザーアカウント有効・無効アクション
public class UserAccountSwitchstatusAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

			
			//セッション情報を取得
			HttpSession session = request.getSession();
			
			//リクエストで受け取ったパラメーターをuseraccountのBeanに格納
			UserAccount useraccount = new UserAccount();
			//boolean型に変換して格納
			useraccount.setAccountstatusflag(Boolean.parseBoolean(request.getParameter("accountstatusflag")));
			//int型に変換して格納
			useraccount.setUsercode(Integer.parseInt(request.getParameter("usercode")));
			
			//userAccountDAOのleaveメソッド実行
			//返り値0:失敗、返り値1:成功　としてint型のsuccessで受け取る。
			UserAccountDAO dao = new UserAccountDAO();
			int success = dao.switchStatus(useraccount);
			
			//successが1だったらユーザーアカウント有効・無効成功画面(仮)へ遷移
			if (success == 1) {
				return "redirect:useraccountswitchstatus-finish.jsp";
			}
			
			//ユーザーアカウント有効・無効失敗画面(仮)へ遷移
			return "useraccountswithstatus-error.jsp";
		}

}
