package adminAccount;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import dao.UserAccountDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;
import tool.Validation;

public class UserAccountValidateAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

		//セッション情報を取得
		//HttpSession session = request.getSession();
		//UserAccount useraccount = new UserAccount();

		//入力内容を取得する
		String loginid = request.getParameter("loginid");
		String password = request.getParameter("password");
		String username = request.getParameter("username");

		//エラー格納するマップを生成
		Map<String, String> errors = new HashMap<>();

		//バリデーションのインスタンスを生成
		Validation vali = new Validation();

		try {
			//userAccountDAO生成
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

			//リクエストにセットする
			request.setAttribute("errors", errors);
			request.setAttribute("loginid", loginid);
			request.setAttribute("password", password);
			request.setAttribute("username", username);

			//エラーがなければユーザーアカウント登録確認ページへ遷移
			if (errors.isEmpty()) {
				//ユーザーアカウント登録確認ページへ遷移
				return "../admin/userAccountRegisterConfirm.jsp";
			} else {

				//エラーがあれば、ユーザーアカウント登録ページへ遷移
				return "../admin/userAccountRegister.jsp";
			}

		} catch (SQLException e) {
			//ユーザーアカウント登録エラーページへ遷移
			//return "../user/registerError.jsp";
		}

		//ユーザーアカウント登録ページへ遷移(戻る) (エラーがあれば)
		return "../admin/userAccountRegister.jsp";
	}

}
