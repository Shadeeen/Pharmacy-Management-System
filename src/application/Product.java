package application;

public class Product {
	private int pid, catid;
	private String expiryDate, productDate, name, img;
	private double sellingPrice;

	public Product(int pid, int catid, String expiryDate, String productDate, String name,double sellingPrice,
			String img) {
		super();
		this.pid = pid;
		this.catid = catid;
		this.expiryDate = expiryDate;
		this.productDate = productDate;
		this.name = name;
		this.sellingPrice = sellingPrice;
		this.img = img;
	}
	public Product(int pid, int catid, String expiryDate, String productDate, String name,double sellingPrice) {
		super();
		this.pid = pid;
		this.catid = catid;
		this.expiryDate = expiryDate;
		this.productDate = productDate;
		this.name = name;
		this.sellingPrice = sellingPrice;
	}

	public Product(int pid) {
		super();
		this.pid = pid;
	}
	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getCatid() {
		return catid;
	}

	public void setCatid(int catid) {
		this.catid = catid;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getProductDate() {
		return productDate;
	}

	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

}