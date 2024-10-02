package com.marketplace.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.ResourceBundle;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import com.marketplace.App;
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

    private List<Producto> getData() {
        
        List<Producto> productos = new ArrayList<>();
        Producto producto;

        for (int i = 0; i<20; i++) {
            producto = new Producto();
            producto.setNombre("ProductoEjemplo");
            producto.setPrecio(5.00);
            producto.setImagen("/com/marketplace/assets/img/logo1.jpg");
            producto.setCategoria("Tecnología");
            producto.setEstado(EstadoProducto.PUBLICADO);

            productos.add(producto);
        }
        return productos;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productos.addAll(getData());
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i<productos.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("itemView.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(productos.get(i));

                if(column == 3) {
                    column = 0;
                    row++;
                }

                gridPane.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
