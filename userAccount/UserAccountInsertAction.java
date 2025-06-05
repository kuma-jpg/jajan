package userAccount;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

import bean.UserAccount;
import dao.UserAccountDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;
import tool.Validation;

//ユーザーアカウント新規登録アクション
public class UserAccountInsertAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

		//セッション情報を取得
		HttpSession session = request.getSession();

		//リクエストで受け取ったパラメーターをuseraccountのBeanに格納
		UserAccount useraccount = new UserAccount();
		String loginid = request.getParameter("loginid");
		String password = request.getParameter("password");
		String username = request.getParameter("username");
		
		//エラー格納するマップを生成
		Map<String, String> errors = new HashMap<>();

		//バリデーションのインスタンスを生成
		Validation vali = new Validation();
		
		try {

			UserAccountDAO dao = new UserAccountDAO();
			//ログインIDのバリデーションチェック
			if (loginid == null || loginid.trim().isEmpty()) {
				errors.put("loginid", "ログインIDは必須です。");
			} else if (loginid.length() < 8 || loginid.length() > 16) {
				errors.put("loginid", "ログインIDは8～16文字で入力してください。");
			} else if (!vali.isAlphanumeric(loginid)) {
				errors.put("loginid", "不正な文字が入っています。");
			} else if (!dao.checkUserLoginidDuplicate(loginid)) {
				errors.put("loginid", "このログインIDはすでに使用されています。");
			}
	
			//パスワードのバリデーションチェック
			if (password == null || password.length() < 8 || password.length() > 16) {
				errors.put("password", "パスワードは8～16文字で入力してください。");
			} else if (!vali.isAlphanumeric(password)) {
				errors.put("password", "不正な文字が入っています。");
			}
	
			//ニックネームのバリデーションチェック
			if (username == null || username.trim().isEmpty()) {
				errors.put("username", "ニックネームは必須です。");
			} else if (!dao.checkUserNameDuplicate(username)) {
				errors.put("username", "このニックネームはすでに使用されています。");
			}
			
			
			//エラーがあれば、ユーザーアカウント登録ページへ遷移
			if (!errors.isEmpty()) {
				//リクエストにセットする
				request.setAttribute("errors", errors);
				request.setAttribute("loginid", loginid);
				request.setAttribute("password", password);
				request.setAttribute("username", username);
				
				return "../user/register.jsp";
			}

			
			//バリデーションチェック後のデータをuseraccountに格納
			useraccount.setLoginid(loginid);
			useraccount.setPassword(password);
			useraccount.setUsername(username);
	
			//userAccountDAOのinsertメソッド実行
			//返り値0:失敗、返り値1:成功　としてint型のsuccessで受け取る。
			
			int success = dao.insert(useraccount);

			//successが1だったらユーザーアカウント登録成功画面(仮)へ遷移
			if (success == 1) {
				useraccount = dao.login(loginid, password);
				session.setAttribute("useraccount", useraccount);
				
				return "redirect:../user/registerComplete.jsp";
			}

		} catch (SQLIntegrityConstraintViolationException e) {
			// ここで重複エラーをキャッチ！
			//System.out.println("ログインIDが既に使われています: " + e.getMessage());
			//ユーザーアカウント登録失敗画面(仮)へ遷移
			return "../user/registerError.jsp";

		} catch (SQLException e) {
			// その他のSQLエラー
			//e.printStackTrace();
		}
		
		return "../user/register.jsp";
	}

}
