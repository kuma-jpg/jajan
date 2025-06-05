package bean;

import java.io.Serializable;

public class EventInfo implements Serializable {
	private int eventcode;
	private String eventdate;
	private String eventlocation;
	private String eventname;
	private String image;
	private String eventtext;
	private String createdate;
	private String eventupdate;
	private boolean eventstatusflag;
	
	
	
	//GET文
	public int getEventcode() {
		return eventcode;
	}
	


	public String getEventdate() {
		return eventdate;
	}
	
	
	public String getEventlocation() {
		return eventlocation;
	}
	
	
	public String getEventname() {
		return eventname;
	}
	
	public String getImage() {
		return image;
	}

	public String getEventtext() {
		return eventtext;
	}
	
	public String getCreatedate() {
		return createdate;
	}
	
	public String getEventupdate() {
		return eventupdate;
	}
	
	public boolean isEventstatusflag() {
		return eventstatusflag;
	}



	
	
	//SET文
	
	public void setEventcode(int eventcode) {
		this.eventcode = eventcode;
	}

	public void setEventdate(String eventdate) {
		this.eventdate = eventdate;
	}

	
	public void setEventlocation(String eventlocation) {
		this.eventlocation = eventlocation;
	}

	public void setEventname(String eventname) {
		this.eventname = eventname;
	}


	public void setImage(String image) {
		this.image = image;
	}

	

	public void setEventtext(String eventtext) {
		this.eventtext = eventtext;
	}

	

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}


	public void setEventupdate(String eventupdate) {
		this.eventupdate = eventupdate;
	}

	public void setEventstatusflag(boolean eventstatusflag) {
		this.eventstatusflag = eventstatusflag;
	}




	
}