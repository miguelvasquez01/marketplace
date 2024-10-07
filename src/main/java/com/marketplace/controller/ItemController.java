package com.marketplace.controller;

import com.marketplace.model.Producto;
import com.marketplace.interfaces.MyListener;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ItemController {
    
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView imgItem;

    @FXML
    private Label lbNombreItem;

    @FXML
    private Label lbPrecioItem;

    private Producto producto;
    private MyListener myListener;
    
    //Todos los paneles se refieren a esta variable
    private static ItemController selectedItem;

    @SuppressWarnings("exports")
    @FXML
    public void click(MouseEvent mouseEvent) {
        myListener.onClickListener(producto);

        //Si ya hay un item seleccionado, quitar el borde
        if (selectedItem != null) {
            selectedItem.anchorPane.getStyleClass().remove("product-item-border");
        }

        anchorPane.getStyleClass().add("product-item-border");//Agregar el borde al item seleccionado actual

        selectedItem = this; //Actualizar el item seleccionado
    }

    @SuppressWarnings("exports")
    public void setData(Producto producto, MyListener myListener) {
        try {
            this.producto = producto;
            this.myListener = myListener;
            lbNombreItem.setText(producto.getNombre());
            lbPrecioItem.setText("$"+producto.getPrecio());
            Image image = new Image(getClass().getResourceAsStream(producto.getImagen()));//Lanza NullPointerException si no encuntra la ruta
            imgItem.setImage(image);
            
        } catch(NullPointerException e) {
            System.out.println("No se encontró la ruta de la imágen de uno o varios de los productos");
        }
    }
}