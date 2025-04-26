package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Backet {

	private Button checkOutButton;
	private Label total;
	private static Label price;
	private VBox vb;
	private HBox hb;
	private ScrollPane scroller;
	static VBox bac;

	private RadioButton r1;
	private RadioButton r2;
	private RadioButton r3;
	private RadioButton r4;
	private RadioButton r5;

	private ToggleGroup g;

	private VBox vb1;
	private BorderPane bp;

	private HBox h;
	private Button okButton;
	private Button cancelButton;

	private Label lb1;
	private static ObservableList<OrdersView> data;

	public Backet() {

		vb = new VBox(10);

		Card car = new Card();

		scroller = new ScrollPane();
		scroller.setPrefSize(200, 400);

		scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scroller.setFitToWidth(true);

		bac = new VBox(4);
		scroller.setContent(bac);

		ImageView image = new ImageView("check.png");
		checkOutButton = new Button("Checkout", image);

		checkOutButton.setStyle(
				"-fx-font-size:15px;-fx-font-weight: bold;-fx-text-fill: #235347;-fx-background-color:#8EB69B;-fx-font-family: 'Times New Roman';");
		vb.setAlignment(Pos.CENTER);

		hb = new HBox(80);
		total = new Label("Total in ₪:");
		price = new Label();
		VBox v = new VBox();
		price.setAlignment(Pos.CENTER);
		v.getChildren().addAll(total, price);
		v.setMargin(total, new Insets(0, 10, 0, 10));
		v.setMargin(price, new Insets(0, 20, 0, 20));

		total.setStyle(
				"-fx-font-size:20px;-fx-font-weight: bold;-fx-text-fill: #235347;-fx-font-family: 'Times New Roman'; -fx-font-size: 20px;");
		price.setStyle(
				"-fx-font-size:20px;-fx-font-weight: bold;-fx-text-fill: #235347;-fx-font-family: 'Times New Roman'; -fx-font-size: 20px;");

		RadioButton deleteAll = new RadioButton("Delete All");
		deleteAll.setStyle(
				"-fx-font-size:20px;-fx-font-weight: bold;-fx-text-fill: #235347;-fx-font-family: 'Times New Roman'; -fx-font-size: 17px;");

		hb.getChildren().addAll(v, checkOutButton, deleteAll);
		vb.setStyle("-fx-background-color: #f0f1f2;");
		vb.getChildren().addAll(scroller, hb);

		TableView<OrdersView> tv = new TableView<OrdersView>();
		data = FXCollections.observableArrayList();
		TableColumn<OrdersView, Integer> idColumn = new TableColumn<>("Product ID");
		TableColumn<OrdersView, String> nameColumn = new TableColumn<>("product Name");
		TableColumn<OrdersView, Double> priceColumn = new TableColumn<>("Selling Price");
		TableColumn<OrdersView, Integer> quantityColumn = new TableColumn<>("Quantity");

		tv.getColumns().addAll(idColumn, nameColumn, priceColumn, quantityColumn);
		idColumn.setCellValueFactory(new PropertyValueFactory<>("pid"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("pname"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

		idColumn.setStyle("-fx-alignment: CENTER;");
		nameColumn.setStyle("-fx-alignment: CENTER;");
		priceColumn.setStyle("-fx-alignment: CENTER;");
		quantityColumn.setStyle("-fx-alignment: CENTER;");

		tv.setStyle("-fx-font-size: 10px; -fx-border-color: #c8e1cc; -fx-border-width: 4px;");
		BorderPane b = v();
		b.setRight(tv);
		b.setMargin(tv, new Insets(15));

		Scene checkoutScene = new Scene(b, 600, 320);
		Stage checkoutStage = new Stage();
		checkoutStage.setScene(checkoutScene);
		checkOutButton.setOnAction(e -> {
			String q = " select s.location , spid from sellingpoint s ";
			query(q);
			bucketTable(
					" select bp.pid ,p.pname,p.sellingprice,bp.quantity from product p ,bucket_product bp where p.pid=bp.pid and bp.cusid="
							+ Main.userId + "");
			tv.setItems(data);
			checkoutStage.show();
			Main.st.close();
		});

		cancelButton.setOnAction(e -> {
			Main.st.show();
			checkoutStage.close();
		});

		okButton.setOnAction(e -> {
			int status = 0;
			int customerId = Main.userId;

			if (bac.getChildren().isEmpty()) {
				checkoutStage.close();
				showAlert(Alert.AlertType.ERROR, "Bucket empty", "there is no product to buy.");
				return;
			}

			if (!checkAllProductsHavePrescription(customerId)) {
				checkoutStage.close();
				showAlert(Alert.AlertType.ERROR, "Prescription Required", "Some products require a prescription.");
				Main.st.show();
				return;
			}
			int coid = addQuery(customerId, point(), status);
			if (coid == -1) {
				System.out.println("coid");
				return;
			}
			List<OrdersView> products = getProductsFromBucket(customerId);
			for (OrdersView product : products) {
				insertCustomerProductOrder(product.getPid(), coid, product.getQuantity());
			}
			BagCard.trashQuery((" delete from  bucket_product bp where bp.cusid=" + customerId));
			Main.backetQuery();
			Backet.totalQuery();

			bac.getChildren().clear();
			checkoutStage.close();

		});
		deleteAll.setOnAction(e -> {
			if (bac.getChildren().isEmpty()) {
				showAlert(Alert.AlertType.ERROR, "Bucket empty", "there is no product to delete.");
				deleteAll.setSelected(false);
				return;
			}
			BagCard.trashQuery((" delete from  bucket_product bp where bp.cusid=" + Main.userId));
			Main.backetQuery();
			Backet.totalQuery();
			deleteAll.setSelected(false);
		});

	}

	private void bucketTable(String q) {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rubapharmacy", "root",
					"shaden2004");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(q);

			data.clear();
			while (rs.next()) {
				OrdersView ov = new OrdersView(rs.getInt(4), rs.getString(2), rs.getInt(1), rs.getDouble(3));
				data.add(ov);
			}

			rs.close();
			stmt.close();
			con.close();
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private void showAlert(Alert.AlertType alertType, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public List<OrdersView> getProductsFromBucket(int customerId) {
		List<OrdersView> products = new ArrayList<>();
		String query = "SELECT bp.pid, cp.perscreption, bp.quantity FROM bucket_product bp "
				+ "LEFT JOIN customer_perscreption cp ON bp.pid = cp.pid WHERE bp.cusid = " + customerId;

		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rubapharmacy", "root",
				"shaden2004"); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				int pid = rs.getInt("pid");
				String prescription = rs.getString("perscreption");
				int quantity = rs.getInt("quantity");
				products.add(new OrdersView(quantity, pid, prescription));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}

	public void insertCustomerProductOrder(int pid, int coid, int quantity) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rubapharmacy", "root",
				"shaden2004"); Statement stmt = con.createStatement()) {
			String query = String.format("INSERT INTO customer_product_order (pid, coid, quantity) VALUES (%d, %d, %d)",
					pid, coid, quantity);

			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean checkAllProductsHavePrescription(int customerId) {
		String totalProductsQuery = "SELECT COUNT(*) " + "FROM bucket_product bp " + "JOIN product p ON bp.pid = p.pid "
				+ "JOIN category ca ON p.catid = ca.catid " + "WHERE bp.cusid = " + customerId
				+ " AND ca.catname = 'Medicine'";

		String productsWithPrescriptionQuery = "SELECT COUNT(*) " + "FROM bucket_product bp "
				+ "JOIN customer_perscreption cp ON bp.pid = cp.pid " + "JOIN product p ON bp.pid = p.pid "
				+ "JOIN category ca ON p.catid = ca.catid " + "WHERE cp.cusid = " + customerId
				+ " AND ca.catname = 'Medicine'";

		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rubapharmacy", "root",
				"shaden2004");
				Statement totalStmt = con.createStatement();
				Statement prescStmt = con.createStatement()) {

			ResultSet totalRs = totalStmt.executeQuery(totalProductsQuery);
			ResultSet prescRs = prescStmt.executeQuery(productsWithPrescriptionQuery);

			int totalProducts = 0;
			if (totalRs.next()) {
				totalProducts = totalRs.getInt(1);
			}

			int productsWithPrescription = 0;
			if (prescRs.next()) {
				productsWithPrescription = prescRs.getInt(1);
			}

			return totalProducts == productsWithPrescription;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static int addQuery(int customerId, int spid, int status) {
		String query = "INSERT INTO customer_order (cusid, spid, orderStatus) VALUES (?, ?, ?)";
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rubapharmacy", "root",
				"shaden2004"); PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

			stmt.setInt(1, customerId);
			stmt.setInt(2, spid);
			stmt.setInt(3, status);

			int affectedRows = stmt.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Creating order failed, no rows affected.");
			}

			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getInt(1); // Get the generated coid
				} else {
					throw new SQLException("Creating order failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1; // Return -1 or any other error indicator
		}
	}

	public int point() {
		if (r1.isSelected()) {
			return ((SellingPoint) r1.getUserData()).getSpid();
		} else if (r2.isSelected()) {
			return ((SellingPoint) r2.getUserData()).getSpid();
		} else if (r3.isSelected()) {
			return ((SellingPoint) r3.getUserData()).getSpid();
		} else if (r4.isSelected()) {
			return ((SellingPoint) r4.getUserData()).getSpid();
		} else if (r5.isSelected()) {
			return ((SellingPoint) r5.getUserData()).getSpid();
		}
		return -1; // No selling point selected
	}

	public static void totalQuery() {

		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rubapharmacy", "root",
					"shaden2004");
			Statement stmt = con.createStatement();
			String sqlText = " select format (sum(bp.quantity * p.sellingprice),2) from product p, bucket_product bp where p.pid=bp.pid and bp.cusid="
					+ Main.userId + ";";
			Statement stm = con.createStatement();
			ResultSet res = stm.executeQuery(sqlText);
			if (res.next()) {
				double total = res.getDouble(1);
				price.setText(total + "");
			}
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void query(String q) {

		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rubapharmacy", "root",
					"shaden2004");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(q);

			if (rs.next()) {
				SellingPoint slp = new SellingPoint(rs.getInt(2), rs.getString(2));
				r1.setText(rs.getString(1));
				r1.setUserData(slp);
			}
			if (rs.next()) {
				SellingPoint slp = new SellingPoint(rs.getInt(2), rs.getString(2));
				r2.setText(rs.getString(1));
				r2.setUserData(slp);
			}
			if (rs.next()) {
				SellingPoint slp = new SellingPoint(rs.getInt(2), rs.getString(2));
				r3.setText(rs.getString(1));
				r3.setUserData(slp);
			}
			if (rs.next()) {
				SellingPoint slp = new SellingPoint(rs.getInt(2), rs.getString(2));
				r4.setText(rs.getString(1));
				r4.setUserData(slp);
			}
			if (rs.next()) {
				SellingPoint slp = new SellingPoint(rs.getInt(2), rs.getString(2));
				r5.setText(rs.getString(1));
				r5.setUserData(slp);
			}

			rs.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public BorderPane v() {

		vb1 = new VBox(20);
		g = new ToggleGroup();

		bp = new BorderPane();

		r1 = new RadioButton();
		r1.setStyle("-fx-font-weight: bold;-fx-font-family: 'Times New Roman'; -fx-font-size: 15px;");
		r1.setToggleGroup(g);
		r1.setSelected(true);

		r2 = new RadioButton();
		r2.setStyle("-fx-font-weight: bold;-fx-font-family: 'Times New Roman'; -fx-font-size: 15px;");
		r2.setToggleGroup(g);

		r3 = new RadioButton();
		r3.setStyle("-fx-font-weight: bold;-fx-font-family: 'Times New Roman'; -fx-font-size: 15px;");
		r3.setToggleGroup(g);

		r4 = new RadioButton();
		r4.setStyle("-fx-font-weight: bold;-fx-font-family: 'Times New Roman'; -fx-font-size: 15px;");
		r4.setToggleGroup(g);

		r5 = new RadioButton();
		r5.setStyle("-fx-font-weight: bold;-fx-font-family: 'Times New Roman'; -fx-font-size: 15px;");
		r5.setToggleGroup(g);

		h = new HBox(5);
		okButton = new Button("Done");

		okButton.setStyle("-fx-font-weight: bold;-fx-font-family: 'Times New Roman'; -fx-font-size: 15px;");
		cancelButton = new Button("Cancel");
		cancelButton.setStyle("-fx-font-weight: bold;-fx-font-family: 'Times New Roman'; -fx-font-size: 15px;");
		h.getChildren().addAll(cancelButton, okButton);
		h.setAlignment(Pos.BOTTOM_RIGHT);

		bp.setLeft(vb1);
		bp.setBottom(h);
		bp.setMargin(h, new Insets(10));
		vb1.setAlignment(Pos.CENTER_LEFT);
		bp.setMargin(vb1, new Insets(20));

		Rectangle rec = new Rectangle(400, 100);
		rec.setStyle(
				"-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,8),10,0,0,0);-fx-fill:#f0f1f2;-fx-border-style: solid hidden hidden hidden;");

		lb1 = new Label("Enter The Selling Point");
		lb1.setStyle("-fx-font-weight: bold;-fx-font-family: 'Times New Roman'; -fx-font-size: 20px;");
		lb1.setAlignment(Pos.CENTER);
		vb1.getChildren().addAll(lb1, r1, r2, r3, r4, r5);

		bp.setStyle("-fx-background-color:#f0f1f2");
		return bp;

	}

	public VBox vPane() {
		return vb;

	}

	public Button getCheckOutButton() {
		return checkOutButton;
	}

	public void setCheckOutButton(Button checkOutButton) {
		this.checkOutButton = checkOutButton;
	}

	public static VBox getBac() {
		return bac;
	}

	public static void setBac(VBox bac) {
		Backet.bac = bac;
	}

}
