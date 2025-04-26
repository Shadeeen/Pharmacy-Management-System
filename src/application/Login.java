package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;

public class Login extends Application {

	private static String dbUsername = "root"; // database username
	private static String dbPassword = "shaden2004"; // database password
	private static String URL = "127.0.0.1"; // server location
	private static String port = "3306"; // port that mysql uses
	private static String dbName = "RubaPharmacy"; // database on mysql to connect to
	private static Connection con;

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		try {
			DBConnection a = new DBConnection(URL, port, dbName, dbUsername, dbPassword);

			con = a.connectDB();

			launch(args);

			con.close();
		} catch (Exception e) {
		}

	}

	public void start(Stage stg) throws SQLException, ClassNotFoundException {

		HBox screen = new HBox();

		VBox imageContainer = new VBox();

		ImageView imageView = new ImageView("loginLogo.png");
		imageView.setFitHeight(200);
		imageView.setFitWidth(200);
		imageContainer.getChildren().add(imageView);
		imageContainer.setAlignment(Pos.CENTER);

		imageContainer.setMaxWidth(300);
		imageContainer.setMinWidth(300);
		imageContainer.setMaxHeight(500);
		imageContainer.setMinHeight(500);
		imageContainer.setStyle("-fx-background-color: #C8E1CC;");
		imageContainer.setPadding(new Insets(-70, 0, 0, 0));

		screen.getChildren().add(imageContainer);
		LOGIN(screen);

		Scene scene = new Scene(screen, 700, 500);
		stg.setResizable(false);
		stg.setMaxWidth(700);
		stg.setMinWidth(700);
		stg.setMaxHeight(500);
		stg.setMinHeight(500);
		stg.centerOnScreen();
		stg.setScene(scene);
		stg.setTitle("Ruba pharmacy");
		stg.show();
	}

	int loginAccount(String username, String password, String table, String tableId, String tableUsername)
			throws SQLException, ClassNotFoundException {
		String SQLtxt = "select " + tableId + " from " + table + " where " + tableUsername + " = '" + username
				+ "' and password = '" + password + "';";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQLtxt);
		int id = -1;
		if (rs.next()) {
			id = rs.getInt(1);
		}
		rs.close();
		stmt.close();
		return id;
	}

	int loginBigManager(String username, String password) throws SQLException, ClassNotFoundException {
		int id = loginAccount(username, password, "Manager", "managerid", "managername");
		return id == 1 ? 1 : -1;
	}

	int loginManager(String username, String password) throws SQLException, ClassNotFoundException {
		return loginAccount(username, password, "Manager", "managerid", "managername");
	}

	int loginEmployee(String username, String password) throws SQLException, ClassNotFoundException {
		return loginAccount(username, password, "PharmacyEmployee", "peid", "pename");
	}

	int loginCustomer(String username, String password) throws SQLException, ClassNotFoundException {
		return loginAccount(username, password, "Customer", "cusid", "cusname");
	}

	private void LOGIN(HBox screen) throws SQLException, ClassNotFoundException {
		VBox login = new VBox();
		login.setMaxWidth(400);
		login.setMinWidth(400);
		login.setMaxHeight(500);
		login.setMinHeight(500);
		login.setPadding(new Insets(-30, 40, 40, 40));
		login.setAlignment(Pos.CENTER);

		Label loginLabel = new Label("LOGIN");
		loginLabel.setStyle("-fx-font-size: 30px; -fx-alignment: CENTER; -fx-font-weight: bold;");
		login.getChildren().add(loginLabel);

		Line line = new Line();
		line.setStartX(0.0f);
		line.setStartY(0.0f);
		line.setEndX(320.0f);
		line.setEndY(0.0f);
		line.setStyle("-fx-stroke: #C8E1CC; -fx-stroke-width: 3px;");
		login.getChildren().add(line);

		GridPane loginGridPane = new GridPane();

		Label usernameLabel = new Label("Username");
		TextField usernameField = new TextField();
		usernameField.setPromptText("Username");
		usernameField.setMaxWidth(200);
		usernameField.setMinWidth(200);
		usernameField.setStyle("-fx-border-color: #C8E1CC;");

		Label passwordLabel = new Label("Password");
		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("Password");
		passwordField.setMaxWidth(200);
		passwordField.setMinWidth(200);
		passwordField.setStyle("-fx-border-color: #C8E1CC;");

		GridPane.setMargin(usernameField, new Insets(20, 10, 10, 10)); // top, right, bottom, left
		GridPane.setMargin(passwordField, new Insets(10, 10, 15, 10));

		TextField passwordTextField = new TextField();
		passwordTextField.setPromptText("Password");
		passwordTextField.setMaxWidth(200);
		passwordTextField.setMinWidth(200);
		passwordTextField.setStyle("-fx-border-color: #C8E1CC;");
		GridPane.setMargin(passwordTextField, new Insets(10, 10, 15, 10));

		CheckBox showPasswordCheckBox = new CheckBox("Show");
		showPasswordCheckBox.setStyle("-fx-font-size: 10px;");
		showPasswordCheckBox.setOnAction(event -> {
			if (showPasswordCheckBox.isSelected()) {
				passwordTextField.setText(passwordField.getText());
				loginGridPane.getChildren().remove(passwordField);
				loginGridPane.add(passwordTextField, 1, 1);
			} else {
				passwordField.setText(passwordTextField.getText());
				loginGridPane.getChildren().remove(passwordTextField);
				loginGridPane.add(passwordField, 1, 1);
			}
		});

		loginGridPane.add(usernameLabel, 0, 0);
		loginGridPane.add(usernameField, 1, 0);
		loginGridPane.add(passwordLabel, 0, 1);
		loginGridPane.add(passwordField, 1, 1);
		loginGridPane.add(showPasswordCheckBox, 2, 1);

		login.getChildren().add(loginGridPane);

		Button loginButton = new Button("Login");
		loginButton.setStyle("-fx-background-color: #C8E1CC; -fx-border-radius: 10px; -fx-background-radius: 10px;");
		loginButton.setMaxWidth(100);
		loginButton.setMinWidth(100);
		loginButton.setOnAction(event -> {
			String username = usernameField.getText();
			String password = passwordField.getText();
			if (loginGridPane.getChildren().size() == 6) {
				loginGridPane.getChildren().remove(5);
			}
			if (username.length() == 0 || password.length() == 0) {
				HBox errorContainer = new HBox();
				Text errorText = new Text("* Please fill all fields");
				errorText.setFill(Color.RED);
				errorContainer.setAlignment(Pos.CENTER_LEFT);
				errorContainer.setMaxWidth(50);
				errorContainer.setMinWidth(50);
				errorContainer.getChildren().add(errorText);
				loginGridPane.add(errorContainer, 0, 2);
				GridPane.setMargin(errorContainer, new Insets(-10, 0, 5, 0));
			} else {
				try {
					int bigManagerId = loginBigManager(username, password);
					int managerId = loginManager(username, password);
					int employeeId = loginEmployee(username, password);
					int customerId = loginCustomer(username, password);
					if (bigManagerId != -1) {
						// go to big manager page
						System.out.println("Big Manager");
					} else if (managerId != -1) {
						// go to manager page
						System.out.println("Manager");
					} else if (employeeId != -1) {
						// go to employee page
						System.out.println("Employee");
					} else if (customerId != -1) {
						// go to customer page
						Main.start(customerId).show();

					} else {
						HBox errorContainer = new HBox();
						Text errorText = new Text("* Invalid username or password");
						errorText.setFill(Color.RED);
						errorContainer.setAlignment(Pos.CENTER_LEFT);
						errorContainer.setMaxWidth(50);
						errorContainer.setMinWidth(50);
						errorContainer.getChildren().add(errorText);
						loginGridPane.add(errorContainer, 0, 2);
						GridPane.setMargin(errorContainer, new Insets(-10, 0, 5, 0));
					}
				} catch (SQLException e) {
					throw new RuntimeException(e);
				} catch (ClassNotFoundException e) {
					throw new RuntimeException(e);
				}
			}
		});
		login.getChildren().add(loginButton);

		Text dontHaveAccountText = new Text("Don't have an account? ");
		Hyperlink signupLink = new Hyperlink("Signup");
		signupLink.setTextFill(Color.web("4AA8F3"));
		dontHaveAccountText.setStyle("-fx-font-size: 10px;");
		signupLink.setStyle("-fx-font-size: 10px;");
		signupLink.setOnAction(event -> {
			screen.getChildren().remove(login);
			try {
				SIGNUP(screen);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		});
		HBox signupBox = new HBox();
		signupBox.setAlignment(Pos.CENTER_LEFT);
		signupBox.setPadding(new Insets(10, 0, 0, 0));
		signupBox.getChildren().addAll(dontHaveAccountText, signupLink);
		login.getChildren().add(signupBox);

		screen.getChildren().add(login);
	}

	private void SIGNUP(HBox screen) throws SQLException, ClassNotFoundException {
		VBox signup = new VBox();
		signup.setMaxWidth(400);
		signup.setMinWidth(400);
		signup.setMaxHeight(500);
		signup.setMinHeight(500);
		signup.setPadding(new Insets(-30, 40, 40, 40));
		signup.setAlignment(Pos.CENTER);

		Label signupLabel = new Label("SIGNUP");
		signupLabel.setStyle("-fx-font-size: 30px; -fx-alignment: CENTER; -fx-font-weight: bold;");
		signup.getChildren().add(signupLabel);

		GridPane signupGridPane = new GridPane();

		Line line2 = new Line();
		line2.setStartX(0.0f);
		line2.setStartY(0.0f);
		line2.setEndX(320.0f);
		line2.setEndY(0.0f);
		line2.setStyle("-fx-stroke: #C8E1CC; -fx-stroke-width: 3px;");
		signup.getChildren().add(line2);

		Label signUpusernameLabel = new Label("Username");
		TextField signUpusernameField = new TextField();
		signUpusernameField.setPromptText("Username");
		signUpusernameField.setMaxWidth(200);
		signUpusernameField.setMinWidth(200);
		signUpusernameField.setStyle("-fx-border-color: #C8E1CC;");
		GridPane.setMargin(signUpusernameField, new Insets(20, 10, 0, 10));

		Label signUpPasswordLabel = new Label("Password");
		PasswordField signUpPasswordField = new PasswordField();
		signUpPasswordField.setPromptText("Password");
		signUpPasswordField.setMaxWidth(200);
		signUpPasswordField.setMinWidth(200);
		signUpPasswordField.setStyle("-fx-border-color: #C8E1CC;");
		GridPane.setMargin(signUpPasswordField, new Insets(10, 10, 0, 10));

		Label signUpConfirmPasswordLabel = new Label("Confirm Password");
		PasswordField signUpConfirmPasswordField = new PasswordField();
		signUpConfirmPasswordField.setPromptText("Password");
		signUpConfirmPasswordField.setMaxWidth(200);
		signUpConfirmPasswordField.setMinWidth(200);
		signUpConfirmPasswordField.setStyle("-fx-border-color: #C8E1CC;");
		GridPane.setMargin(signUpConfirmPasswordField, new Insets(10, 10, 0, 10));

		Label emailLabel = new Label("Email");
		TextField emailField = new TextField();
		emailField.setPromptText("Email");
		emailField.setMaxWidth(200);
		emailField.setMinWidth(200);
		emailField.setStyle("-fx-border-color: #C8E1CC;");
		GridPane.setMargin(emailField, new Insets(10, 10, 0, 10));

		Label genderLabel = new Label("Gender");
		RadioButton maleRadioButton = new RadioButton("Male");
		RadioButton femaleRadioButton = new RadioButton("Female");
		ToggleGroup genderToggleGroup = new ToggleGroup();
		maleRadioButton.setToggleGroup(genderToggleGroup);
		femaleRadioButton.setToggleGroup(genderToggleGroup);
		HBox genderBox = new HBox(10);
		genderBox.getChildren().addAll(maleRadioButton, femaleRadioButton);
		GridPane.setMargin(genderBox, new Insets(10, 10, 0, 10));

		Label birthdateLabel = new Label("Birthdate");
		DatePicker birthdatePicker = new DatePicker();
		birthdatePicker.setMaxWidth(200);
		birthdatePicker.setMinWidth(200);
		GridPane.setMargin(birthdatePicker, new Insets(10, 10, 0, 10));

		Label addressLabel = new Label("Address");
		ComboBox<String> addressComboBox = new ComboBox<>();
		addressComboBox.getItems().addAll("Jenin", "TulKarm", "Tubas", "Nablus", "Qalqiliya", "Salfit", "Jericho",
				"Ramallah", "Jerusalem", "Bethlehem", "Hebron");
		addressComboBox.setPromptText("Select Address");
		addressComboBox.setMaxWidth(200);
		addressComboBox.setMinWidth(200);
		GridPane.setMargin(addressComboBox, new Insets(10, 10, 0, 10));

		Label phoneNumberLabel = new Label("Phone Number");
		TextField phoneNumberField = new TextField();
		phoneNumberField.setPromptText("Phone Number");
		phoneNumberField.setMaxWidth(200);
		phoneNumberField.setMinWidth(200);
		GridPane.setMargin(phoneNumberField, new Insets(10, 10, 15, 10));

		signupGridPane.add(signUpusernameLabel, 0, 0);
		signupGridPane.add(signUpusernameField, 1, 0);
		signupGridPane.add(signUpPasswordLabel, 0, 1);
		signupGridPane.add(signUpPasswordField, 1, 1);
		signupGridPane.add(signUpConfirmPasswordLabel, 0, 2);
		signupGridPane.add(signUpConfirmPasswordField, 1, 2);
		signupGridPane.add(emailLabel, 0, 3);
		signupGridPane.add(emailField, 1, 3);
		signupGridPane.add(genderLabel, 0, 4);
		signupGridPane.add(genderBox, 1, 4);
		signupGridPane.add(birthdateLabel, 0, 5);
		signupGridPane.add(birthdatePicker, 1, 5);
		signupGridPane.add(addressLabel, 0, 6);
		signupGridPane.add(addressComboBox, 1, 6);
		signupGridPane.add(phoneNumberLabel, 0, 7);
		signupGridPane.add(phoneNumberField, 1, 7);

		Button signUpButton = new Button("Sign Up");
		signUpButton.setStyle("-fx-background-color: #C8E1CC; -fx-border-radius: 10px; -fx-background-radius: 10px;");
		signUpButton.setMaxWidth(100);
		signUpButton.setMinWidth(100);
		signUpButton.setOnAction(event -> {
			String username = signUpusernameField.getText();
			String password = signUpPasswordField.getText();
			String confirmPassword = signUpConfirmPasswordField.getText();
			String email = emailField.getText();
			RadioButton selectedRadioButton = (RadioButton) genderToggleGroup.getSelectedToggle();
			LocalDate birthdate = birthdatePicker.getValue();
			String address = addressComboBox.getValue();
			String phoneNumber = phoneNumberField.getText();
			if (signupGridPane.getChildren().size() == 17) {
				signupGridPane.getChildren().remove(16);
			}
			if (username.length() == 0 || password.length() == 0 || confirmPassword.length() == 0 || email.length() == 0
					|| selectedRadioButton == null || birthdate == null || address == null
					|| phoneNumber.length() == 0) {
				HBox errorContainer = new HBox();
				Text errorText = new Text("* Please fill all fields");
				errorText.setFill(Color.RED);
				errorContainer.setAlignment(Pos.CENTER_LEFT);
				errorContainer.setMaxWidth(50);
				errorContainer.setMinWidth(50);
				errorContainer.getChildren().add(errorText);
				signupGridPane.add(errorContainer, 0, 8);
				GridPane.setMargin(errorContainer, new Insets(-10, 0, 5, 0));
			} else if (username.length() > 20) {
				HBox errorContainer = new HBox();
				Text errorText = new Text("* Username is too long");
				errorText.setFill(Color.RED);
				errorContainer.setAlignment(Pos.CENTER_LEFT);
				errorContainer.setMaxWidth(50);
				errorContainer.setMinWidth(50);
				errorContainer.getChildren().add(errorText);
				signupGridPane.add(errorContainer, 0, 8);
				GridPane.setMargin(errorContainer, new Insets(-10, 0, 5, 0));
			} else if (password.length() > 20) {
				HBox errorContainer = new HBox();
				Text errorText = new Text("* Password is too long");
				errorText.setFill(Color.RED);
				errorContainer.setAlignment(Pos.CENTER_LEFT);
				errorContainer.setMaxWidth(50);
				errorContainer.setMinWidth(50);
				errorContainer.getChildren().add(errorText);
				signupGridPane.add(errorContainer, 0, 8);
				GridPane.setMargin(errorContainer, new Insets(-10, 0, 5, 0));
			} else if (!password.equals(confirmPassword)) {
				HBox errorContainer = new HBox();
				Text errorText = new Text("* Passwords don't match");
				errorText.setFill(Color.RED);
				errorContainer.setAlignment(Pos.CENTER_LEFT);
				errorContainer.setMaxWidth(50);
				errorContainer.setMinWidth(50);
				errorContainer.getChildren().add(errorText);
				signupGridPane.add(errorContainer, 0, 8);
				GridPane.setMargin(errorContainer, new Insets(-10, 0, 5, 0));
			} else if (email.length() > 30) {
				HBox errorContainer = new HBox();
				Text errorText = new Text("* Email is too long");
				errorText.setFill(Color.RED);
				errorContainer.setAlignment(Pos.CENTER_LEFT);
				errorContainer.setMaxWidth(50);
				errorContainer.setMinWidth(50);
				errorContainer.getChildren().add(errorText);
				signupGridPane.add(errorContainer, 0, 8);
				GridPane.setMargin(errorContainer, new Insets(-10, 0, 5, 0));
			} else if (!email.matches(
					"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
				HBox errorContainer = new HBox();
				Text errorText = new Text("* Email isn't correct");
				errorText.setFill(Color.RED);
				errorContainer.setAlignment(Pos.CENTER_LEFT);
				errorContainer.setMaxWidth(50);
				errorContainer.setMinWidth(50);
				errorContainer.getChildren().add(errorText);
				signupGridPane.add(errorContainer, 0, 8);
				GridPane.setMargin(errorContainer, new Insets(-10, 0, 5, 0));
			} else if (birthdate.isAfter(LocalDate.of(2010, 1, 1))) {
				HBox errorContainer = new HBox();
				Text errorText = new Text("* Birthdate is invalid");
				errorText.setFill(Color.RED);
				errorContainer.setAlignment(Pos.CENTER_LEFT);
				errorContainer.setMaxWidth(50);
				errorContainer.setMinWidth(50);
				errorContainer.getChildren().add(errorText);
				signupGridPane.add(errorContainer, 0, 8);
				GridPane.setMargin(errorContainer, new Insets(-10, 0, 5, 0));
			} else if (phoneNumber.length() != 10 || !phoneNumber.matches("[0-9]+")) {
				HBox errorContainer = new HBox();
				Text errorText = new Text("* Phone number isn't correct");
				errorText.setFill(Color.RED);
				errorContainer.setAlignment(Pos.CENTER_LEFT);
				errorContainer.setMaxWidth(50);
				errorContainer.setMinWidth(50);
				errorContainer.getChildren().add(errorText);
				signupGridPane.add(errorContainer, 0, 8);
				GridPane.setMargin(errorContainer, new Insets(-10, 0, 5, 0));
			} else {
				String gender = selectedRadioButton.getText().equals("Male") ? "M" : "F";
				String SQLtxt = "insert into Customer (email, password, cusname, birthDate, gender, phoneNumber, address)"
						+ " values ('" + email + "', '" + password + "', '" + username + "', '" + birthdate + "', '"
						+ gender + "', '" + phoneNumber + "', '" + address + "');";
				try {
					Statement stmt = con.createStatement();
					stmt.executeUpdate(SQLtxt);
					stmt.close();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
				// go to Customer page
			}
		});

		signup.getChildren().addAll(signupGridPane, signUpButton);

		Text doYouHaveAccountText = new Text("Do you have an account? ");
		Hyperlink loginLink = new Hyperlink("Login");
		loginLink.setTextFill(Color.web("4AA8F3"));
		doYouHaveAccountText.setStyle("-fx-font-size: 10px;");
		loginLink.setStyle("-fx-font-size: 10px;");
		loginLink.setOnAction(event -> {
			screen.getChildren().remove(signup);
			try {
				LOGIN(screen);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		});
		HBox loginBox = new HBox();
		loginBox.setAlignment(Pos.CENTER_LEFT);
		loginBox.setPadding(new Insets(10, 0, 0, 0));
		loginBox.getChildren().addAll(doYouHaveAccountText, loginLink);
		signup.getChildren().add(loginBox);

		screen.getChildren().add(signup);
	}

}