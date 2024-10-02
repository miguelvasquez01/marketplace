package com.marketplace.controller;

import com.marketplace.model.Producto;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ItemController {
    
    @FXML
    private ImageView imgItem;

    @FXML
    private Label lbNombreItem;

    @FXML
    private Label lbPrecioItem;

    @SuppressWarnings("unused")
    private Producto producto;

    public void setData(Producto producto) {
        this.producto = producto;
        lbNombreItem.setText(producto.getNombre());
        lbPrecioItem.setText("$"+producto.getPrecio());
        Image image = new Image(getClass().getResourceAsStream(producto.getImagen()));
        imgItem.setImage(image);
    }
}