package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ProfileScreen {
	private Button orders;
	private VBox vbox = new VBox(50);
	private Label res;
	private String name = "", gender = "", birthDate = "", address = "";
	private int phoneNumber = 0;
	private DatePicker date = new DatePicker();
	private TableView<OrdersView> tableView;
	private static ObservableList<OrdersView> data;

	public ProfileScreen() throws Exception {

		res = new Label();
		vbox.setStyle("-fx-background-color:#f0f1f2;");
		vbox.setAlignment(Pos.CENTER);
		Label lbl = new Label("Update Your Personal Information");
		lbl.setStyle("-fx-font-weight: bold;-fx-font-family: 'Times New Roman'; -fx-font-size: 30px;");
		StackPane sPane = new StackPane();
		sPane.setAlignment(Pos.CENTER);
		Rectangle rec = new Rectangle(600, 300);
		rec.setFill(Color.WHITE);
		rec.setArcWidth(50);
		rec.setArcHeight(50);
		rec.setStyle(
				"-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,8),10,0,0,0);-fx-stroke:#5fa364;-fx-border-style: solid hidden hidden hidden;");
		GridPane gp1 = new GridPane();
		gp1.setAlignment(Pos.CENTER);
		gp1.setVgap(5);
		gp1.setHgap(5);
		Label lblName = new Label("Name");
		lblName.setStyle("-fx-font-weight: bold;-fx-font-family: 'Times New Roman'; -fx-font-size: 15px;");
		TextField tfName = new TextField();
		tfName.setPrefColumnCount(8);
		tfName.setStyle(
				"-fx-border-color: #5fa364;-fx-border-radius: 10; -fx-background-radius: 10;-fx-border-width: 2; -fx-padding: 5 10 5 10;");
		gp1.add(lblName, 0, 0);
		gp1.add(tfName, 1, 0);

		Label lblDate = new Label("Birth Date");
		lblDate.setStyle("-fx-font-weight: bold;-fx-font-family: 'Times New Roman'; -fx-font-size: 15px;");

		date.setPrefHeight(10);
		date.setPrefWidth(120);
		date.getEditor().setDisable(true);
		date.setDayCellFactory(picker -> new DateCell() {
			@Override
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				if (date.isAfter(LocalDate.now())) {
					setDisable(true);
				}
			}
		});
		date.setStyle(
				"-fx-background-color: white;-fx-border-radius: 10;-fx-background-radius: 10;-fx-border-width: 2;-fx-border-color: #5fa364; -fx-padding: 3;");
		gp1.add(lblDate, 0, 1);
		gp1.add(date, 1, 1);

		GridPane gp2 = new GridPane();
		gp2.setAlignment(Pos.CENTER);
		gp2.setHgap(5);
		gp2.setVgap(5);

		Label lblPhone = new Label("Phone Number");
		lblPhone.setStyle("-fx-font-weight: bold;-fx-font-family: 'Times New Roman'; -fx-font-size: 15px;");
		TextField tfPhone = new TextField();
		tfPhone.setPrefColumnCount(8);
		tfPhone.setStyle(
				"-fx-border-color: #5fa364;-fx-border-radius: 10; -fx-background-radius: 10;-fx-border-width: 2; -fx-padding: 5 10 5 10;");
		gp2.add(lblPhone, 0, 0);
		gp2.add(tfPhone, 1, 0);

		Label lblAddress = new Label("Address");
		lblAddress.setAlignment(Pos.CENTER);
		lblAddress.setStyle("-fx-font-weight: bold;-fx-font-family: 'Times New Roman'; -fx-font-size: 15px;");
		TextField tfAddress = new TextField();
		tfAddress.setPrefColumnCount(8);
		tfAddress.setStyle(
				"-fx-border-color: #5fa364;-fx-border-radius: 10; -fx-background-radius: 10;-fx-border-width: 2; -fx-padding: 5 10 5 10;");
		gp2.add(lblAddress, 0, 1);
		gp2.add(tfAddress, 1, 1);

		HBox hGridPanes = new HBox(35);
		hGridPanes.setAlignment(Pos.CENTER);
		hGridPanes.getChildren().addAll(gp1, gp2);

		HBox hGender = new HBox(5);
		hGender.setAlignment(Pos.CENTER);
		Label lblGender = new Label("Gender");
		lblGender.setStyle("-fx-font-weight: bold;-fx-font-family: 'Times New Roman'; -fx-font-size: 15px;");
		ComboBox<String> cb = new ComboBox<String>();
		cb.getItems().addAll("M", "F");
		cb.setStyle("-fx-background-color:#f0f1f2;");
		Button button = new Button("Update");
		button.setStyle(
				" -fx-font-weight: bold;-fx-font-family: 'Times New Roman'; -fx-font-size: 12px;-fx-background-color:#5fa364; -fx-text-fill:white;");
		hGender.getChildren().addAll(lblGender, cb, button);
		VBox vAll = new VBox(35);
		vAll.setAlignment(Pos.CENTER);

		VBox v = new VBox(15);
		v.setAlignment(Pos.CENTER);
		orders = new Button("My Orders");
		orders.setStyle(
				" -fx-font-weight: bold;-fx-font-family: 'Times New Roman'; -fx-font-size: 12px;-fx-background-color:#5fa364; -fx-text-fill:white;");
		res.setStyle("-fx-font-weight: bold;-fx-font-family: 'Times New Roman';-fx-font-size: 11px");
		v.getChildren().addAll(hGender, res, orders);
		vAll.getChildren().addAll(hGridPanes, v);

		sPane.getChildren().addAll(rec, vAll);
		vbox.getChildren().addAll(lbl, sPane);

		tfName.addEventFilter(javafx.scene.input.KeyEvent.KEY_TYPED, event -> {
			if (!event.getCharacter().matches("[a-zA-Z]")) {
				event.consume();
			}
		});

		tfAddress.addEventFilter(javafx.scene.input.KeyEvent.KEY_TYPED, event -> {
			if (!event.getCharacter().matches("[a-zA-Z]")) {
				event.consume();
			}
		});
		tfPhone.addEventFilter(javafx.scene.input.KeyEvent.KEY_TYPED, event -> {
			if (!event.getCharacter().matches("[0-9]")) {
				event.consume();
			}
		});

		button.setOnAction(e -> {
			if (tfName.getText().isBlank() || tfPhone.getText().isBlank() || tfAddress.getText().isBlank()
					|| date.getValue() == null || cb.getValue() == null) {
				res.setText("enter all the information!");
				return;
			}
			name = tfName.getText().trim();
			phoneNumber = Integer.parseInt(tfPhone.getText().trim());
			address = tfAddress.getText().trim();
			LocalDate selectedDate = date.getValue();
			birthDate = formatDate(selectedDate);
			gender = cb.getValue();
			String update = " update customer c set c.cusname = '" + name + "' , c.phoneNumber= " + phoneNumber
					+ " , c.gender = '" + gender + " ' , c.address = '" + address + "' , c.birthDate = '" + birthDate
					+ "'";
			updateQuery(update);

		});
		tableView = new TableView<>();
		data = FXCollections.observableArrayList();
		TableColumn<OrdersView, Integer> idColumn = new TableColumn<>("Product ID");
		TableColumn<OrdersView, String> nameColumn = new TableColumn<>("product Name");
		TableColumn<OrdersView, Double> priceColumn = new TableColumn<>("Selling Price");
		TableColumn<OrdersView, Integer> quantityColumn = new TableColumn<>("Quantity");
		TableColumn<OrdersView, String> locationColumn = new TableColumn<>("Location");
		TableColumn<OrdersView, String> statusColumn = new TableColumn<>("Order status");

		tableView.getColumns().addAll(idColumn, nameColumn, quantityColumn, priceColumn, locationColumn, statusColumn);
		idColumn.setCellValueFactory(new PropertyValueFactory<>("pid"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("pname"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));

		idColumn.setStyle("-fx-alignment: CENTER;");
		nameColumn.setStyle("-fx-alignment: CENTER;");
		locationColumn.setStyle("-fx-alignment: CENTER;");
		priceColumn.setStyle("-fx-alignment: CENTER;");
		statusColumn.setStyle("-fx-alignment: CENTER;");
		quantityColumn.setStyle("-fx-alignment: CENTER;");

		tableView.setItems(data);
		tableView.setPrefWidth(600);
		tableView.setPrefHeight(300);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableView.setStyle("-fx-font-size: 14.5px; -fx-border-color: #c8e1cc; -fx-border-width: 2px;");

		VBox vbox = new VBox(tableView);
		vbox.setPrefSize(600, 300);
		vbox.setStyle("-fx-padding: 10px;");

		Scene scene = new Scene(vbox, 700, 300);
		Stage stage = new Stage();
		stage.setScene(scene);

		orders.setOnAction(e -> {
			tableView.getItems().clear();
			try {
				refreshTable("select p.pid, p.pname,p.sellingPrice,sp.location,co.orderStatus,cpo.quantity "
						+ " from product p , sellingpoint sp , customer_order co, customer_product_order cpo ,customer c "
						+ " where co.spid=sp.spid and co.cusid=c.cusid and cpo.pid=p.pid and cpo.coid=co.coid and c.cusid="
						+ Main.userId + ";");
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			stage.show();
		});

		tableView.setRowFactory(tv -> {
			Stage st = new Stage();
			TableRow<OrdersView> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				VBox vb = new VBox(5);
				Scene s = new Scene(vb, 300, 300);
				st.setScene(s);
				if (event.getClickCount() == 1 && (!row.isEmpty())) {
					OrdersView rowData = row.getItem();
					for (int i = 0; i < Main.orderList.size(); i++) {
						if (Main.orderList.get(i).getPid() == rowData.getPid()) {
							Main.orderList.get(i).getImage().setFitHeight(170);
							Main.orderList.get(i).getImage().setFitWidth(200);
							Label l = new Label(rowData.getPname() + "\n  " + rowData.getSellingPrice() + "₪");
							l.setStyle(
									"-fx-font-weight: bold;-fx-font-family: 'Times New Roman'; -fx-font-size: 20px;-fx-text-fill: #235347;");
							vb.getChildren().addAll(Main.orderList.get(i).getImage(), l);
							vb.setStyle("-fx-background-color: #f0f1f2;;");
							vb.setAlignment(Pos.CENTER);
							vb.setPadding(new Insets(10, 0, 0, 0));
							st.show();
						}
					}
				}
			});
			return row;
		});

	}

	private static void refreshTable(String query) throws Exception {

		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rubapharmacy", "root",
					"shaden2004");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			data.clear();

			int columnCount = rs.getMetaData().getColumnCount();

			while (rs.next()) {
				if (rs.getInt(5) == 0) {
					OrdersView ov = new OrdersView(rs.getInt(6), "Processing", rs.getString(4), rs.getString(2),
							rs.getInt(1), rs.getDouble(3));
					data.add(ov);
				} else if (rs.getInt(5) == 1) {
					OrdersView ov = new OrdersView(rs.getInt(6), "Wating", rs.getString(4), rs.getString(2),
							rs.getInt(1), rs.getDouble(3));
					data.add(ov);
				} else if (rs.getInt(5) == 2) {
					OrdersView ov = new OrdersView(rs.getInt(6), "Done", rs.getString(4), rs.getString(2), rs.getInt(1),
							rs.getDouble(3));
					data.add(ov);
				}
			}

			rs.close();
			stmt.close();
			con.close();
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private String formatDate(LocalDate date) {
		DateTimeFormatter dateFormmater = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return date.format(dateFormmater);
	}

	public void updateQuery(String query) {
		Connection con;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rubapharmacy", "root", "shaden2004");
			Statement stmt = con.createStatement();
			int f = stmt.executeUpdate(query);
			if (f > 0)
				res.setText("updated!");
			else
				res.setText("there is a problem!");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public VBox getVbox() {
		return vbox;
	}

	public void setVbox(VBox vbox) {
		this.vbox = vbox;
	}

}
