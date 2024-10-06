package com.marketplace.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.ResourceBundle;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import com.marketplace.App;
import com.marketplace.interfaces.MyListener;
import com.marketplace.model.EstadoProducto;
import com.marketplace.model.Producto;

public class MarketController implements Initializable {
    
    @FXML
    private VBox chosenProductCard;

    @FXML
    private ImageView imgProducto;

    @FXML
    private Label lbNombreProducto;

    @FXML
    private Label lbPrecioProducto;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridPane;

    private List<Producto> productos = new ArrayList<>();
    private Image image;
    private MyListener myListener;

    private List<Producto> getData() {
        
        List<Producto> productos = new ArrayList<>();
        Producto producto;

        
        producto = new Producto();
        producto.setNombre("ProductoEjemplo");
        producto.setPrecio(5.00);
        producto.setImagen("/com/marketplace/assets/img/logo1.jpg");
        producto.setCategoria("Tecnología");
        producto.setEstado(EstadoProducto.PUBLICADO);
        productos.add(producto);

        producto = new Producto();
        producto.setNombre("ProductoEjemplo2");
        producto.setPrecio(9.99);
        producto.setImagen("/com/marketplace/assets/img/images (3).jpg");
        producto.setCategoria("Tecnología");
        producto.setEstado(EstadoProducto.PUBLICADO);
        productos.add(producto);
        
        return productos;
    }

    public void setChosenProducto(Producto producto) {
        lbNombreProducto.setText(producto.getNombre());
        lbPrecioProducto.setText("$"+producto.getPrecio());
        image = new Image(getClass().getResourceAsStream(producto.getImagen()));
        imgProducto.setImage(image);
        //añadir demás atributos
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productos.addAll(getData());
        if (productos.size() > 0) {
            //setChosenProducto(productos.get(0));
            myListener = new MyListener() {

                @Override
                public void onClickListener(Producto producto) {
                    setChosenProducto(producto);
                }
                
            };
        }
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i<productos.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("itemView.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                //Le mando el producto y el listener con con la implementación del método
                itemController.setData(productos.get(i), myListener);

                if(column == 3) {
                    column = 0;
                    row++;
                }

                gridPane.add(anchorPane, column++, row);

                //set grid width
                gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
