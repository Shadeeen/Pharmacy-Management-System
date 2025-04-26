module DataBase {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    // Open the package containing OrdersView to javafx.base
    opens application to javafx.base;

    exports application;
}