package application;

public class Customer {
	private int cid;
	private String address, passward, email, name, birthDate, gender, phoneNumber;

	public Customer(int cid, String address, String passward, String email, String name, String birthDate,
			String gender, String phoneNumber) {
		super();
		this.cid = cid;
		this.address = address;
		this.passward = passward;
		this.email = email;
		this.name = name;
		this.birthDate = birthDate;
		this.gender = gender;
		this.phoneNumber = phoneNumber;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassward() {
		return passward;
	}

	public void setPassward(String passward) {
		this.passward = passward;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}