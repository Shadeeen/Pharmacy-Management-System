package application;

public class SellingPoint {

	private int spid;
	private String location;
	private int phoneNumber;

	public SellingPoint(int spid, String location, int phoneNumber) {
		super();
		this.spid = spid;
		this.location = location;
		this.phoneNumber = phoneNumber;
	}

	public SellingPoint() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SellingPoint(int spid, String location) {
		super();
		this.spid = spid;
		this.location = location;
	}

	public int getSpid() {
		return spid;
	}

	public void setSpid(int spid) {
		this.spid = spid;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
