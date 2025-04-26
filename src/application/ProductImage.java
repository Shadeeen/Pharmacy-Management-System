package application;

import javafx.scene.image.ImageView;

public class ProductImage {
	private int pid;
	private ImageView image;

	public ProductImage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductImage(int pid, ImageView image) {
		super();
		this.pid = pid;
		this.image = image;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public ImageView getImage() {
		return image;
	}

	public void setImage(ImageView image) {
		this.image = image;
	}

}
