package com.marketplace.controller;

import com.marketplace.model.Vendedor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ContactoController {

    @FXML
    private Label lbCedula;

    @FXML
    private Label lbNombre;

    private Vendedor contacto;

    @FXML
    void btnEliminar(ActionEvent event) {

    }

    @FXML
    void btnVerProductos(ActionEvent event) {
        
    }

    public void setData(Vendedor contacto) {
        try {
            this.contacto = contacto;
            //this.myListener = myListener;
            lbNombre.setText(contacto.getNombre() + " " + contacto.getApellidos());
            lbCedula.setText(contacto.getCedula());
            //Image image = new Image(getClass().getResourceAsStream(producto.getImagen()));//Lanza NullPointerException si no encuntra la ruta
            //imgItem.setImage(image);
            
        } catch(NullPointerException e) {
            System.out.println("No se encontró la ruta de la imágen de uno o varios de los productos");
        }
    }
}
