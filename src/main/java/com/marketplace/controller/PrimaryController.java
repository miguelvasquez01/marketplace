package com.marketplace.controller;

import java.io.IOException;

import com.marketplace.App;

import javafx.fxml.FXML;
import lombok.Data;

@Data
public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
