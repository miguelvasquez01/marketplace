package com.marketplace.controller;

import java.io.IOException;

import com.marketplace.model.Vendedor;
import com.marketplace.services.ActualizarVendedorService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ContactoController {

    @FXML
    private HBox hBoxForRemove;

    @FXML
    private Label lbCedula;

    @FXML
    private Label lbNombre;

    private AnchorPane anchorPane;

    private ActualizarVendedorService actualizarVendedorService = new ActualizarVendedorService();

    private Vendedor vendedorAutenticado;
    private Vendedor contacto;

    @FXML
    void btnEliminar(ActionEvent event) {

        vendedorAutenticado.getContactos().remove(contacto);
        actualizarVendedorService.actualizar(vendedorAutenticado);
        removePanel();
    }

    @FXML
    void btnVerProductos(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marketplace/marketView.fxml"));
        Pane root = loader.load();

        MarketController marketController = loader.getController();
        marketController.setData(contacto, vendedorAutenticado);
        marketController.initView();

        Stage stage = (Stage) anchorPane.getParent().getParent().getScene().getWindow(); //Obtener la ventana actual
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void setData(Vendedor contacto, Vendedor vendedorAutenticado) {
        try {
            this.contacto = contacto;
            this.vendedorAutenticado = vendedorAutenticado;
            this.anchorPane = (AnchorPane) hBoxForRemove.getParent();
            //this.myListener = myListener;
            lbNombre.setText(contacto.getNombre() + " " + contacto.getApellidos());
            lbCedula.setText(contacto.getCedula());
            //Image image = new Image(getClass().getResourceAsStream(producto.getImagen()));//Lanza NullPointerException si no encuntra la ruta
            //imgItem.setImage(image);
            
        } catch(NullPointerException e) {
            System.out.println("No se encontró la ruta de la imágen de uno o varios de los productos");
        }
    }

    private void removePanel() {
        Node parent = anchorPane.getParent();
        if (parent instanceof GridPane) {
            GridPane gridPane = (GridPane) parent;
            gridPane.getChildren().remove(anchorPane); // Eliminar el AnchorPane del GridPane
        }
    }
}
