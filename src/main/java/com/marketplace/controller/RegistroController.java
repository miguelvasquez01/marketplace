package com.marketplace.controller;

import java.io.IOException;
import java.net.ConnectException;

import com.marketplace.App;
import com.marketplace.services.RegisterService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegistroController {

    @FXML
    private TextField tfApellidos;

    @FXML
    private TextField tfCedula;

    @FXML
    private TextField tfDireccion;

    @FXML
    private TextField tfNombre;

    @FXML
    private Label lbAdvertencia;

    private RegisterService registerService = new RegisterService();

    @FXML
    void btnCrearUsuario(ActionEvent event) throws IOException {
        String nombre = tfNombre.getText();
        String apellidos = tfApellidos.getText();
        String cedula = tfCedula.getText();
        String direccion = tfDireccion.getText();

        if (nombre.isEmpty() || apellidos.isEmpty() || cedula.isEmpty() || direccion.isEmpty()) {
            lbAdvertencia.setText("Por favor, rellene todos los campos de texto");
            lbAdvertencia.setVisible(true);
            return;
        }

        try {
            if (registerService.register(nombre, apellidos, cedula, direccion)) {
                Alert alerta = new Alert(AlertType.INFORMATION);
                alerta.setTitle("Información");
                alerta.setHeaderText("Operación exitosa");
                alerta.setContentText("Vendedor creado correctamente");
                alerta.showAndWait();

            } else {
                lbAdvertencia.setText("Ya existe un vendedor con esa cédula en el sistema");
                lbAdvertencia.setVisible(true);
                return;
            }
        } catch (ConnectException e) {
            lbAdvertencia.setText("Error al conectar con el servidor");
            lbAdvertencia.setVisible(true);
            return;
        }
        
        App.setRoot("loginView");
    }

    @FXML
    void btnVolverAInicio(ActionEvent event) {
        try {
            App.setRoot("loginView");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}