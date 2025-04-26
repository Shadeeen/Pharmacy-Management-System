package application;

import java.time.LocalDate;
import java.util.Locale;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class Filter {
	private static Button salary;
	private static Menu type, filling, company;
	private static DatePicker exdp, prdp;
	private static MenuBar menu;
	private static MenuItem medicine, nonMedicine, all1, all2, all3, alQuds, helping, ramo, med, cure, tablet, cream,
			capsule, ointment, Inhaler;
	private static HBox hb;
	private static FlowPane fp;
	private static VBox vb;

	public static VBox getVb() {

		type = new Menu("Type ↓");
		type.setStyle("-fx-background-color: #e3e2e1; -fx-font-size: 12px;");
		filling = new Menu("Filling ↓");
		filling.setStyle("-fx-background-color: #e3e2e1; -fx-font-size: 12px;");
		company = new Menu("Company ↓");
		company.setStyle("-fx-background-color: #e3e2e1; -fx-font-size: 12px;");
		medicine = new MenuItem("Medicine");
		nonMedicine = new MenuItem("Non-Medicine");
		all1 = new MenuItem("All");
		type.getItems().addAll(medicine, nonMedicine, all1);
		tablet = new MenuItem("Tablet");
		cream = new MenuItem("Cream");
		capsule = new MenuItem("Capsule");
		ointment = new MenuItem("Ointment");
		Inhaler = new MenuItem("Inhaler");
		all2 = new MenuItem("All");
		filling.getItems().addAll(tablet, cream, capsule, ointment, Inhaler, all2);
		alQuds = new MenuItem("AlQuds");
		helping = new MenuItem("Helping Hands");
		ramo = new MenuItem("Ramo");
		med = new MenuItem("Med Care");
		cure = new MenuItem("Cure");
		all3 = new MenuItem("All");
		company.getItems().addAll(alQuds, helping, ramo, med, cure, all3);
		menu = new MenuBar();
		menu.setStyle("-fx-background-color: transparent;");
		Menu space1 = new Menu("  "), space2 = new Menu("  ");
		space1.setStyle("-fx-background-color: transparent;");
		space2.setStyle("-fx-background-color: transparent;");
		menu.getMenus().addAll(type, space1, filling, space2, company);
		Locale.setDefault(Locale.ENGLISH);
		exdp = new DatePicker();
		exdp.getEditor().setDisable(true);
		exdp.setPrefWidth(120);
		exdp.getEditor().setText("Expiry Date");
		exdp.setDayCellFactory(picker -> new DateCell() {
			@Override
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				if (date.isBefore(LocalDate.now())) {
					setDisable(true); // Disable future dates
					setStyle("-fx-background-color: #c3c2c1;"); // Change color for disabled dates (optional)
				}
			}
		});
		prdp = new DatePicker();
		prdp.getEditor().setDisable(true);
		prdp.setPrefWidth(120);
		prdp.getEditor().setText("Product Date");
		prdp.setDayCellFactory(picker -> new DateCell() {
			@Override
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				if (date.isAfter(LocalDate.now())) {
					setDisable(true); // Disable future dates
					setStyle("-fx-background-color: #c3c2c1;"); // Change color for disabled dates (optional)
				}
			}
		});
		salary = new Button("Salary ↓");
		salary.setStyle("-fx-background-color: #e3e2e1; -fx-font-size: 12px;");
		hb = new HBox(300);
		ImageView img = new ImageView(new Image("logo.png"));
		img.setFitWidth(150);
		img.setFitHeight(60);
		HBox hb3 = new HBox(20);
		hb3.setAlignment(Pos.CENTER);
		hb3.getChildren().addAll(menu, salary, prdp, exdp);
		hb.getChildren().addAll(img, hb3);

		HBox.setMargin(img, new Insets(0, 0, 0, 15));

		vb = new VBox(0);
		fp = new FlowPane();
		vb.getChildren().addAll(hb, fp);
		VBox.setMargin(hb, new Insets(10, 0, 0, 0));
		
		
		return vb;
	}

	public static Button getSalary() {
		return salary;
	}

	public static void setSalary(Button salary) {
		Filter.salary = salary;
	}

	public static Menu getType() {
		return type;
	}

	public static void setType(Menu type) {
		Filter.type = type;
	}

	public static Menu getFilling() {
		return filling;
	}

	public static void setFilling(Menu filling) {
		Filter.filling = filling;
	}

	public static Menu getCompany() {
		return company;
	}

	public static void setCompany(Menu company) {
		Filter.company = company;
	}

	public static DatePicker getExdp() {
		return exdp;
	}

	public static void setExdp(DatePicker exdp) {
		Filter.exdp = exdp;
	}

	public static DatePicker getPrdp() {
		return prdp;
	}

	public static void setPrdp(DatePicker prdp) {
		Filter.prdp = prdp;
	}

	public static MenuBar getMenu() {
		return menu;
	}

	public static void setMenu(MenuBar menu) {
		Filter.menu = menu;
	}

	public static MenuItem getMedicine() {
		return medicine;
	}

	public static void setMedicine(MenuItem medicine) {
		Filter.medicine = medicine;
	}

	public static MenuItem getNonMedicine() {
		return nonMedicine;
	}

	public static void setNonMedicine(MenuItem nonMedicine) {
		Filter.nonMedicine = nonMedicine;
	}

	public static MenuItem getAll1() {
		return all1;
	}

	public static void setAll1(MenuItem all1) {
		Filter.all1 = all1;
	}

	public static MenuItem getAll2() {
		return all2;
	}

	public static void setAll2(MenuItem all2) {
		Filter.all2 = all2;
	}

	public static MenuItem getAll3() {
		return all3;
	}

	public static void setAll3(MenuItem all3) {
		Filter.all3 = all3;
	}

	public static MenuItem getAlQuds() {
		return alQuds;
	}

	public static void setAlQuds(MenuItem alQuds) {
		Filter.alQuds = alQuds;
	}

	public static MenuItem getHelping() {
		return helping;
	}

	public static void setHelping(MenuItem helping) {
		Filter.helping = helping;
	}

	public static MenuItem getRamo() {
		return ramo;
	}

	public static void setRamo(MenuItem ramo) {
		Filter.ramo = ramo;
	}

	public static MenuItem getMed() {
		return med;
	}

	public static void setMed(MenuItem med) {
		Filter.med = med;
	}

	public static MenuItem getCure() {
		return cure;
	}

	public static void setCure(MenuItem cure) {
		Filter.cure = cure;
	}

	public static MenuItem getTablet() {
		return tablet;
	}

	public static void setTablet(MenuItem tablet) {
		Filter.tablet = tablet;
	}

	public static MenuItem getCream() {
		return cream;
	}

	public static void setCream(MenuItem cream) {
		Filter.cream = cream;
	}

	public static MenuItem getCapsule() {
		return capsule;
	}

	public static void setCapsule(MenuItem capsule) {
		Filter.capsule = capsule;
	}

	public static MenuItem getOintment() {
		return ointment;
	}

	public static void setOintment(MenuItem ointment) {
		Filter.ointment = ointment;
	}

	public static MenuItem getInhaler() {
		return Inhaler;
	}

	public static void setInhaler(MenuItem inhaler) {
		Inhaler = inhaler;
	}

	public static HBox getHb() {
		return hb;
	}

	public static void setHb(HBox hb) {
		Filter.hb = hb;
	}

	public static FlowPane getFp() {
		return fp;
	}

	public static void setFp(FlowPane fp) {
		Filter.fp = fp;
	}

	public static void setVb(VBox vb) {
		Filter.vb = vb;
	}
	
	
}
