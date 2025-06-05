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
import bean.EventInfo;
import bean.UserAccount;
import tool.Validation;

public class EventInfoDAO extends Dao {
	
	
	
	
	//管理者権限でイベント名検索のメソッド
	public List<EventInfo> adminSearch(String keyword)  
			throws Exception
	{
		//リスト初期化
		List<EventInfo> list = new ArrayList<>();
		
		try(Connection con = getConnection();
		PreparedStatement st=con.prepareStatement
				("SELECT * FROM eventinfo WHERE eventname LIKE ? ");)
		{
		
			st.setString(1, "%" + keyword + "%");
			ResultSet rs = st.executeQuery();
			
			//バリデーション(サニタイズ)用インスタンス
			Validation vali = new Validation();
			
			while (rs.next()) {
				
				//Eventbeanの初期化
				EventInfo event = new EventInfo();
				
				//イベント情報コード取得Beanに格納
				event.setEventcode(rs.getInt("eventcode"));
				//イベント名を格納
				//String型をサニタイズ(無害化)して格納
				String eventname2 =vali.Sanitize(rs.getString("eventname"));
				event.setEventname(eventname2);
				

				//開催日を格納
				
				// Mysqlからタイムスタンプ型で取得
				Timestamp sqlDate = rs.getTimestamp("eventdate"); 
				// java.time.LocalDateTime に変換
				LocalDateTime localDate = sqlDate.toLocalDateTime();
				//開催日をlocaldatetimeで格納
				// 日付フォーマットを指定
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日　HH時mm分");

		        // LocalDateTime を String に変換
		        //String型をサニタイズ(無害化)して格納
		        String formattedDate = localDate.format(formatter);
		        String formattedDate2 =vali.Sanitize(formattedDate);
				event.setEventdate(formattedDate2);
				
				//開催場所を格納
				//String型をサニタイズ(無害化)して格納
				String eventlocation2 =vali.Sanitize(rs.getString("eventlocation"));
				event.setEventlocation(eventlocation2);
				
				//画像パスを格納
				event.setImage(rs.getString("image"));
				
				
				//作成日を格納
				
				// Mysqlからタイムスタンプ型で取得
				sqlDate = rs.getTimestamp("createdate"); 
				// java.time.LocalDateTime に変換
				localDate = sqlDate.toLocalDateTime();
				
				formattedDate = localDate.format(formatter);
				//作成日をlocaldatetimeで格納
				event.setCreatedate(formattedDate);
				
				//更新日を格納
				
				// Mysqlからタイムスタンプ型で取得
				sqlDate = rs.getTimestamp("eventupdate"); 
				// java.time.LocalDateTime に変換
				localDate = sqlDate.toLocalDateTime();
				//作成日をlocaldatetimeで格納
				formattedDate = localDate.format(formatter);
				//作成日をlocaldatetimeで格納
				event.setEventupdate(formattedDate);
				
				
				
				//イベント説明文を格納
				//String型をサニタイズ(無害化)して格納
				String eventtext2 =vali.Sanitize(rs.getString("eventtext"));
				event.setEventtext(eventtext2);
				
				
				//イベントのステータスの有効・無効格納
				event.setEventstatusflag(rs.getBoolean("eventstatusflag"));
				
				list.add(event);
			}
		}
		
		return list;
	}
	
	//ユーザー権限でイベント名で検索のメソッド
	public List<EventInfo> userSearch(String keyword)  
			throws Exception
	{
		//リスト初期化
		List<EventInfo> list = new ArrayList<>();
		
		try(Connection con = getConnection();
		PreparedStatement st=con.prepareStatement
				(" SELECT * FROM eventinfo WHERE eventstatusflag = true  AND (eventname LIKE ? OR eventlocation  LIKE ?)");)
		{
		
			st.setString(1, "%" + keyword + "%");
			st.setString(2, "%" + keyword + "%");
			ResultSet rs = st.executeQuery();
			
			//バリデーション(サニタイズ)用インスタンス
			Validation vali = new Validation();
			
			
			while (rs.next()) {
				
				//Eventbeanの初期化
				EventInfo event = new EventInfo();
				
				//イベント情報コード取得Beanに格納
				event.setEventcode(rs.getInt("eventcode"));
				//イベント名を格納
				//String型をサニタイズ(無害化)して格納
				String eventname2 =vali.Sanitize(rs.getString("eventname"));
				event.setEventname(eventname2);
				

				//開催日を格納
				
				// Mysqlからタイムスタンプ型で取得
				Timestamp sqlDate = rs.getTimestamp("eventdate"); 
				// java.time.LocalDateTime に変換
				LocalDateTime localDate = sqlDate.toLocalDateTime();
				// 日付フォーマットを指定
		        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("yyyy年MM月dd日　HH時mm分");
		        String formattedDate = localDate.format(formatter);
				//開催日をStringで格納
		        //String型をサニタイズ(無害化)して格納
		        String formattedDate2 =vali.Sanitize(formattedDate);
				event.setEventdate(formattedDate2);
				
				//開催場所を格納
				//String型をサニタイズ(無害化)して格納
				String eventlocation2 =vali.Sanitize(rs.getString("eventlocation"));
				event.setEventlocation(eventlocation2);
				
				//画像パスを格納
				event.setImage(rs.getString("image"));
				
				
				//作成日を格納
				
				// Mysqlからタイムスタンプ型で取得
				sqlDate = rs.getTimestamp("createdate"); 
				// java.time.LocalDateTime に変換
				localDate = sqlDate.toLocalDateTime();
				formattedDate = localDate.format(formatter);
				//作成日をStringで格納
				//String型をサニタイズ(無害化)して格納
				formattedDate2 =vali.Sanitize(formattedDate);
				event.setCreatedate(formattedDate2);
				
				//更新日を格納
				
				// Mysqlからタイムスタンプ型で取得
				sqlDate = rs.getTimestamp("eventupdate"); 
				// java.time.LocalDateTime に変換
				localDate = sqlDate.toLocalDateTime();
				formattedDate = localDate.format(formatter);
				//作成日をlocaldatetimeで格納
				event.setEventupdate(formattedDate);
				
				
				
				//イベント説明文を格納
				//String型をサニタイズ(無害化)して格納
				String eventtext2 =vali.Sanitize(rs.getString("eventtext"));
				event.setEventtext(eventtext2);
				
				
				//イベントのステータスの有効・無効格納
				event.setEventstatusflag(rs.getBoolean("eventstatusflag"));
				
				list.add(event);
			}
		}
		
		return list;
	}
	
	//イベントコードで検索のメソッド
	public EventInfo searchCode(String eventcode)  
			throws Exception
	{
		
		//Eventbeanの初期化
		EventInfo event = new EventInfo();
		
		try(Connection con = getConnection();
		PreparedStatement st=con.prepareStatement
				("SELECT * FROM eventinfo WHERE eventcode = ? ");)
		{
		
			st.setString(1,eventcode);
			ResultSet rs = st.executeQuery();
			
			//バリデーション(サニタイズ)用インスタンス
			Validation vali = new Validation();
			
			
			while (rs.next()) {
				
				
				//イベント情報コード取得Beanに格納
				event.setEventcode(rs.getInt("eventcode"));
				//イベント名を格納
				//String型をサニタイズ(無害化)して格納
				String eventname2 =vali.Sanitize(rs.getString("eventname"));
				event.setEventname(eventname2);
				

				//開催日を格納
				
				// Mysqlからタイムスタンプ型で取得
				Timestamp sqlDate = rs.getTimestamp("eventdate"); 
				// java.time.LocalDateTime に変換
				LocalDateTime localDate = sqlDate.toLocalDateTime();
				// 日付フォーマットを指定
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日　HH時mm分");
		        String formattedDate = localDate.format(formatter);
				
				//開催日をlocaldatetimeで格納
		      //String型をサニタイズ(無害化)して格納
		        String formattedDate2 =vali.Sanitize(formattedDate);
				event.setEventdate(formattedDate2);
				
				//開催場所を格納
				//String型をサニタイズ(無害化)して格納
				String eventlocation2 =vali.Sanitize(rs.getString("eventlocation"));
				event.setEventlocation(eventlocation2);
				
				//画像パスを格納
				event.setImage(rs.getString("image"));
				
				
				//作成日を格納
				
				// Mysqlからタイムスタンプ型で取得
				sqlDate = rs.getTimestamp("createdate"); 
				// java.time.LocalDateTime に変換
				localDate = sqlDate.toLocalDateTime();
				formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
				formattedDate = localDate.format(formatter);
				//作成日をlocaldatetimeで格納
				//String型をサニタイズ(無害化)して格納
				formattedDate2 =vali.Sanitize(formattedDate);
				event.setCreatedate(formattedDate2);
				
				//更新日を格納
				
				// Mysqlからタイムスタンプ型で取得
				sqlDate = rs.getTimestamp("eventupdate"); 
				// java.time.LocalDateTime に変換
				localDate = sqlDate.toLocalDateTime();
				formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
				formattedDate = localDate.format(formatter);
				//作成日をlocaldatetimeで格納
				//String型をサニタイズ(無害化)して格納
				formattedDate2 =vali.Sanitize(formattedDate);
				event.setEventupdate(formattedDate2);
				
				
				
				//イベント説明文を格納
				//String型をサニタイズ(無害化)して格納
				String eventtext2 =vali.Sanitize(rs.getString("eventtext"));
				event.setEventtext(eventtext2);
				
				
				//イベントのステータスの有効・無効格納
				event.setEventstatusflag(rs.getBoolean("eventstatusflag"));
				
				
			}
		}
		
		return event;
	}
	
	//最近開催されるイベント検索メソッド
	public List<EventInfo> findUpcomingEvents(int limit)  
				throws Exception
		{
			
			//リスト初期化
			List<EventInfo> list = new ArrayList<>();
			int count=0;
			
			try(Connection con = getConnection();
			PreparedStatement st=con.prepareStatement
					("SELECT * FROM eventinfo WHERE eventdate >= CURRENT_DATE ORDER BY eventdate ASC LIMIT ?"))
			{
			
				
				st.setInt(1,limit);
				ResultSet rs = st.executeQuery();
				
				//バリデーション(サニタイズ)用インスタンス
				Validation vali = new Validation();
				
				
				while (rs.next()) {
					
					//Eventbeanの初期化
					EventInfo event = new EventInfo();
					
					//イベント情報コード取得Beanに格納
					event.setEventcode(rs.getInt("eventcode"));
					//イベント名を格納
					//String型をサニタイズ(無害化)して格納
					String eventname2 =vali.Sanitize(rs.getString("eventname"));
					event.setEventname(eventname2);
					
	
					//開催日を格納
					
					// Mysqlからタイムスタンプ型で取得
					Timestamp sqlDate = rs.getTimestamp("eventdate"); 
					// java.time.LocalDateTime に変換
					LocalDateTime localDate = sqlDate.toLocalDateTime();
					// 日付フォーマットを指定
			        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("yyyy年MM月dd日　HH時mm分");
			        String formattedDate = localDate.format(formatter);
					//開催日をStringで格納
			        //String型をサニタイズ(無害化)して格納
			        String formattedDate2 =vali.Sanitize(formattedDate);
					event.setEventdate(formattedDate2);
					
					//開催場所を格納
					//String型をサニタイズ(無害化)して格納
					String eventlocation2 =vali.Sanitize(rs.getString("eventlocation"));
					event.setEventlocation(eventlocation2);
					
					//画像パスを格納
					event.setImage(rs.getString("image"));
					
					
					//作成日を格納
					
					// Mysqlからタイムスタンプ型で取得
					sqlDate = rs.getTimestamp("createdate"); 
					// java.time.LocalDateTime に変換
					localDate = sqlDate.toLocalDateTime();
					formattedDate = localDate.format(formatter);
					//作成日をStringで格納
					//String型をサニタイズ(無害化)して格納
					formattedDate2 =vali.Sanitize(formattedDate);
					event.setCreatedate(formattedDate2);
					
					//更新日を格納
					
					// Mysqlからタイムスタンプ型で取得
					sqlDate = rs.getTimestamp("eventupdate"); 
					// java.time.LocalDateTime に変換
					localDate = sqlDate.toLocalDateTime();
					formattedDate = localDate.format(formatter);
					//作成日をlocaldatetimeで格納
					event.setEventupdate(formattedDate);
					
					
					
					//イベント説明文を格納
					//String型をサニタイズ(無害化)して格納
					String eventtext2 =vali.Sanitize(rs.getString("eventtext"));
					event.setEventtext(eventtext2);
					
					
					//イベントのステータスの有効・無効格納
					event.setEventstatusflag(rs.getBoolean("eventstatusflag"));
					
					list.add(event);
				}
				
			}
			System.out.println("イベント件数:" + list.size());
			
			return list;
		}
	
	
	//イベント情報を追加するメソッド
	public int insert(EventInfo eventInfo) throws Exception{
		//DB追加件数の初期化
		int line = 0;
		
		//DB接続とsql文の設定
		try(Connection con = getConnection();
		PreparedStatement st=con.prepareStatement(
				"insert into eventinfo(eventname,eventdate,eventlocation,image,eventtext,eventstatusflag) values(?,?,?,?,?,true);"
				);){
		
		//ユーザーアカウントBeanからそれぞれの値をstのsql文に格納
		st.setString(1, eventInfo.getEventname());
		
		//タイムスタンプ型に変換
		Timestamp timestamp = Timestamp.valueOf(eventInfo.getEventdate());
		//SQL文にタイムスタンプ型で納める
		st.setTimestamp(2, timestamp);
		
		//開催場所を格納
		st.setString(3, eventInfo.getEventlocation());
		
		//画像パス保存
		st.setString(4, eventInfo.getImage());
		
		//イベント情報の説明文
		st.setString(5, eventInfo.getEventtext());
		
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
					"update useraccount set loginid=?, password=?, username=?,accountstatusflag=? Where usercode=?"
					);){
				
				//ユーザーアカウントBeanからそれぞれの値をstのsql文に格納
				st.setString(1, userAccount.getLoginid());
				st.setString(2, userAccount.getPassword());
				st.setString(3, userAccount.getUsername());
				st.setBoolean(4, userAccount.isAccountstatusflag());
				st.setInt(5, userAccount.getUsercode());
				//失敗したら0,成功したら追加行数がlineに格納される
				line = st.executeUpdate();
			}
			return line;
		}
	
	
	
	//イベント情報削除のメソッド
	public boolean delete(AdminAccount admin)  
				throws Exception
		{
			//現在何件あるかを保存する変数
			int totalRows=0;
			boolean successflag=false;
			
			//現在件数取得
			try(Connection con = getConnection();
					PreparedStatement st=con.prepareStatement
							("SELECT COUNT(*) FROM eventinfo");)
			{
				ResultSet rs = st.executeQuery();
				 if (rs.next()) {
			            totalRows = rs.getInt(1);
			      }
				 
			}
			
			//削除処理
			try(Connection con = getConnection();)
			{
				// 自動コミットを無効にする
				con.setAutoCommit(false);
							
				try(PreparedStatement st=con.prepareStatement("DELETE FROM eventinfo WHERE eventcode = ?");)
				{
					st.setInt(1, admin.getAdmincode());
					int deleterows = st.executeUpdate();
					if (deleterows > 0 && (totalRows - deleterows) == 0 ) {
						//残るデータが1件以上になるか、削除対象があればコミット
						con.commit();
						
					}
					else
					{
		                //削除対象ないか、データが0件になるならロールバック
		                con.rollback();
					}
					
				}
				
			}
			
			return successflag;
		}
		
	
}
