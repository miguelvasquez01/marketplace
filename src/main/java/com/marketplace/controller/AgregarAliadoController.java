package com.marketplace.controller;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.marketplace.App;
import com.marketplace.model.Vendedor;
import com.marketplace.services.GetAllVendedoresService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

public class AgregarAliadoController implements Initializable {

    @FXML
    private GridPane gridPane;

    private Vendedor vendedorAutenticado;

    private GetAllVendedoresService getAllVendedoresService = new GetAllVendedoresService();
    private List<Vendedor> aliados;
    private List<Vendedor> contactos;

    private void loadAliados() {
        gridPane.getChildren().clear(); // Limpiar el GridPane antes de agregar los nuevos contactos
        int column = 0;
        int row = 1;
    
        try {
            if (aliados != null) {
                for (Vendedor vendedor : aliados) {
                    //Validar que el vendedor no está en los contactos del vendedor autenticado
                    if (!contactos.contains(vendedor)) {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(App.class.getResource("aliadoItemView.fxml"));
                        AnchorPane anchorPane = fxmlLoader.load();
    
                        AliadoItemController aliadoController = fxmlLoader.getController();
                        aliadoController.setData(vendedor, vendedorAutenticado);//Vendedor autenticado para mandarlo como solicitud
    
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public void setAliados() {
        try {
            this.aliados = getAllVendedoresService.getAllVendedores();
        } catch (ConnectException e) {
            e.printStackTrace();
        }
    }

    public void setVendedorAutenticado(Vendedor vendedor) {
        this.vendedorAutenticado = vendedor;
        if(contactos == null) {
            this.contactos = new ArrayList<>();//Si no hay crea un nuevo ArrayList
        }
        loadAliados();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        setAliados();
    }
}

