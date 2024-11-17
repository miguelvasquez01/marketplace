package com.marketplace.controller;

import java.util.ArrayList;

import com.marketplace.model.Vendedor;
import com.marketplace.services.ActualizarVendedorService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class SolicitudItemController {

    @FXML
    private HBox hBoxForRemove;

    @FXML
    private Label lbMeGustas;

    @FXML
    private Label lbNombre;

    private ActualizarVendedorService actualizarVendedorService = new ActualizarVendedorService();

    private Vendedor vendedorAutenticado;
    private Vendedor aliado;
    private AnchorPane anchorPane; // Referencia al AnchorPane actual


    @FXML
    void btnAceptar(ActionEvent event) {

        this.vendedorAutenticado.getContactos().add(aliado);
        this.vendedorAutenticado.getSolicitudesDeContacto().remove(aliado);
        actualizarVendedorService.actualizar(vendedorAutenticado);
        //Quitar el panel
        removePanel();
    }

    @FXML
    void btnRechazar(ActionEvent event) {

        this.vendedorAutenticado.getSolicitudesDeContacto().remove(aliado);
        actualizarVendedorService.actualizar(vendedorAutenticado);
        //Quitar el panel
        removePanel();
    }
    

    public void setData(Vendedor aliado, Vendedor vendedorAutenticado) {

        ArrayList<Vendedor> contactos = vendedorAutenticado.getContactos();
        if (contactos == null) {
            contactos = new ArrayList<>();
            vendedorAutenticado.setContactos(contactos);
        }
        try {
            this.aliado = aliado;
            this.vendedorAutenticado = vendedorAutenticado;
            this.anchorPane = (AnchorPane) hBoxForRemove.getParent(); // Obtener el AnchorPane actual

            lbNombre.setText(aliado.getNombre() + " " + aliado.getApellidos());
            lbMeGustas.setText("");//Integer.toString(aliado.getMuro().getMeGustas()) cuando el muro no sea null poner esto

        } catch (NullPointerException e) {
            System.out.println("Hay algo nulo en los vendedores (Muro)");
            e.printStackTrace();
        }
    }

    // Método para eliminar el panel actual del GridPane
    private void removePanel() {
        Node parent = anchorPane.getParent();
        if (parent instanceof GridPane) {
            GridPane gridPane = (GridPane) parent;
            gridPane.getChildren().remove(anchorPane); // Eliminar el AnchorPane del GridPane
        }
    }
}
