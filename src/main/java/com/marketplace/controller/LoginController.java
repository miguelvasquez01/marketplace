package com.marketplace.controller;

import java.io.IOException;

import com.marketplace.App;
import com.marketplace.model.Vendedor;
import com.marketplace.services.AuthService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField tfCedula;

    @FXML
    private TextField tfNombreUsuario;

    private AuthService authService = new AuthService();

    @FXML
    void btnEntrar(ActionEvent event) {
        String username = tfNombreUsuario.getText();
        String password = tfCedula.getText();

        Vendedor vendedor = authService.login(username, password);

        if (vendedor != null) {
            System.out.println("Hola");

        } else {

        }
    }

    @FXML
    void btnRegistrarse(ActionEvent event) {
        try {
            App.setRoot("registroView");
        } catch (IOException e) {
            e.printStackTrace(); // Maneja la excepción adecuadamente
        }
    }
}
