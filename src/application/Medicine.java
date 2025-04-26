package application;

public class Medicine extends Product {
	private String scientificName, filling;

	public Medicine(int pid, int catid, String expiryDate, String productDate, String name, double sellingPrice,
			String img, String scientificName, String filling) {
		super(pid, catid, expiryDate, productDate, name, sellingPrice, img);
		this.scientificName = scientificName;
		this.filling = filling;
	}

	public String getScientificName() {
		return scientificName;
	}

	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}

	public String getFilling() {
		return filling;
	}

	public void setFilling(String filling) {
		this.filling = filling;
	}

}
