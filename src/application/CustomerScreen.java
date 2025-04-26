package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class CustomerScreen {

	static ArrayList<Product> productArray = new ArrayList<>();

	private FlowPane content;
	private VBox vFullScreenBox;
	private HBox hb;
	private TextField searchTextField;
	private ScrollPane scrollPane;

	private ImageView backetImage;
	private ImageView personalImage;

	private Button backetButton;
	private Button profileButton;

	private StackPane stackPane;
	private Button b;
	private HBox hbConect;

	Filter f = new Filter();
	FlowPane fl;
	private String typeFilter = "", companyFilter = "", fillingFilter = "", exDateFilter = "", prDateFilter = "",
			searchFilter = "";

	public CustomerScreen() throws SQLException {
		fl = Main.write();
		vFullScreenBox = new VBox(5);

		hb = new HBox(10);
		ImageView im = new ImageView("search.png");
		b = new Button("", im);
		searchTextField = new TextField();
		searchTextField.setPromptText(" Search for items ");
		searchTextField.setPrefSize(350, 33);
		searchTextField.setStyle(
				"-fx-border-color: #B8D8BE;-fx-border-radius: 15; -fx-background-radius: 15;-fx-border-width: 1; -fx-padding: 5 10 5 10;-fx-text-fill: #235347;-fx-font-weight: bold;-fx-font-family: 'Times New Roman';-fx-font-size: 15px;");

		im.setFitHeight(15); // Adjust the image size as needed
		im.setFitWidth(15);
		StackPane s = new StackPane();
		s.setAlignment(Pos.CENTER_RIGHT); // Align the image to the right
		s.getChildren().addAll(searchTextField, b);
		b.setStyle("-fx-background-color:white");
		s.setMargin(b, new Insets(0, 5, 0, 0));

		backetImage = new ImageView("bag.png");
		backetImage.setFitHeight(25.0);
		backetImage.setFitWidth(37.0);
		backetImage.setLayoutX(526);
		backetImage.setLayoutY(28);
		backetImage.setPickOnBounds(true);
		backetImage.setPreserveRatio(true);

		personalImage = new ImageView("user.png");
		personalImage.setFitHeight(25.0);
		personalImage.setFitWidth(37.0);
		personalImage.setLayoutX(526);
		personalImage.setLayoutY(28);
		personalImage.setPickOnBounds(true);
		personalImage.setPreserveRatio(true);

		backetButton = new Button("", backetImage);
		profileButton = new Button("", personalImage);
		backetButton.setStyle("-fx-background-color:#f0f1f2");
		profileButton.setStyle("-fx-background-color:#f0f1f2");

		vFullScreenBox.setStyle("-fx-background:#f0f1f2;");

		stackPane = new StackPane();
		scrollPane = new ScrollPane();

		scrollPane.setPrefSize(200, 800);
		stackPane.getChildren().add(scrollPane);
		scrollPane.setStyle("-fx-background:#f0f1f2;");

		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(s, backetButton, profileButton);

		fl.setOrientation(Orientation.HORIZONTAL);
		fl.setHgap(50);
		fl.setVgap(50);

		scrollPane.setContent(fl);
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scrollPane.setFitToWidth(true);

		hbConect = new HBox(400);

		hbConect.getChildren().addAll(hb);
		hbConect.setAlignment(Pos.TOP_CENTER);

		vFullScreenBox.getChildren().addAll(hbConect, f.getVb(), scrollPane);
		vFullScreenBox.setMargin(scrollPane, new Insets(10, 0, 0, 10));
		vFullScreenBox.setMargin(hbConect, new Insets(20, 0, 0, 0));

		b.setOnMouseClicked(e -> {
			try {
				if (!searchTextField.getText().isBlank()) {

					searchFilter = "p.pname LIKE '%" + searchTextField.getText() + "%'";
					String query = "SELECT p.pid, p.catid, p.expiredDate, p.productDate,p.pname, p.sellingPrice  "
							+ "FROM product p WHERE " + searchFilter;
					Queries(query);

				} else {
					String query = "SELECT p.pid, p.catid,p.expiredDate, p.productDate,p.pname, p.sellingPrice  "
							+ "FROM product p ";
					Queries(query);
				}
			} catch (Exception e1) {
			}

		});

		f.getMedicine().setOnAction(e -> {
			try {
				typeFilter = " and c.catname='Medicine'";
				fillingFilter = "";
				companyFilter = "";
				Queries("SELECT p.pid, p.catid, p.expiredDate, p.productDate,p.pname, p.sellingPrice  "
						+ " from product p, category c " + "where p.catid=c.catid " + typeFilter);

			} catch (Exception e1) {
			}
			f.getFilling().setDisable(false);
		});
		f.getNonMedicine().setOnAction(e -> {
			try {

				typeFilter = " and c.catname!='Medicine'";
				fillingFilter = "";
				companyFilter = "";
				Queries("SELECT p.pid, p.catid, p.expiredDate, p.productDate,p.pname, p.sellingPrice  "
						+ " from product p, category c " + "where p.catid=c.catid " + typeFilter);
			} catch (Exception e1) {
			}
			f.getFilling().setDisable(true);
		});

		f.getAll1().setOnAction(e -> {
			try {
				typeFilter = "";
				fillingFilter = "";
				companyFilter = "";
				String query = "SELECT p.pid, p.catid, p.expiredDate, p.productDate, p.pname, p.sellingPrice  "
						+ " FROM product p";

				Queries(query);
			} catch (Exception e1) {
			}
			f.getFilling().setDisable(true);
		});
		f.getFilling().setDisable(true);
		f.getCapsule().setOnAction(e -> filterFilling("Capsule"));
		f.getTablet().setOnAction(e -> filterFilling("Tablet"));
		f.getCream().setOnAction(e -> filterFilling("Cream"));
		f.getOintment().setOnAction(e -> filterFilling("Ointment"));
		f.getInhaler().setOnAction(e -> filterFilling("Inhaler"));
		f.getAlQuds().setOnAction(e -> filterCompany("Al-Quds"));
		f.getMed().setOnAction(e -> filterCompany("Med Care"));
		f.getHelping().setOnAction(e -> filterCompany("Helping Hands"));
		f.getCure().setOnAction(e -> filterCompany("Cure"));
		f.getRamo().setOnAction(e -> filterCompany("Ramo"));

		f.getAll2().setOnAction(e -> {
			try {
				typeFilter = "";
				fillingFilter = "";

				String query = "SELECT p.pid, p.catid, p.expiredDate, p.productDate, p.pname, p.sellingPrice "
						+ " FROM product p";

				Queries(query);
			} catch (Exception e1) {
			}
			f.getType().setDisable(false);
		});

		f.getAll3().setOnAction(e -> {
			try {
				typeFilter = "";
				fillingFilter = "";

				String query = "SELECT p.pid, p.catid, p.expiredDate, p.productDate, p.pname, p.sellingPrice  "
						+ " FROM product p ";

				Queries(query);
			} catch (Exception e1) {
			}

			f.getType().setDisable(false);
		});

		f.getSalary().setOnAction(e -> {
			try {
				if (f.getSalary().getText().equals("Salary ↓")) {
					String query = "SELECT p.pid, p.catid, p.expiredDate, p.productDate, p.pname, p.sellingPrice  "
							+ " FROM product p " + " order by 6 DESC ";
					Queries(query);
					f.getSalary().setText("Salary ↑");
				} else {
					String query = "SELECT p.pid, p.catid, p.expiredDate, p.productDate, p.pname, p.sellingPrice  "
							+ " FROM product p " + " order by 6 ";
					Queries(query);
					f.getSalary().setText("Salary ↓");
				}
			} catch (Exception e1) {
			}
		});
		f.getExdp().setOnAction(e -> {
			try {
				exDateFilter = "  p.expiredDate='" + f.getExdp().getValue().toString() + "'";
				Queries(" SELECT p.pid, p.catid, p.expiredDate, p.productDate, p.pname, p.sellingPrice \n"
						+ "from product p \n" + " where  " + exDateFilter);
			} catch (Exception e1) {
			}
		});

		f.getPrdp().setOnAction(e -> {
			try {
				prDateFilter = " p.productDate='" + f.getPrdp().getValue().toString() + "'";
				Queries(" SELECT p.pid, p.catid, p.expiredDate, p.productDate, p.pname, p.sellingPrice \n"
						+ "from product p \n" + " where  " + prDateFilter);
			} catch (Exception e1) {
			}
		});

	}

	private void Queries(String query) throws Exception {
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rubapharmacy", "root", "shaden2004");
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);

		fl.getChildren().clear();

		while (rs.next()) {
			Product p = null;
			for (int i = 0; i < Main.pimList.size(); i++) {
				if (Main.pimList.get(i).getPid() == rs.getInt(1)) {
					Card c = new Card();
					p = new Product(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5),
							rs.getDouble(6));
					fl.getChildren()
							.add(c.getVb(p.getName(), p.getSellingPrice(), Main.pimList.get(i).getImage(), 1, p));

				}

			}
		}
		scrollPane.setContent(fl);

		rs.close();
		stmt.close();
		con.close();
	}

	private void filterCompany(String name) {
		try {
			companyFilter = " and cm.cname='" + name + "'";

			Queries(" SELECT p.pid, p.catid, p.expiredDate, p.productDate, p.pname, p.sellingPrice, p.img , c.catname, cm.cname \n"
					+ "from product p, category c, company_product cp, company cm\n"
					+ "where p.catid=c.catid and cp.cid=cm.cid and p.pid=cp.pid " + companyFilter);

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void filterFilling(String name) {
		try {
			Queries(" SELECT p.pid, p.catid, p.expiredDate, p.productDate, p.pname, p.sellingPrice, p.img , c.catname \n"
					+ "from product p, category c , medicine m \n"
					+ "where p.catid=c.catid and p.pid=m.pid and m.filling='" + name + "'" + fillingFilter);

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public VBox VPane() {
		return vFullScreenBox;
	}

	public Button getBacketButton() {
		return backetButton;
	}

	public void setBacketButton(Button backetButton) {
		this.backetButton = backetButton;
	}

	public Button getProfileButton() {
		return profileButton;
	}

	public void setProfileButton(Button profileButton) {
		this.profileButton = profileButton;
	}

	public FlowPane getContent() {
		return content;
	}

	public void setContent(FlowPane content) {
		this.content = content;
	}

	public ArrayList<Product> getProductArray() {
		return productArray;
	}

	public void setProductArray(ArrayList<Product> productArray) {
		this.productArray = productArray;
	}

	public VBox getvFullScreenBox() {
		return vFullScreenBox;
	}

	public void setvFullScreenBox(VBox vFullScreenBox) {
		this.vFullScreenBox = vFullScreenBox;
	}

	public HBox getHb() {
		return hb;
	}

	public void setHb(HBox hb) {
		this.hb = hb;
	}

	public TextField getSearchTextField() {
		return searchTextField;
	}

	public void setSearchTextField(TextField searchTextField) {
		this.searchTextField = searchTextField;
	}

	public ScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(ScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public ImageView getBacketImage() {
		return backetImage;
	}

	public void setBacketImage(ImageView backetImage) {
		this.backetImage = backetImage;
	}

	public ImageView getPersonalImage() {
		return personalImage;
	}

	public void setPersonalImage(ImageView personalImage) {
		this.personalImage = personalImage;
	}

	public StackPane getStackPane() {
		return stackPane;
	}

	public void setStackPane(StackPane stackPane) {
		this.stackPane = stackPane;
	}

	public Button getB() {
		return b;
	}

	public void setB(Button b) {
		this.b = b;
	}

	public HBox getHbConect() {
		return hbConect;
	}

	public void setHbConect(HBox hbConect) {
		this.hbConect = hbConect;
	}

	public Filter getF() {
		return f;
	}

	public void setF(Filter f) {
		this.f = f;
	}

	public FlowPane getFl() {
		return fl;
	}

	public void setFl(FlowPane fl) {
		this.fl = fl;
	}

	public String getTypeFilter() {
		return typeFilter;
	}

	public void setTypeFilter(String typeFilter) {
		this.typeFilter = typeFilter;
	}

	public String getCompanyFilter() {
		return companyFilter;
	}

	public void setCompanyFilter(String companyFilter) {
		this.companyFilter = companyFilter;
	}

	public String getFillingFilter() {
		return fillingFilter;
	}

	public void setFillingFilter(String fillingFilter) {
		this.fillingFilter = fillingFilter;
	}

	public String getExDateFilter() {
		return exDateFilter;
	}

	public void setExDateFilter(String exDateFilter) {
		this.exDateFilter = exDateFilter;
	}

	public String getPrDateFilter() {
		return prDateFilter;
	}

	public void setPrDateFilter(String prDateFilter) {
		this.prDateFilter = prDateFilter;
	}

	public String getSearchFilter() {
		return searchFilter;
	}

	public void setSearchFilter(String searchFilter) {
		this.searchFilter = searchFilter;
	}

}
