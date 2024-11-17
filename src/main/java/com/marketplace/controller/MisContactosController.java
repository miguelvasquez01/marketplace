package com.marketplace.controller;

import java.io.IOException;
import java.util.List;

import com.marketplace.App;
import com.marketplace.model.Vendedor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MisContactosController {

    @FXML
    private GridPane gridPane;

    private List<Vendedor> contactos;
    private Vendedor vendedorAutenticado;

    @FXML
    void btnAgregarAliado(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marketplace/agregarAliadoView.fxml"));
        Parent root = loader.load();

        AgregarAliadoController agrAliadoController = loader.getController();
        agrAliadoController.setVendedorAutenticado(vendedorAutenticado);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.showAndWait();
    }

    @FXML
    void btnSolicitudesAmistad(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marketplace/solicitudAliadoView.fxml"));
        Parent root = loader.load();

        SolicitudAliadoController solicitudAliadoController = loader.getController();
        solicitudAliadoController.setVendedorAutenticado(vendedorAutenticado);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.showAndWait();

        loadVendedores();
    }


    private void loadVendedores() {
        gridPane.getChildren().clear(); // Limpiar el GridPane antes de agregar los nuevos contactos
        int column = 0;
        int row = 1;
    
        try {
            if (contactos != null) {
                for (Vendedor contacto : contactos) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(App.class.getResource("contactoView.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();
        
                    ContactoController contactoController = fxmlLoader.getController();
                    contactoController.setData(contacto, vendedorAutenticado);
        
                    if (column == 1) {
                        column = 0;
                        row++;
                    }
        
                    gridPane.add(anchorPane, column++, row);
        
                    //Set grid width
                    gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                    gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    gridPane.setMaxWidth(Region.USE_PREF_SIZE);
        
                    //Set grid height
                    gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                    gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    gridPane.setMaxHeight(Region.USE_PREF_SIZE);
        
                    GridPane.setMargin(anchorPane, new Insets(10));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void setVendedor(Vendedor vendedor) {//Este método se llama despues de initialize
        this.vendedorAutenticado = vendedor;
        //Setear los contactos
        this.contactos = vendedorAutenticado.getContactos();
        loadVendedores();
    }
}
