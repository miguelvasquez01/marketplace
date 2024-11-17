module com.marketplace {
    requires javafx.fxml;
    requires lombok;
    // Para manejar JSON
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires javafx.web;
    requires java.net.http;

    opens com.marketplace.controller to javafx.fxml;
    opens com.marketplace.model to javafx.base;
    exports com.marketplace;
    exports com.marketplace.controller;
    exports com.marketplace.services;
    exports com.marketplace.model;
}
