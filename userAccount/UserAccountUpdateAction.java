package userAccount;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import bean.UserAccount;
import dao.UserAccountDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

//ユーザーアカウント更新アクション
public class UserAccountUpdateAction extends Action {

	/**
	 *
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

		//セッション情報を取得
		HttpSession session = request.getSession();
		/*
				//入力チェッククラス生成
				Validation validation = new Validation();
		
				//入力チェックフラグ
				boolean isUserIdValid = true;
				boolean isPasswordValid = true;
				boolean isUserNameValid = true;
		*/
		//入力された値をローカルのString型で受け取る。
		String loginId = request.getParameter("loginid");
		String password = request.getParameter("password");
		String userName = request.getParameter("username");
		String usercode = request.getParameter("usercode");
		//System.out.println(usercode);
		/*	
			
			
		System.out.println("LoginID: " + loginId);
		System.out.println("Password: " + password);
		System.out.println("Username: " + userName);
			
			
			
			
			
			//入力エラーコード 0:正常 1:バリデーションエラー 2:重複エラー
			int loginIdCode = 0;
			int passwordCode = 0;
			int userNameCode = 0;
		
			// loginidのチェック
			if (!validation.isValidKeyword(loginId)) {
				isUserIdValid = false; // エラーがあった場合、フラグをfalseにする
				loginIdCode=1; //エラーコードを更新
			}
			if (!validation.isAlphanumeric(loginId)) {
				isUserIdValid = false;
				loginIdCode=1;
			}
			if (!validation.isWithinLength(loginId, 8, 16)) {
				isUserIdValid = false;
				loginIdCode=1;
			}
		
			// passwordのチェック
			if (!validation.isValidKeyword(password)) {
				isPasswordValid = false;
				passwordCode = 1;
			}
			if (!validation.isAlphanumeric(password)) {
				isPasswordValid = false;
				passwordCode = 1;
			}
			if (!validation.isWithinLength(password, 8, 16)) {
				isPasswordValid = false;
				passwordCode = 1;
			}
		
			// usernameのチェック
			if (!validation.isValidKeyword(userName)) {
				isUserNameValid = false;
				userNameCode = 1;
			}
			if (!validation.isWithinLength(userName, 0, 8)) {
				isUserNameValid = false;
				userNameCode = 1;
			}
			
			//入力チェック完了後、更新予定の文字列をサニタイズする
			loginId = validation.Sanitize(loginId);
			password = validation.Sanitize(password);
			userName = validation.Sanitize(userName);
		
			//重複チェックフラグ
			boolean isUserIdDuplicate = true;
			boolean isUserNameDuplicate = true;
		*/

		//ユーザーアカウントDAOクラス生成
		UserAccountDAO dao = new UserAccountDAO();

		/*
		//既存userIDと既存userNameとの重複チェック
		//自身のIDとニックネームは重複チェックから除外する必要あり。
		//ログインユーザーとログインネームを格納する
		UserAccount useraccount2 =(UserAccount)session.getAttribute("useraccount");
		String loginId2 = useraccount2.getLoginid();
		String userName2 = useraccount2.getUsername();
		int usercode2 = useraccount2.getUsercode();
		
		
		
		
		System.out.println("LoginID: " + loginId2);
		System.out.println("Username: " + userName2);
		
		
		
		
		
		
		//サニタイズ処理
		loginId2 = validation.Sanitize(loginId);
		userName2 = validation.Sanitize(userName);
		
		
		//ログインユーザーIDと変更したいログインユーザーIDが異なる場合、重複チェックを行う
		if (loginId != loginId2) {
			//ユーザーID重複チェックメソッド実行(重複あり：false 重複なし:true)
			isUserIdDuplicate = dao.checkUserLoginidDuplicate(loginId);
			if (!isUserIdDuplicate) {
				loginIdCode = 2; //エラーコードを更新(2:重複)
			}
		}
		
		//ログインユーザー名と変更したいログインユーザー名が異なる場合、重複チェックを行う
		if (userName != userName2) {
			//ユーザーネーム重複チェックメソッド実行(重複あり：false 重複なし:true)
			isUserNameDuplicate = dao.checkUserNameDuplicate(userName);
			if (!isUserNameDuplicate) {
				userNameCode = 2; //エラーコードを更新(2:重複)
			}
		}
		
		
		
		
		
		System.out.println("isUserIdValid: " + isUserIdValid);
		System.out.println("isPasswordValid: " + isPasswordValid);
		System.out.println("isUserNameValid: " + isUserNameValid);
		System.out.println("isUserIdDuplicate: " + isUserIdDuplicate);
		System.out.println("isUserNameDuplicate: " + isUserNameDuplicate);
		
		
		
		
		
		//入力チェックフラグor重複チェックフラグがfalseならエラーページへ遷移する。
		if (!isUserIdValid || !isPasswordValid || !isUserNameValid || !isUserIdDuplicate || !isUserNameDuplicate) {
			
			
			System.out.println("エラーページその１");
			System.out.println("loginIdCode:"+loginIdCode);
			System.out.println("passwordCode:"+passwordCode);
			System.out.println("userNameCode:"+userNameCode);
			
			
			//
			// 入力エラーページ(ユーザーアカウント編集)へ遷移する処理
			return "../user/editAccount.jsp"
		    + "?loginIdErrorCode=" + loginIdCode
		    + "&passwordErrorCode=" + passwordCode
		    + "&userNameErrorCode=" + userNameCode;
					
					
					
					
					//"../user/editAccount.jsp";
			
			
			
			
			
			//		+ "&loginIdErrorCode=loginIdCode&passwordErrorCode=passwordCode&userNameErrorCode=userNameCode";
		}
		*/
		//リクエストで受け取ったパラメーターをuseraccountのBeanに格納
		UserAccount useraccount = new UserAccount();
		useraccount.setLoginid(loginId);
		useraccount.setPassword(password);
		useraccount.setUsername(userName);
		//System.out.println(usercode);
		useraccount.setUsercode(Integer.parseInt(usercode));

		/*
		System.out.println("LoginID: " + loginId);
		System.out.println("Password: " + password);
		System.out.println("Username: " + userName);
		
		
		
		System.out.println("LoginID: " + useraccount.getLoginid());
		System.out.println("Password: " + useraccount.getPassword());
		System.out.println("Username: " + useraccount.getUsername());
		*/

		//
		try {
			// DB更新
			int success = dao.update(useraccount);

			// 更新成功時
			if (success == 1) {
				// セッション情報のユーザーアカウント更新
				session.setAttribute("useraccount", useraccount);
				return "redirect:../user/editComplete.jsp";
			}

		} catch (SQLIntegrityConstraintViolationException e) {
			// 重複エラー発生時（ユニーク制約違反など）
			return "../user/editAccountError.jsp";

		} catch (SQLException e) {
			// その他のSQLエラー発生時
			//e.printStackTrace(); // ログ出力（あとで調査しやすい）
			return "../user/editAccountEditError.jsp";
		}

		// ここまで来たら何らかの理由で更新失敗したとき
		return "../user/editAccountEditError.jsp";

	}

}
