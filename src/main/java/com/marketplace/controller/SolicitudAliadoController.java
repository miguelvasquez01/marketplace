package com.marketplace.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.marketplace.App;
import com.marketplace.model.Vendedor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

public class SolicitudAliadoController implements Initializable {

    @FXML
    private GridPane gridPane;

    private Vendedor vendedorAutenticado;

    private List<Vendedor> solicitudes;

    private void loadAliados() {
        gridPane.getChildren().clear(); // Limpiar el GridPane antes de agregar los nuevos contactos
        int column = 0;
        int row = 1;
    
        try {
            if (solicitudes != null) {
                for (Vendedor vendedor : solicitudes) {
                    
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(App.class.getResource("solicitudItemView.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();
    
                    SolicitudItemController solicitudItemController = fxmlLoader.getController();
                    solicitudItemController.setData(vendedor, vendedorAutenticado);//Para añadir el vendedor a los contactos
    
                    if (column == 1) {
                        column = 0;
                        row++;
                    }
    
                    gridPane.add(anchorPane, column++, row);
    
                    // Configurar el tamaño del grid
                    gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                    gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    gridPane.setMaxWidth(Region.USE_PREF_SIZE);
    
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

    public void setVendedorAutenticado(Vendedor vendedor) {
        this.vendedorAutenticado = vendedor;
        this.solicitudes = vendedorAutenticado.getSolicitudesDeContacto();
        //Si no sirve poner loadAliados aquí
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadAliados();
    }
}
