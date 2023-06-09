module com.example.multiplechoicesapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;

    opens com.example.multiplechoiceapp to javafx.fxml;
    exports com.example.multiplechoiceapp;
}