package application;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BagCard {

	private static VBox vb;
	private static HBox hb;
	private static Label lb1, lb2;

	private static VBox vPane;

	public static VBox getVb(String name, double price, ImageView image, int quantity, Product p) {
		vb = new VBox(10);
		hb = new HBox(20);

		lb1 = new Label(name + "  X" + quantity);

		hb.setStyle(
				"-fx-background-color: #f0f1f2; -fx-background-radius: 5px; -fx-border-radius: 2px; -fx-border-color:#5fa364; -fx-border-width:5px; -fx-border-style: solid hidden hidden hidden;"
						+ "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
		lb1.setStyle(
				"-fx-font-size: 18px; -fx-font-weight: bold;-fx-text-fill: #5C5C5C;-fx-font-family: 'Times New Roman';");

		lb2 = new Label("₪" + price);
		lb2.setStyle(
				"-fx-font-size: 22px;-fx-font-weight: bold;-fx-text-fill: #235347;-fx-font-family: 'Times New Roman';");

		ImageView m = new ImageView("trash.jpg");
		m.setFitWidth(20);
		m.setFitHeight(20);
		image.setFitHeight(70);
		image.setFitWidth(70);

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

		Button trash = new Button("", m);

		trash.setUserData(p);

		trash.setOnAction(e -> {

			Product pr = (Product) trash.getUserData();
			if (pr != null) {
				trashQuery(" delete from bucket_product where pid=" + pr.getPid() + " and cusid=" + Main.userId + ";");
				Main.backetQuery();
				Backet.totalQuery();
				int coid = Backet.addQuery(Main.userId, pr.getPid(), 0);
				trashQuery("delete from customer_perscreption where pid=" + pr.getPid() + " and cusid=" + Main.userId
						+ ";");
			}
		});

		trash.setStyle("-fx-background-color: #f0f1f2;");
		ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), trash);
		scaleUp.setToX(1.1);
		scaleUp.setToY(1.1);
		ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), trash);
		scaleDown.setToX(1.0);
		scaleDown.setToY(1.0);

		trash.setOnMouseEntered(event -> scaleUp.playFromStart());
		trash.setOnMouseExited(event -> scaleDown.playFromStart());

		vb.getChildren().addAll(lb1, lb2);

		hb.setMargin(stackPane, new Insets(10, 10, 10, 10));
		hb.setMargin(vb, new Insets(10, 10, 10, 10));

		vPane = new VBox();
		vPane.getChildren().add(hb);

		VBox b = new VBox(10);
		ImageView pm = new ImageView("prescription.png");
		pm.setFitWidth(20);
		pm.setFitHeight(20);

		Button prescriptionButton = new Button("", pm);
		b.getChildren().addAll(trash, prescriptionButton);
		vb.setMargin(b, new Insets(0, 0, 0, 150));

		ScaleTransition scaleUp2 = new ScaleTransition(Duration.millis(200), prescriptionButton);
		scaleUp2.setToX(1.1);
		scaleUp2.setToY(1.1);
		ScaleTransition scaleDown2 = new ScaleTransition(Duration.millis(200), prescriptionButton);
		scaleDown2.setToX(1.0);
		scaleDown2.setToY(1.0);

		prescriptionButton.setOnMouseEntered(event -> scaleUp2.playFromStart());
		prescriptionButton.setOnMouseExited(event -> scaleDown2.playFromStart());

		hb.getChildren().addAll(stackPane, vb, b);
		b.setAlignment(Pos.CENTER);
		prescriptionButton.setStyle("-fx-background-color: #f0f1f2;");

		vPane.setMargin(hb, new Insets(6, 100, 6, 100));
		hb.setAlignment(Pos.CENTER);
		vPane.setAlignment(Pos.CENTER);
		vPane.setStyle("-fx-background-color:#f0f1f2");

		prescriptionButton.setUserData(p);

		prescriptionButton.setOnAction(e -> {
			try {
				Product product = ((Product) prescriptionButton.getUserData());

				if (product != null) {
					String check = " select ca.catname from category ca , product p  where ca.catid=p.catid and ca.catname= 'Medicine' and p.pid="
							+ product.getPid();
					if (checProduct(check)) {

						Stage stage = new Stage();
						FileChooser fileChooser = new FileChooser();
						fileChooser.setInitialDirectory(new File("C:\\"));
						fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("jpg ", "*.jpg"));
						File selectedFile = fileChooser.showOpenDialog(stage);

						if (selectedFile != null) {
							String g = selectedFile.getCanonicalPath();
							String q = " insert into customer_perscreption (perscreption,pid,cusid) values (? ,"
									+ product.getPid() + "," + Main.userId + ");";
							preQuery(q, g);
						}
					}
				} else
					return;
			} catch (NullPointerException | IOException e1) {
				e1.printStackTrace();
			}

		});

		return vPane;
	}

	public static void trashQuery(String q) {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rubapharmacy", "root",
					"shaden2004");
			Statement stmt = con.createStatement();
			stmt.executeUpdate(q);
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void preQuery(String q, String g) {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rubapharmacy", "root",
					"shaden2004");
			PreparedStatement stmt = con.prepareStatement(q);
			stmt.setString(1, g);
			stmt.executeUpdate();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean checProduct(String q) {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rubapharmacy", "root",
					"shaden2004");
			PreparedStatement stmt = con.prepareStatement(q);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				stmt.close();
				con.close();
				return true;
			} else {
				stmt.close();
				con.close();
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
