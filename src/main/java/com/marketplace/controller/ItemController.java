package com.marketplace.controller;

import com.marketplace.model.Producto;
import com.marketplace.interfaces.MyListener;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ItemController {
    
    @FXML
    private ImageView imgItem;

    @FXML
    private Label lbNombreItem;

    @FXML
    private Label lbPrecioItem;

    private Producto producto;
    private MyListener myListener;

    @SuppressWarnings("exports")
    @FXML
    public void click(MouseEvent mouseEvent) {
        myListener.onClickListener(producto);
    }

    @SuppressWarnings("exports")
    public void setData(Producto producto, MyListener myListener) {
        this.producto = producto;
        this.myListener = myListener;
        lbNombreItem.setText(producto.getNombre());
        lbPrecioItem.setText("$"+producto.getPrecio());
        Image image = new Image(getClass().getResourceAsStream(producto.getImagen()));//Lanza NullPointerException si no encuntra la ruta
        imgItem.setImage(image);
    }
}