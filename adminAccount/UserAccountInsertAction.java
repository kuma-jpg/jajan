package adminAccount;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import bean.UserAccount;
import dao.UserAccountDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

//ユーザーアカウント新規登録アクション
public class UserAccountInsertAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

		
		//リクエストで受け取ったパラメーターをuseraccountのBeanに格納
		UserAccount useraccount = new UserAccount();
		useraccount.setLoginid(request.getParameter("loginid"));
		useraccount.setPassword(request.getParameter("password"));
		useraccount.setUsername(request.getParameter("username"));

		try {

			//userAccountDAOのinsertメソッド実行
			//返り値0:失敗、返り値1:成功　としてint型のsuccessで受け取る。
			UserAccountDAO dao = new UserAccountDAO();
			int success = dao.insert(useraccount);

			//successが1だったらユーザーアカウント登録成功画面(仮)へ遷移
			if (success == 1) {
				return "redirect:../admin/userAccountRegisterComplete.jsp";
			}

		} catch (SQLIntegrityConstraintViolationException e) {
			// ここで重複エラーをキャッチ！
			//System.out.println("ログインIDが既に使われています: " + e.getMessage());
			//ユーザーアカウント登録失敗画面(仮)へ遷移
			return "../admin/userAccountRegisterError.jsp";

		} catch (SQLException e) {
			// その他のSQLエラー
			//e.printStackTrace();
		}
		
		return "../admin/userAccountRegister.jsp";
	}

}
