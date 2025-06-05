package bean;

import java.io.Serializable;

public class AdminAccount implements Serializable {
	
	private int admincode;
	private String loginid;
	private String password;
	private String adminname;
	private String createdate;
	private boolean accountstatusflag;
	
	//各種GET文
	public int getAdmincode() {
		return admincode;
	}
	
	public String getLoginid() {
		return loginid;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getAdminname() {
		return adminname;
	}
	
	public String getCreatedate() {
		return createdate;
	}
	
	public boolean isAccountstatusflag() {
		return accountstatusflag;
	}
	
	
	//各種SET文
	public void setAdmincode(int admincode) {
		this.admincode = admincode;
	}
	
	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setAdminname(String adminname) {
		this.adminname = adminname;
	}
	
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public void setAccountstatusflag(boolean accountstatusflag) {
		this.accountstatusflag = accountstatusflag;
	}
	
	

	
	
	

}
