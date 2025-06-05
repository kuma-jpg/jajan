package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import bean.AdminAccount;
import tool.Validation;

public class AdminAccountDAO extends Dao {
	
	//管理者アカウントを追加するメソッド
	public int insert(AdminAccount adminAccount) throws Exception{
		//DB追加件数の初期化
		int line = 0;
		
		//DB接続とsql文の設定
		try(Connection con = getConnection();
		PreparedStatement st=con.prepareStatement(
				"insert into adminaccount(loginid, password, adminname,accountstatusflag) values(?,?,?,true);"
				);){
		
		//ユーザーアカウントBeanからそれぞれの値をstのsql文に格納
		st.setString(1, adminAccount.getLoginid());
		st.setString(2, adminAccount.getPassword());
		st.setString(3, adminAccount.getAdminname());
		//失敗したら0,成功したら追加行数がlineに格納される
		line = st.executeUpdate();
		}

		return line;
	}
	
	
	//ログイン機能
	public AdminAccount login(String loginid , String password)  
			throws Exception
	{
		AdminAccount adminaccount = null;
		
		try(Connection con = getConnection();
		PreparedStatement st=con.prepareStatement
				("SELECT * FROM adminaccount WHERE BINARY loginid = ? AND BINARY password = ?");)
		{
		
			st.setString(1, loginid);
			st.setString(2, password);
			ResultSet rs = st.executeQuery();
			
			//バリデーション(サニタイズ)用インスタンス
			Validation vali = new Validation();
			
			
			while (rs.next()) {
				//ユーザーアカウントbeanの初期化
				adminaccount = new AdminAccount();
				//ユーザーcode取得Beanに格納
				adminaccount.setAdmincode(rs.getInt("admincode"));
				//loginIDを格納
				//String型をサニタイズ(無害化)して格納
				String loginid2 =vali.Sanitize(rs.getString("loginid"));
				adminaccount.setLoginid(loginid2);
				//パスワードを格納
				//String型をサニタイズ(無害化)して格納
				String password2 =vali.Sanitize(rs.getString("password"));
				adminaccount.setPassword(password2);
				//ユーザーネームを格納
				//String型をサニタイズ(無害化)して格納
				String adminname2 =vali.Sanitize(rs.getString("adminname"));
				adminaccount.setAdminname(adminname2);
				
				//作成日を格納
				
				// Mysqlからタイムスタンプ型で取得
				Timestamp sqlDate = rs.getTimestamp("createdate"); 
				// java.time.LocalDateTime に変換
				LocalDateTime localDate = sqlDate.toLocalDateTime();
				// 日付フォーマットを指定
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日　HH時mm分");
		        
		        // LocalDateTime を String に変換
		        //String型をサニタイズ(無害化)して格納
		        String formattedDate = localDate.format(formatter);
		        formattedDate=vali.Sanitize(formattedDate);
				adminaccount.setCreatedate(formattedDate);
				
				//アカウントのステータスの有効・無効格納
				adminaccount.setAccountstatusflag(rs.getBoolean("accountstatusflag"));
				
				
			}
		}
		
		return adminaccount;
	}
	
	
	//管理者ネームで検索のメソッド
	public List<AdminAccount> search(String keyword)  
			throws Exception
	{
		List<AdminAccount> list = new ArrayList<>();
		
		try(Connection con = getConnection();
		PreparedStatement st=con.prepareStatement
				("SELECT * FROM adminaccount WHERE admincode LIKE ? OR adminname LIKE ? ");)
		{
		
			st.setString(1, "%" + keyword + "%");
			st.setString(2, "%" + keyword + "%");
			ResultSet rs = st.executeQuery();
			
			//バリデーション(サニタイズ)用インスタンス
			Validation vali = new Validation();
			
			while (rs.next()) {
				
				//管理者アカウントbeanの初期化
				AdminAccount adminaccount = new AdminAccount();
				//管理者コード取得Beanに格納
				adminaccount.setAdmincode(rs.getInt("admincode"));
				//loginIDを格納
				//String型をサニタイズ(無害化)して格納
				String loginid2 =vali.Sanitize(rs.getString("loginid"));
				adminaccount.setLoginid(loginid2);
				//パスワードを格納
				//String型をサニタイズ(無害化)して格納
				String password2 =vali.Sanitize(rs.getString("password"));
				adminaccount.setPassword(password2);
				//管理者ネームを格納
				//String型をサニタイズ(無害化)して格納
				String adminname2 =vali.Sanitize(rs.getString("adminname"));
				adminaccount.setAdminname(adminname2);
				
				//作成日を格納
				
				// Mysqlからタイムスタンプ型で取得
				Timestamp sqlDate = rs.getTimestamp("createdate"); 
				// java.time.LocalDateTime に変換
				LocalDateTime localDate = sqlDate.toLocalDateTime();
				// 日付フォーマットを指定
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日　HH時mm分");
		        
		        // LocalDateTime を String に変換
		        //String型をサニタイズ(無害化)して格納
		        String formattedDate = localDate.format(formatter);
		        formattedDate=vali.Sanitize(formattedDate);
				adminaccount.setCreatedate(formattedDate);
				
				//アカウントのステータスの有効・無効格納
				adminaccount.setAccountstatusflag(rs.getBoolean("accountstatusflag"));
				
				list.add(adminaccount);
				
			}
		}
		
		return list;
	}
	
	//管理者コードで検索のメソッド
	public AdminAccount searchCode(String admincode)
			throws Exception {
		//管理者アカウントbeanの初期化
		AdminAccount adminaccount = new AdminAccount();

		try (Connection con = getConnection();
				PreparedStatement st = con.prepareStatement("SELECT * FROM adminaccount WHERE admincode = ? ");) {

			st.setString(1, admincode);
			ResultSet rs = st.executeQuery();

			//バリデーション(サニタイズ)用インスタンス
			Validation vali = new Validation();

			while (rs.next()) {

				//ユーザーコード取得Beanに格納
				adminaccount.setAdmincode(rs.getInt("admincode"));
				//loginIDを格納
				//String型をサニタイズ(無害化)して格納
				String loginid2 = vali.Sanitize(rs.getString("loginid"));
				adminaccount.setLoginid(loginid2);
				//パスワードを格納
				//String型をサニタイズ(無害化)して格納
				String password2 = vali.Sanitize(rs.getString("password"));
				adminaccount.setPassword(password2);
				//ユーザーネームを格納
				//String型をサニタイズ(無害化)して格納
				String username2 = vali.Sanitize(rs.getString("adminname"));
				adminaccount.setAdminname(username2);

				//作成日を格納

				// Mysqlからタイムスタンプ型で取得
				Timestamp sqlDate = rs.getTimestamp("createdate");
				// java.time.LocalDateTime に変換
				LocalDateTime localDate = sqlDate.toLocalDateTime();
				// 日付フォーマットを指定
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日　HH時mm分");
		        
		        // LocalDateTime を String に変換
		        //String型をサニタイズ(無害化)して格納
		        String formattedDate = localDate.format(formatter);
		        formattedDate=vali.Sanitize(formattedDate);
				adminaccount.setCreatedate(formattedDate);

				//アカウントのステータスの有効・無効格納
				adminaccount.setAccountstatusflag(rs.getBoolean("accountstatusflag"));

			}
		}

		return adminaccount;
	}
	
	
	//ユーザーアカウントを有効に更新するメソッド
	public boolean OnStatus(AdminAccount adminAccount) throws Exception {
		//DB更新件数の初期化
		int line = 0;
		boolean s_flag = false;

		//DB接続とsql文の設定
		try (Connection con = getConnection();
				PreparedStatement st = con.prepareStatement(
						"update adminaccount set accountstatusflag=? Where admincode=?");) {

			//ユーザーアカウントBeanからそれぞれの値をstのsql文に格納
			st.setBoolean(1, true);
			st.setInt(2, adminAccount.getAdmincode());
			//失敗したら0,成功したら追加行数がlineに格納される
			line = st.executeUpdate();
		}
		if (line > 0) {
			s_flag = true;
		}

		return s_flag;
	}

	//ユーザーアカウントを無効に更新するメソッド
	public boolean OffStatus(AdminAccount adminAccount) throws Exception {
		//DB更新件数の初期化
		int line = 0;
		boolean s_flag = false;

		//DB接続とsql文の設定
		try (Connection con = getConnection();
				PreparedStatement st = con.prepareStatement(
						"update adminaccount set accountstatusflag=? Where admincode=?");) {

			//ユーザーアカウントBeanからそれぞれの値をstのsql文に格納
			st.setBoolean(1, false);
			st.setInt(2, adminAccount.getAdmincode());
			//失敗したら0,成功したら追加行数がlineに格納される
			line = st.executeUpdate();
		}
		if (line > 0) {
			s_flag = true;
		}

		return s_flag;
	}
	
	
	//管理者ID重複チェックメソッド
	public boolean checkAdminLoginidDuplicate(String loginId) throws Exception {

		//select count(*)～で該当するアカウントが何件あるか確認するsqlを実行する
		try (Connection con = getConnection();
				PreparedStatement st = con.prepareStatement("SELECT COUNT(*) FROM useraccount WHERE loginid = ? ");) {
			st.setString(1, loginId);

			//クエリの実行結果をrsに格納する
			try (ResultSet rs = st.executeQuery()) {
				//クエリの実行結果を一件ずつ表示する(今回は検索ヒット数を表示するだけなので一回だけ実行)
				if (rs.next()) {
					//クエリ実行結果の1件目：検索ヒット件数(既存アカウント件数)をcountに代入する
					int count = rs.getInt(1);
					return count == 0; // 重複がなければ(検索ヒット数が0なら) true / 検索ヒット数が1以上であれば false
				}
			}

		}
		//それ以外はfalse
		return false;
	}

	//管理者ネーム重複チェックメソッド
	public boolean checkAdminNameDuplicate(String userName) throws Exception {

		//select count(*)～で該当するアカウントが何件あるか確認するsqlを実行する
		try (Connection con = getConnection();
				PreparedStatement st = con.prepareStatement("SELECT COUNT(*) FROM useraccount WHERE username = ? ");) {
			st.setString(1, userName);

			//クエリの実行結果をrsに格納する
			try (ResultSet rs = st.executeQuery()) {
				//クエリの実行結果を一件ずつ表示する(今回は検索ヒット数を表示するだけなので一回だけ実行)
				if (rs.next()) {
					//クエリ実行結果の1件目：検索ヒット件数(既存アカウント件数)をcountに代入する
					int count = rs.getInt(1);
					return count == 0; // 重複がなければ(検索ヒット数が0なら) true / 検索ヒット数が1以上であれば false
				}
			}

		}
		//それ以外はfalse
		return false;
	}

	//管理者アカウントの削除のメソッド
	public boolean delete(AdminAccount admin)
			throws Exception {
		//現在何件あるかを保存する変数
		int totalRows = 0;
		boolean successflag = false;

		//現在件数取得
		try (Connection con = getConnection();
				PreparedStatement st = con.prepareStatement("SELECT COUNT(*) FROM adminaccount");
				PreparedStatement st2 = con.prepareStatement("DELETE FROM adminaccount WHERE admincode = ?");) {

			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				totalRows = rs.getInt(1);
			}

			//削除処理

			// 自動コミットを無効にする
			con.setAutoCommit(false);

			st2.setInt(1, admin.getAdmincode());
			int deleterows = st2.executeUpdate();
			if (deleterows > 0 && (totalRows - deleterows) >= 1) {
				successflag = true;
				//残るデータが1件以上になるか、削除対象があればコミット
				con.commit();
			} else {
				//削除対象ないか、データが0件になるならロールバック
				con.rollback();
			}

		}

		return successflag;
	}
		
		
	
}
