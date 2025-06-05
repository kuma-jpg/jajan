package bean;

import java.io.Serializable;

public class ReviewInfo implements Serializable {
	private int reviewcode;
	private int eventcode;
	private String eventname;
	private int usercode;
	private String username;
	private boolean accountstatusflag;
	private int admincode;
	
	private String reviewtext;
	private String createdate;
	private String reviewupdate;
	private boolean reviewstatusflag;
	
	//get
	public int getReviewcode() {
		return reviewcode;
	}
	public int getEventcode() {
		return eventcode;
	}
	public String getEventname() {
		return eventname;
	}
	public int getUsercode() {
		return usercode;
	}
	public String getUsername() {
		return username;
	}
	public boolean isAccountstatusflag() {
		return accountstatusflag;
	}
	public int getAdmincode() {
		return admincode;
	}
	public String getReviewtext() {
		return reviewtext;
	}
	public String getCreatedate() {
		return createdate;
	}
	public String getReviewupdate() {
		return reviewupdate;
	}
	public boolean isReviewstatusflag() {
		return reviewstatusflag;
	}
	
	//set
	public void setReviewcode(int reviewcode) {
		this.reviewcode = reviewcode;
	}
	public void setEventcode(int eventcode) {
		this.eventcode = eventcode;
	}
	public void setEventname(String eventname) {
		this.eventname = eventname;
	}
	public void setUsercode(int usercode) {
		this.usercode = usercode;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setAccountstatusflag(boolean accountstatusflag) {
		this.accountstatusflag = accountstatusflag;
	}
	public void setAdmincode(int admincode) {
		this.admincode = admincode;
	}
	public void setReviewtext(String reviewtext) {
		this.reviewtext = reviewtext;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public void setReviewupdate(String reviewupdate) {
		this.reviewupdate = reviewupdate;
	}
	public void setReviewstatusflag(boolean reviewstatusflag) {
		this.reviewstatusflag = reviewstatusflag;
	}
	
}
