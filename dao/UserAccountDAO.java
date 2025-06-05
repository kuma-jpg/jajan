package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import bean.UserAccount;
import tool.Validation;

public class UserAccountDAO extends Dao {
	
	//ログイン処理
	public UserAccount login(String loginid , String password)  
			throws Exception
	{
		//ユーザーアカウントDAO初期化
		UserAccount useraccount = null;
		
		//MYSQL文の実行　ログインIDとパスワードで検索をかける
		//SQLインジェクション対策としてPreparedStatementを使用
		try(Connection con = getConnection();
		PreparedStatement st=con.prepareStatement
				("SELECT * FROM useraccount WHERE loginid = ? AND password = ? AND accountstatusflag = ?");)
		{
		
			st.setString(1, loginid);
			st.setString(2, password);
			st.setBoolean(3, true);
			
			ResultSet rs = st.executeQuery();
			
			//バリデーション(サニタイズ)用インスタンス
			Validation vali = new Validation();
						
			while (rs.next()) {
				//ユーザーアカウントbeanの初期化
				useraccount = new UserAccount();
				//ユーザーcode取得Beanに格納
				useraccount.setUsercode(rs.getInt("usercode"));
				//loginIDを格納
				//String型をサニタイズ(無害化)して格納
				String loginid2 =vali.Sanitize(rs.getString("loginid"));
				useraccount.setLoginid(loginid2);
				//パスワードを格納
				//String型をサニタイズ(無害化)して格納
				String password2 =vali.Sanitize(rs.getString("password"));
				useraccount.setPassword(password2);
				//ユーザーネームを格納
				//String型をサニタイズ(無害化)して格納
				String username2 =vali.Sanitize(rs.getString("username"));
				useraccount.setUsername(username2);
				
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
				
				//作成日をlocaldatetimeで格納
				useraccount.setCreatedate(formattedDate);
				
				//アカウントのステータスの有効・無効格納
				useraccount.setAccountstatusflag(rs.getBoolean("accountstatusflag"));
				
				
			}
		}
		
		return useraccount;
	}
	
	//ユーザーアカウントを追加するメソッド
	public int insert(UserAccount userAccount) throws Exception{
		//DB追加件数の初期化
		int line = 0;
		
		//DB接続とsql文の設定
		try(Connection con = getConnection();
		PreparedStatement st=con.prepareStatement(
				"insert into useraccount(loginid, password, username,accountstatusflag) values(?,?,?,true);"
				);){
		
		//ユーザーアカウントBeanからそれぞれの値をstのsql文に格納
		st.setString(1, userAccount.getLoginid());
		st.setString(2, userAccount.getPassword());
		st.setString(3, userAccount.getUsername());
		//失敗したら0,成功したら追加行数がlineに格納される
		line = st.executeUpdate();
		}

		return line;
	}
	
	//ユーザーアカウントを更新するメソッド
	public int update(UserAccount userAccount)throws Exception{
		//DB更新件数の初期化
		int line = 0;
		
		//DB接続とsql文の設定
		try(Connection con = getConnection();
		PreparedStatement st=con.prepareStatement(
				"update useraccount set loginid=?, password=?, username=? Where usercode=?"
				);){
			
			//ユーザーアカウントBeanからそれぞれの値をstのsql文に格納
			st.setString(1, userAccount.getLoginid());
			st.setString(2, userAccount.getPassword());
			st.setString(3, userAccount.getUsername());
			//st.setBoolean(4, userAccount.isAccountstatusflag());
			st.setInt(4, userAccount.getUsercode());
			//失敗したら0,成功したら追加行数がlineに格納される
			line = st.executeUpdate();
		}
		return line;
	}
	

	//ユーザーアカウントの有効・無効を更新するメソッド
	public int switchStatus(UserAccount userAccount)throws Exception{
		//DB更新件数の初期化
		int line = 0;
		
		//DB接続とsql文の設定
		try(Connection con = getConnection();
		PreparedStatement st=con.prepareStatement(
				"update useraccount set accountstatusflag=? Where usercode=?"
				);){
			
			//ユーザーアカウントBeanからそれぞれの値をstのsql文に格納
			st.setBoolean(1, userAccount.isAccountstatusflag());
			st.setInt(2, userAccount.getUsercode());
			//失敗したら0,成功したら追加行数がlineに格納される
			line = st.executeUpdate();
		}
		return line;
	}
	
	//ユーザーアカウントを有効に更新するメソッド
	public boolean OnStatus(UserAccount userAccount) throws Exception {
		//DB更新件数の初期化
		int line = 0;
		boolean s_flag=false;

		//DB接続とsql文の設定
		try (Connection con = getConnection();
				PreparedStatement st = con.prepareStatement(
						"update useraccount set accountstatusflag=? Where usercode=?");) {

			//ユーザーアカウントBeanからそれぞれの値をstのsql文に格納
			st.setBoolean(1, true);
			st.setInt(2, userAccount.getUsercode());
			//失敗したら0,成功したら追加行数がlineに格納される
			line = st.executeUpdate();
		}
		if(line>0) {s_flag=true;}
		
		return s_flag;
	}
	
	//ユーザーアカウントを無効に更新するメソッド
	public boolean OffStatus(UserAccount userAccount) throws Exception {
		//DB更新件数の初期化
		int line = 0;
		boolean s_flag = false;

		//DB接続とsql文の設定
		try (Connection con = getConnection();
				PreparedStatement st = con.prepareStatement(
						"update useraccount set accountstatusflag=? Where usercode=?");) {

			//ユーザーアカウントBeanからそれぞれの値をstのsql文に格納
			st.setBoolean(1, false);
			st.setInt(2, userAccount.getUsercode());
			//失敗したら0,成功したら追加行数がlineに格納される
			line = st.executeUpdate();
		}
		if (line > 0) {
			s_flag = true;
		}

		return s_flag;
	}
	
	
	//ユーザーアカウントを無効にするメソッド(退会処理)
	public int leave(UserAccount userAccount)throws Exception{
		//DB更新件数の初期化
		int line = 0;
		
		//DB接続とsql文の設定
		try(Connection con = getConnection();
		PreparedStatement st=con.prepareStatement(
				"update useraccount set accountstatusflag=? Where usercode=?"
				);){
			
			//ユーザーアカウントBeanからそれぞれの値をstのsql文に格納
			st.setBoolean(1, false);
			st.setInt(2, userAccount.getUsercode());
			//失敗したら0,成功したら追加行数がlineに格納される
			line = st.executeUpdate();
		}
		return line;
	}
	
	
	
	//ユーザーネームで検索のメソッド
	public List<UserAccount> search(String keyword)  
			throws Exception
	{
		List<UserAccount> list = new ArrayList<>();
		
		try(Connection con = getConnection();
		PreparedStatement st=con.prepareStatement
				("SELECT * FROM useraccount WHERE username LIKE ? ");)
		{
		
			st.setString(1, "%" + keyword + "%");
			ResultSet rs = st.executeQuery();
			
			//バリデーション(サニタイズ)用インスタンス
			Validation vali = new Validation();
			
			while (rs.next()) {
				
				//ユーザーアカウントbeanの初期化
				UserAccount useraccount = new UserAccount();
				//ユーザーコード取得Beanに格納
				useraccount.setUsercode(rs.getInt("usercode"));
				//loginIDを格納
				//String型をサニタイズ(無害化)して格納
				String loginid2 =vali.Sanitize(rs.getString("loginid"));
				useraccount.setLoginid(loginid2);
				//パスワードを格納
				//String型をサニタイズ(無害化)して格納
				String password2 =vali.Sanitize(rs.getString("password"));
				useraccount.setPassword(password2);
				//ユーザーネームを格納
				//String型をサニタイズ(無害化)して格納
				String username2 =vali.Sanitize(rs.getString("username"));
				useraccount.setUsername(username2);
				
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
				
				
				useraccount.setCreatedate(formattedDate);
				
				//アカウントのステータスの有効・無効格納
				useraccount.setAccountstatusflag(rs.getBoolean("accountstatusflag"));
				
				list.add(useraccount);
				
			}
		}
		
		return list;
	}
	
	
	
	//ユーザーコードで検索のメソッド
	public UserAccount searchCode(String usercode)
			throws Exception {
		//ユーザーアカウントbeanの初期化
		UserAccount useraccount = new UserAccount();

		try (Connection con = getConnection();
				PreparedStatement st = con.prepareStatement("SELECT * FROM useraccount WHERE usercode = ? ");) {

			st.setString(1, usercode);
			ResultSet rs = st.executeQuery();

			//バリデーション(サニタイズ)用インスタンス
			Validation vali = new Validation();

			while (rs.next()) {

				//ユーザーコード取得Beanに格納
				useraccount.setUsercode(rs.getInt("usercode"));
				//loginIDを格納
				//String型をサニタイズ(無害化)して格納
				String loginid2 = vali.Sanitize(rs.getString("loginid"));
				useraccount.setLoginid(loginid2);
				//パスワードを格納
				//String型をサニタイズ(無害化)して格納
				String password2 = vali.Sanitize(rs.getString("password"));
				useraccount.setPassword(password2);
				//ユーザーネームを格納
				//String型をサニタイズ(無害化)して格納
				String username2 = vali.Sanitize(rs.getString("username"));
				useraccount.setUsername(username2);

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
				useraccount.setCreatedate(formattedDate);

				//アカウントのステータスの有効・無効格納
				useraccount.setAccountstatusflag(rs.getBoolean("accountstatusflag"));

			}
		}

		return useraccount;
	}
	
	
	
	//ユーザーID重複チェックメソッド
	public boolean checkUserLoginidDuplicate(String loginId) throws Exception {
		
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
	
	//ユーザーネーム重複チェックメソッド
	public boolean checkUserNameDuplicate(String userName) throws Exception {
		
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
		
		
	//ユーザーアカウントの削除のメソッド
	public boolean delete(UserAccount user)
			throws Exception {
		//現在何件あるかを保存する変数
		int totalRows = 0;
		boolean successflag = false;

		//現在件数取得
		try (	Connection con = getConnection();
				PreparedStatement st = con.prepareStatement("SELECT COUNT(*) FROM useraccount");
				PreparedStatement st2 = con.prepareStatement("DELETE FROM useraccount WHERE usercode = ?");
				) {
			
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				totalRows = rs.getInt(1);
			}

			//削除処理

			// 自動コミットを無効にする
			con.setAutoCommit(false);

			

			st2.setInt(1, user.getUsercode());
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
