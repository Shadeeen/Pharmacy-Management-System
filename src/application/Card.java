
package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Card {
	private VBox vb;
	private HBox hb;
	private Label lb1, lb2, lb3;
	private Button backet;
	private Button plus, minus;
	private HBox hbQua;

	public VBox getVb(String name, double price, ImageView image, int quantity, Product p) {
		vb = new VBox(10);
		vb.setPadding(new Insets(10, 0, 0, 0));

		lb1 = new Label(name);

		vb.setStyle(
				"-fx-background-color: #f0f1f2; -fx-background-radius: 5px; -fx-border-radius: 2px; -fx-border-color:#5fa364; -fx-border-width:5px; -fx-border-style: solid hidden hidden hidden;"
						+ "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
		lb1.setStyle(
				"-fx-font-size: 23px; -fx-font-weight: bold;-fx-text-fill: #5C5C5C;-fx-font-family: 'Times New Roman';");

		lb2 = new Label("₪" + price);
		lb2.setStyle(
				"-fx-font-size: 30px;-fx-font-weight: bold;-fx-text-fill: #235347;-fx-font-family: 'Times New Roman';");

		ImageView m = new ImageView("a.png");
		m.setFitWidth(30);
		m.setFitHeight(30);
		image.setFitHeight(170);
		image.setFitWidth(200);

		Rectangle clip = new Rectangle(image.getFitWidth(), image.getFitHeight());
		clip.setArcWidth(30);
		clip.setArcHeight(30);

		image.setClip(clip);

		Rectangle border = new Rectangle(image.getFitWidth(), image.getFitHeight());
		border.setArcWidth(30);
		border.setArcHeight(30);
		border.setStroke(Color.GRAY);
		border.setFill(Color.TRANSPARENT);

		StackPane stackPane = new StackPane(image, border);

		backet = new Button("", m);

		backet.setStyle("-fx-background-color: #f0f1f2;");
		backet.setUserData(p);

		ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), backet);
		scaleUp.setToX(1.1);
		scaleUp.setToY(1.1);
		ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), backet);
		scaleDown.setToX(1.0);
		scaleDown.setToY(1.0);

		ImageView im = new ImageView("add.png");
		ImageView i = new ImageView("minus.png");
		lb3 = new Label("1");

		minus = new Button("", i);
		i.setOpacity(.8);
		minus.setStyle("-fx-font-size:12px;-fx-font-weight: bold;-fx-background-color: #f0f1f2;");

		plus = new Button("", im);

		backet.setOnAction(e -> {
			Product pr = (Product) backet.getUserData();
			if (pr != null) {
				for (int f = 0; f < Main.pimList.size(); f++) {
					if (pr.getPid() == Main.backetimList.get(f).getPid()) {
						insetTobucket(" insert into bucket_product (pid,quantity,cusid)values (" + pr.getPid() + ","
								+ Integer.parseInt(lb3.getText().trim()) + "," + Main.userId + ");");
					}
				}

			}

		});

		getPlus().setOnAction(e -> {
			int n = Integer.parseInt(lb3.getText().trim());
			if (n < 5) {
				n++;
				lb3.setText(n + "");
			}
		});

		getMinus().setOnAction(e -> {
			int n = Integer.parseInt(lb3.getText().trim());
			if (n != 1) {
				n--;
				lb3.setText(n + "");
			}
		});

		plus.setStyle("-fx-font-size:12px;-fx-font-weight: bold;-fx-background-color: #f0f1f2;");
		lb3.setStyle("-fx-font-size: 17px;-fx-font-weight: bold;");
		hbQua = new HBox();
		hbQua.getChildren().addAll(minus, lb3, plus);

		ScaleTransition scaleUp1 = new ScaleTransition(Duration.millis(200), plus);
		scaleUp1.setToX(1.1);
		scaleUp1.setToY(1.1);
		ScaleTransition scaleDown1 = new ScaleTransition(Duration.millis(200), plus);
		scaleDown1.setToX(1.0);
		scaleDown1.setToY(1.0);

		plus.setOnMouseEntered(event -> scaleUp1.playFromStart());
		plus.setOnMouseExited(event -> scaleDown1.playFromStart());

		ScaleTransition scaleUp2 = new ScaleTransition(Duration.millis(200), minus);
		scaleUp2.setToX(1.1);
		scaleUp2.setToY(1.1);
		ScaleTransition scaleDown2 = new ScaleTransition(Duration.millis(200), minus);
		scaleDown2.setToX(1.0);
		scaleDown2.setToY(1.0);

		minus.setOnMouseEntered(event -> scaleUp2.playFromStart());
		minus.setOnMouseExited(event -> scaleDown2.playFromStart());

		hb = new HBox(70);
		backet.setOnMouseEntered(event -> scaleUp.playFromStart());
		backet.setOnMouseExited(event -> scaleDown.playFromStart());
		vb.setAlignment(Pos.CENTER);
		hbQua.setAlignment(Pos.BOTTOM_LEFT);

		hb.getChildren().addAll(hbQua, backet);

		vb.getChildren().addAll(stackPane, lb1, lb2, hb);

		return vb;

	}

	public void insetTobucket(String q) {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rubapharmacy", "root",
					"shaden2004");
			Statement stmt = con.createStatement();

			stmt.executeUpdate(q);

			stmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public VBox getVb() {
		return vb;
	}

	public void setVb(VBox vb) {
		this.vb = vb;
	}

	public HBox getHb() {
		return hb;
	}

	public void setHb(HBox hb) {
		this.hb = hb;
	}

	public Label getLb1() {
		return lb1;
	}

	public void setLb1(Label lb1) {
		this.lb1 = lb1;
	}

	public Label getLb2() {
		return lb2;
	}

	public void setLb2(Label lb2) {
		this.lb2 = lb2;
	}

	public Button getBacket() {
		return backet;
	}

	public void setBacket(Button backet) {
		this.backet = backet;
	}

	public Button getPlus() {
		return plus;
	}

	public void setPlus(Button plus) {
		this.plus = plus;
	}

	public Button getMinus() {
		return minus;
	}

	public void setMinus(Button minus) {
		this.minus = minus;
	}

	public HBox getHbQua() {
		return hbQua;
	}

	public void setHbQua(HBox hbQua) {
		this.hbQua = hbQua;
	}

	public Label getLb3() {
		return lb3;
	}

	public void setLb3(Label lb3) {
		this.lb3 = lb3;
	}

}