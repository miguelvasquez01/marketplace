package com.marketplace.controller;

import java.io.IOException;

import com.marketplace.App;
import com.marketplace.model.Vendedor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class DashBoardController {
    
    @FXML
    private VBox contentVbox;

    @SuppressWarnings("unused")
    private Vendedor vendedorAutenticado;

    @FXML
    void btnCerrarSesión(ActionEvent event) throws IOException {
        App.setRoot("loginView");
    }

    @FXML
    void btnEstadisticas(ActionEvent event) {

    }

    @FXML
    void btnMiPerfil(ActionEvent event) {

    }

    @FXML
    void btnMisContactos(ActionEvent event) {

    }

    @FXML
    void btnMisProductos(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marketplace/misProductosView.fxml"));
            Pane newPane = loader.load();

            MisProductosController misProductosController = loader.getController();
            misProductosController.setVendedor(vendedorAutenticado);

            //Vincular las propiedades de tamaño del nuevo pane con el contentPane
            newPane.prefWidthProperty().bind(contentVbox.widthProperty());
            newPane.prefHeightProperty().bind(contentVbox.heightProperty());


            //Reemplazar el contenido del Pane con la nueva escena
            contentVbox.getChildren().clear();
            contentVbox.getChildren().add(newPane);

            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedorAutenticado = vendedor;
    }
}