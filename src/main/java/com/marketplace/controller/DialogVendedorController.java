package com.marketplace.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.marketplace.model.Vendedor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DialogVendedorController implements Initializable {

    @FXML
    private TextField tfApellidos;

    @FXML
    private TextField tfDireccion;

    @FXML
    private TextField tfNombre;
    
    @FXML
    private Label lbAdvertencia;

    private Vendedor vendedorEditado;

    @FXML
    void btnAceptar(ActionEvent event) {

        String nombre = tfNombre.getText();
        String apellidos = tfApellidos.getText();
        String direccion = tfDireccion.getText();

        if (nombre.isEmpty() || apellidos.isEmpty() || direccion.isEmpty()) {
            lbAdvertencia.setText("Por favor, rellene todos los campos");
            lbAdvertencia.setVisible(true);
            return;
        }

        vendedorEditado.setNombre(nombre);
        vendedorEditado.setApellidos(apellidos);
        vendedorEditado.setDireccion(direccion);

        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText("Operación exitosa");
        alerta.setContentText("Vendedor actualizado correctamente, por favor actualize la página para visualizar los cambios");
        alerta.showAndWait();

        Stage stage = (Stage)tfNombre.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnCancelar(ActionEvent event) {
        Stage stage = (Stage)tfNombre.getScene().getWindow();
        stage.close();
    }

    public void initAtributos(Vendedor vendedor) {

        this.vendedorEditado = vendedor;

        tfNombre.setText(vendedor.getNombre());
        tfApellidos.setText(vendedor.getApellidos());
        tfDireccion.setText(vendedor.getDireccion());
    }

    public Vendedor getVendedorEditado() {
        return vendedorEditado;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lbAdvertencia.setVisible(false);
    }
}
