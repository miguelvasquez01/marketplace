package com.marketplace.controller;

import java.io.IOException;
import java.util.Optional;

import com.marketplace.App;
import com.marketplace.model.Vendedor;
import com.marketplace.services.ActualizarVendedorService;
import com.marketplace.services.EliminarVendedorService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MiPerfilController {

    @FXML
    private GridPane gridPane;

    @FXML
    private Label lbApellidos;

    @FXML
    private Label lbCedula;

    @FXML
    private Label lbDireccion;

    @FXML
    private Label lbLikes;

    @FXML
    private Label lbNombre;

    @FXML
    private Label lbNombreCompleto;

    private ActualizarVendedorService actuVendService = new ActualizarVendedorService();
    private EliminarVendedorService elimVendService = new EliminarVendedorService();
    private Vendedor vendedorAutenticado;

    @FXML
    void btnEditarPerfil(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marketplace/dialogVendedorView.fxml"));
        Parent root = loader.load();

        DialogVendedorController dialog = loader.getController();
        dialog.initAtributos(vendedorAutenticado);//Pone los atributos para editarlos

        Stage stage = new Stage();
        stage.setTitle("Editar perfil");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        vendedorAutenticado = dialog.getVendedorEditado();

        actuVendService.actualizar(vendedorAutenticado);
    }

    @FXML
    void btnEliminarCuenta(ActionEvent event) throws IOException {

        Alert alerta = new Alert(AlertType.CONFIRMATION);
        alerta.setTitle("Eliminar Cuenta");
        alerta.setHeaderText("Confirmación de operación");
        alerta.setContentText("SE ELIMINARÁ SU CUENTA DE FORMA PERMANENTE");
        
        Optional<ButtonType> resultado = alerta.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            elimVendService.eliminar(vendedorAutenticado.getCedula());
            App.setRoot("loginView");
            
        } else {
            System.out.println("El usuario canceló la operación.");
        }
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedorAutenticado = vendedor;

        initLabels();//El método initialize se ejecuta despues de inicializar la escena y el vendedor no ha sido establecido
    }

    private void initLabels() {
        String nombreCompleto = vendedorAutenticado.getNombre()+" "+vendedorAutenticado.getApellidos();
        lbNombreCompleto.setText(nombreCompleto);
        lbNombre.setText(vendedorAutenticado.getNombre());
        lbApellidos.setText(vendedorAutenticado.getApellidos());
        lbCedula.setText(vendedorAutenticado.getCedula());
        lbDireccion.setText(vendedorAutenticado.getDireccion());
        //lbLikes
    }
}