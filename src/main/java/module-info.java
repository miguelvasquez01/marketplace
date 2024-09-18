module com.marketplace {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires lombok;

    opens com.marketplace.controller to javafx.fxml;
    opens com.marketplace.model to javafx.base;
    exports com.marketplace;
}
