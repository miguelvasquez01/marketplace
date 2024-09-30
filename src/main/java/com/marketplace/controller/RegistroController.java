package com.marketplace.controller;

import java.io.IOException;

import com.marketplace.App;
import com.marketplace.model.Vendedor;
import com.marketplace.services.RegisterService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

    private RegisterService registerService = new RegisterService();

    @FXML
    void btnCrearUsuario(ActionEvent event) {
        String nombre = tfNombre.getText();
        String apellidos = tfApellidos.getText();
        String cedula = tfCedula.getText();
        String direccion = tfDireccion.getText();

        Vendedor vendedor = registerService.register(nombre, apellidos, cedula, direccion);

        if (vendedor != null) {
            System.out.println("Vendedor creado");

        } else {

        }
        
        try{
            App.setRoot("loginview");
        }catch (IOException e){
            e.printStackTrace();
        }

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