package application;

public class OrdersView {
	private int quantity;
	private String orderStatus;
	private String location;
	private String pname;
	private int pid;
	private double sellingPrice;
	private String prescription;

	public OrdersView() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrdersView(int quantity, int pid, String prescription) {
		super();
		this.quantity = quantity;
		this.pid = pid;
		this.prescription = prescription;
	}

	public OrdersView(int quantity, String orderStatus, String location, String pname, int pid, double sellingPrice) {
		super();
		this.quantity = quantity;
		this.orderStatus = orderStatus;
		this.location = location;
		this.pname = pname;
		this.pid = pid;
		this.sellingPrice = sellingPrice;
	}

	public OrdersView(int quantity, String pname, int pid, double sellingPrice) {
		super();
		this.quantity = quantity;
		this.pname = pname;
		this.pid = pid;
		this.sellingPrice = sellingPrice;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

}
