package com.marketplace.controller;

import java.io.IOException;
import java.net.ConnectException;

import com.marketplace.App;
import com.marketplace.model.Vendedor;
import com.marketplace.services.AuthService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField tfCedula;

    @FXML
    private TextField tfNombreUsuario;
    
    @FXML
    private Label lbAdvertencia;

    private AuthService authService = new AuthService();

    @FXML
    void btnEntrar(ActionEvent event) throws IOException {
        String username = tfNombreUsuario.getText();
        String password = tfCedula.getText();

        try {

        Vendedor vendedor = authService.login(username, password);

        if (vendedor != null) {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marketplace/dashboardView.fxml"));
                Parent root = loader.load();

                //Obtener el controlador de la vista de productos y pasarle el vendedor autenticado
                DashBoardController dashBoardController = loader.getController();
                dashBoardController.setVendedor(vendedor);

                Stage stage = (Stage) tfNombreUsuario.getScene().getWindow(); //Obtener la ventana actual
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            lbAdvertencia.setText("Nombre o cédula inválidos");
            lbAdvertencia.setVisible(true);

        }
        } catch(ConnectException e) {
            lbAdvertencia.setText("Error al conectar con el servidor");
            lbAdvertencia.setVisible(true);
            System.out.println("Error al conectar con el servidor");
        }
    }

    @FXML
    void btnRegistrarse(ActionEvent event) {
        try {
            App.setRoot("registroView");
        } catch (IOException e) {
            e.printStackTrace(); //Maneja la excepción adecuadamente
        }
    }
}
