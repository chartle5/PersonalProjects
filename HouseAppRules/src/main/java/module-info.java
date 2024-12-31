module com.example.houseapprules {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.houseapprules to javafx.fxml;
    exports com.example.houseapprules;
}