package bean;

import java.io.Serializable;

public class UserAccount implements Serializable {
	
	private int usercode;
	private String loginid;
	private String password;
	private String username;
	private String createdate;
	private boolean accountstatusflag;
	
	
	//get文
	public int getUsercode() {
		return usercode;
	}
	
	public String getLoginid() {
		return loginid;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public boolean isAccountstatusflag() {
		return accountstatusflag;
	}

	
	public String getCreatedate() {
		return createdate;
	}
	
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	
	public void setUsercode(int usercode) {
		this.usercode = usercode;
	}
	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}



	public void setAccountstatusflag(boolean accountstatusflag) {
		this.accountstatusflag = accountstatusflag;
	}
	
	

	
	
	

}
