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

    private Vendedor vendedorAutenticado;

    @FXML
    void btnCerrarSesion(ActionEvent event) throws IOException {
        App.setRoot("loginView");
    }

    @FXML
    void btnEstadisticas(ActionEvent event) {

    }

    @FXML
    void btnMiPerfil(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marketplace/miPerfilView.fxml"));
            Pane newPane = loader.load();

            MiPerfilController miPerfilController = loader.getController();
            miPerfilController.setVendedor(vendedorAutenticado);

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

    @FXML
    void btnMisContactos(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marketplace/misContactosView.fxml"));
            Pane newPane = loader.load();

            MisContactosController misContactosController = loader.getController();
            misContactosController.setVendedor(vendedorAutenticado);

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

    @FXML
    void btnChat(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marketplace/chatView.fxml"));
            Pane newPane = loader.load();


            ChatView chatViewController = loader.getController();
            chatViewController.setVendedor(vendedorAutenticado);


            newPane.prefWidthProperty().bind(contentVbox.widthProperty());
            newPane.prefHeightProperty().bind(contentVbox.heightProperty());


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
