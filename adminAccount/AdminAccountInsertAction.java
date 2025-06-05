package adminAccount;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import bean.AdminAccount;
import dao.AdminAccountDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

//ユーザーアカウント新規登録アクション
public class AdminAccountInsertAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

		
		//リクエストで受け取ったパラメーターをuseraccountのBeanに格納
		AdminAccount adminaccount = new AdminAccount();
		adminaccount.setLoginid(request.getParameter("loginid"));
		adminaccount.setPassword(request.getParameter("password"));
		adminaccount.setAdminname(request.getParameter("adminname"));
		
		try {

			//userAccountDAOのinsertメソッド実行
			//返り値0:失敗、返り値1:成功　としてint型のsuccessで受け取る。
			AdminAccountDAO dao = new AdminAccountDAO();
			int success = dao.insert(adminaccount);

			//successが1だったらユーザーアカウント登録成功画面(仮)へ遷移
			if (success == 1) {
				return "redirect:../admin/adminAccountRegisterComplete.jsp";
			}

		} catch (SQLIntegrityConstraintViolationException e) {
			// ここで重複エラーをキャッチ！
			//System.out.println("ログインIDが既に使われています: " + e.getMessage());
			//ユーザーアカウント登録失敗画面(仮)へ遷移
			return "../admin/adminAccountRegisterError.jsp";

		} catch (SQLException e) {
			// その他のSQLエラー
			//e.printStackTrace();
		}
		
		return "../admin/adminAccountRegisterError.jsp";
	}

}
