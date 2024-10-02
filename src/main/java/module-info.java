module com.marketplace {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires lombok;
    requires java.net.http;
    // Para manejar JSON
    requires com.fasterxml.jackson.databind;
    requires javafx.graphics;

    opens com.marketplace.controller to javafx.fxml;
    opens com.marketplace.model to javafx.base;
    exports com.marketplace;
    exports com.marketplace.controller;
    exports com.marketplace.services;
    exports com.marketplace.model;
}
