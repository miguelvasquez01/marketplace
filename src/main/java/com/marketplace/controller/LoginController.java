package com.marketplace.controller;

import com.marketplace.model.Vendedor;
import com.marketplace.services.AuthService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {
    
    @FXML
    private Label lbApellidos;

    @FXML
    private Label lbCedula;

    @FXML
    private Label lbNombre;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfCedula;

    private AuthService authService = new AuthService();

    @FXML
    private void ingresarAction(ActionEvent event) {
        String username = tfNombre.getText();
        String password = tfCedula.getText();
        
        Vendedor vendedor = authService.login(username, password);

        if(vendedor != null) {
            lbNombre.setText(vendedor.getNombre());
            lbApellidos.setText(vendedor.getApellidos());
            lbCedula.setText(vendedor.getCedula());

        } else {

        }
    }
}
