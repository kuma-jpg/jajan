package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import bean.ReviewInfo;
import tool.Validation;

public class ReviewInfoDAO extends Dao {

	//口コミ検索メソッド(イベントコードに紐づく口コミを返す)
	//口コミ検索メソッド(イベントコードに紐づく口コミを返す)
		public List<ReviewInfo> UserSearch(String eventCode) throws Exception {

			List<ReviewInfo> list = new ArrayList<>();

			try (Connection con = getConnection();
					PreparedStatement st = con.prepareStatement("SELECT reviewinfo.reviewcode, reviewinfo.eventcode, reviewinfo.usercode, reviewinfo.reviewtext, reviewinfo.createdate, reviewinfo.reviewupdate, reviewinfo.reviewstatusflag, useraccount.username,useraccount.accountstatusflag,eventinfo.eventname "
							+ " FROM reviewinfo "
							+ " INNER JOIN useraccount"
							+ " ON reviewinfo.usercode = useraccount.usercode"
							+ " INNER JOIN eventinfo"
							+ " ON reviewinfo.eventcode = eventinfo.eventcode"
							+ " where reviewinfo.eventcode = ?;");)
			{
				st.setString(1, eventCode);

				ResultSet rs = st.executeQuery();
				
				//バリデーション(サニタイズ)用インスタンス
				Validation vali = new Validation();

				while (rs.next()) {

					//口コミ情報beanの初期化
					ReviewInfo reviewInfo = new ReviewInfo();

					//口コミコードを格納
					reviewInfo.setReviewcode(rs.getInt("reviewcode"));
					//イベントコードを格納
					reviewInfo.setEventcode(rs.getInt("eventcode"));
					//ユーザーコードを格納
					reviewInfo.setUsercode(rs.getInt("usercode"));
					//口コミ本文を格納
					//String型をサニタイズ(無害化)して格納
					String reviewtext2 =vali.Sanitize(rs.getString("reviewtext"));
					reviewInfo.setReviewtext(reviewtext2);
					//作成日を格納
					Timestamp sqlDate = rs.getTimestamp("createdate");
					LocalDateTime localDate = sqlDate.toLocalDateTime();
					// 日付フォーマットを指定
			        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
			        
			        // LocalDateTime を String に変換
			        //String型をサニタイズ(無害化)して格納
			        String formattedDate = localDate.format(formatter);
			        formattedDate=vali.Sanitize(formattedDate);
					reviewInfo.setCreatedate(formattedDate);
					//更新日を格納
					sqlDate = rs.getTimestamp("reviewupdate");
					localDate = sqlDate.toLocalDateTime();
					//フォーマット適用
					formattedDate = localDate.format(formatter);
			        formattedDate=vali.Sanitize(formattedDate);
					
					reviewInfo.setReviewupdate(formattedDate);
					
					//有効・無効を格納
					reviewInfo.setReviewstatusflag(rs.getBoolean("reviewstatusflag"));
					
					//ユーザーネームを格納
					//String型をサニタイズ(無害化)して格納
					String username2 =vali.Sanitize(rs.getString("username"));
					reviewInfo.setUsername(username2);
					
					//イベント名を格納
					//String型をサニタイズ(無害化)して格納
					String eventname2 =vali.Sanitize(rs.getString("eventname"));
					reviewInfo.setEventname(eventname2);
					
					//ユーザーの有効無効フラグを格納
					reviewInfo.setAccountstatusflag(rs.getBoolean("accountstatusflag"));
					

					list.add(reviewInfo);

				}

			}
			return list;
		}
		
		//口コミ検索メソッド(ユーザーコードに紐づく口コミを返す)
		public List<ReviewInfo> UserAccountSearch(String userCode) throws Exception {

			List<ReviewInfo> list = new ArrayList<>();

			try (Connection con = getConnection();
					PreparedStatement st = con.prepareStatement("SELECT reviewinfo.reviewcode, reviewinfo.eventcode, reviewinfo.usercode, reviewinfo.reviewtext, reviewinfo.createdate, reviewinfo.reviewupdate, reviewinfo.reviewstatusflag, useraccount.username,useraccount.accountstatusflag,eventinfo.eventname "
							+ " FROM reviewinfo "
							+ " INNER JOIN useraccount"
							+ " ON reviewinfo.usercode = useraccount.usercode"
							+ " INNER JOIN eventinfo"
							+ " ON reviewinfo.eventcode = eventinfo.eventcode"
							+ " where reviewinfo.usercode = ?;");)
			{
				st.setString(1, userCode);

				ResultSet rs = st.executeQuery();
				
				//バリデーション(サニタイズ)用インスタンス
				Validation vali = new Validation();

				while (rs.next()) {

					//口コミ情報beanの初期化
					ReviewInfo reviewInfo = new ReviewInfo();

					//口コミコードを格納
					reviewInfo.setReviewcode(rs.getInt("reviewcode"));
					//イベントコードを格納
					reviewInfo.setEventcode(rs.getInt("eventcode"));
					//ユーザーコードを格納
					reviewInfo.setUsercode(rs.getInt("usercode"));
					//口コミ本文を格納
					//String型をサニタイズ(無害化)して格納
					String reviewtext2 =vali.Sanitize(rs.getString("reviewtext"));
					reviewInfo.setReviewtext(reviewtext2);
					//作成日を格納
					Timestamp sqlDate = rs.getTimestamp("createdate");
					LocalDateTime localDate = sqlDate.toLocalDateTime();
					// 日付フォーマットを指定
			        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
			        
			        // LocalDateTime を String に変換
			        //String型をサニタイズ(無害化)して格納
			        String formattedDate = localDate.format(formatter);
			        formattedDate=vali.Sanitize(formattedDate);
					reviewInfo.setCreatedate(formattedDate);
					//更新日を格納
					sqlDate = rs.getTimestamp("reviewupdate");
					localDate = sqlDate.toLocalDateTime();
					
					
			        formattedDate = localDate.format(formatter);
			        formattedDate=vali.Sanitize(formattedDate);
					
					reviewInfo.setReviewupdate(formattedDate);
					//有効・無効を格納
					reviewInfo.setReviewstatusflag(rs.getBoolean("reviewstatusflag"));
					
					//ユーザーネームを格納
					//String型をサニタイズ(無害化)して格納
					String username2 =vali.Sanitize(rs.getString("username"));
					reviewInfo.setUsername(username2);
					
					//イベント名を格納
					//String型をサニタイズ(無害化)して格納
					String eventname2 =vali.Sanitize(rs.getString("eventname"));
					reviewInfo.setEventname(eventname2);

					list.add(reviewInfo);

				}

			}
			return list;
		}
	
	//口コミ投稿画面から、口コミ情報をReviewInfoのDBにinsertするメソッド
	public int insert(ReviewInfo reviewInfo) throws Exception {
		//成功1:失敗0のint型変数を生成(insert成功数を格納する変数)
		int line;

		try (Connection con = getConnection();
				PreparedStatement st = con.prepareStatement(
						"insert into reviewinfo(eventcode, usercode, reviewtext,reviewstatusflag) values(?,?,?,true);");) {

			st.setInt(1, reviewInfo.getEventcode());
			st.setInt(2, reviewInfo.getUsercode());
			st.setString(3, reviewInfo.getReviewtext());

			//sql文実行、insert成功したデータ数がlineに格納される(1つのデータのみinsertするので成功:1 失敗:0になる)
			line = st.executeUpdate();
		}
		return line;
	}
	
	
	//口コミを更新するメソッド
	public int update(ReviewInfo reviewInfo) throws Exception {
		//DB更新件数の初期化
		int line = 0;

		try (Connection con = getConnection();
				PreparedStatement st = con.prepareStatement(
						"update reviewinfo set reviewtext=?,reviewstatusflag=? Where reviewcode=?");) {

			st.setString(1, reviewInfo.getReviewtext());
			st.setBoolean(2, reviewInfo.isReviewstatusflag());
			st.setInt(3, reviewInfo.getReviewcode());

			line = st.executeUpdate();

		}

		return line;
	}
	
	//口コミの有効無効を更新するメソッド
	public int switchStatus(ReviewInfo reviewInfo)throws Exception{
		//DB更新件数の初期化
		int line = 0;
		
		//DB接続とsql文の設定
		try(Connection con = getConnection();
		PreparedStatement st=con.prepareStatement(
				"update reviewinfo set reviewstatusflag=? Where reviewcode=?"
				);){
			
			//ユーザーアカウントBeanからそれぞれの値をstのsql文に格納
			st.setBoolean(1, reviewInfo.isReviewstatusflag());
			st.setInt(2, reviewInfo.getReviewcode());
			//失敗したら0,成功したら追加行数がlineに格納される
			line = st.executeUpdate();
		}
		return line;
	}
	
}
