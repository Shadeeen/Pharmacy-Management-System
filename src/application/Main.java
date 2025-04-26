package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class Main {
	public static int userId;
	static Stage st = new Stage();

	public static Stage start(int id) {
		userId = id;
		Stage primaryStage = new Stage();
		try {
			CustomerScreen c = new CustomerScreen();
			Scene scene = new Scene(c.VPane(), 400, 400);
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.show();

			ProfileScreen p = new ProfileScreen();

			Scene profileScene = new Scene(p.getVbox(), 800, 500);
			Stage profileStage = new Stage();
			profileStage.setScene(profileScene);

			c.getProfileButton().setOnAction(e -> {
				profileStage.show();
			});

			Backet ba = new Backet();
			Scene sce = new Scene(ba.vPane(), 550, 460);

			st.setScene(sce);

			c.getBacketButton().setOnAction(e -> {

				backetQuery();
				Backet.totalQuery();
				st.show();
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
		return primaryStage;
	}

	static ArrayList<ProductImage> pimList = new ArrayList<>();
	static ArrayList<ProductImage> backetimList = new ArrayList<>();
	static ArrayList<ProductImage> orderList = new ArrayList<>();

	public static void backetQuery() {
		try {

			String q = " select p.pid , p.pname , p.sellingprice , bp.quantity "
					+ " from product p, bucket_product bp , customer c "
					+ " where p.pid=bp.pid and c.cusid=bp.cusid and c.cusid=" + Main.userId + ";";

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rubapharmacy", "root",
					"shaden2004");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(q);
			Backet.bac.getChildren().clear();
			while (rs.next()) {
				Product pr = new Product(rs.getInt(1));
				for (int f = 0; f < Main.pimList.size(); f++) {
					if (pr.getPid() == Main.backetimList.get(f).getPid()) {
						Backet.bac.getChildren().add(BagCard.getVb(rs.getString(2), rs.getDouble(3),
								Main.backetimList.get(f).getImage(), rs.getInt(4), pr));
					}
				}
			}

			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static FlowPane write() {

		FlowPane content = new FlowPane();

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rubapharmacy", "root",
					"shaden2004");
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("select * from product");

			int i = 0;
			while (rs.next()) {
				rs.next();
				rs.next();
				rs.next();
				rs.next();
				Card c = new Card();
				ImageView m = new ImageView(rs.getString(7));
				ImageView m2 = new ImageView(rs.getString(7));
				ImageView m3 = new ImageView(rs.getString(7));

				pimList.add(new ProductImage(rs.getInt(1), m));
				backetimList.add(new ProductImage(rs.getInt(1), m2));
				orderList.add(new ProductImage(rs.getInt(1), m3));
				Product product = new Product(rs.getInt(1), rs.getInt(6), rs.getString(5), rs.getString(4),
						rs.getString(2), rs.getDouble(3), rs.getString(7));
				content.getChildren().add(
						c.getVb(product.getName(), product.getSellingPrice(), pimList.get(i).getImage(), 1, product));

				System.out.println(i);
				i++;
			}

			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		return content;
	}

}
