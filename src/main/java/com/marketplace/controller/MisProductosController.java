package com.marketplace.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import com.marketplace.App;
import com.marketplace.interfaces.MyListener;
import com.marketplace.model.Producto;
import com.marketplace.model.Vendedor;
import com.marketplace.services.ActualizarVendedorService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MisProductosController implements Initializable {

    @FXML
    private GridPane gridPane;

    private ActualizarVendedorService actualizarVendedorService = new ActualizarVendedorService();

    private MyListener myListener;
    private List<Producto> productos = new ArrayList<>();
    private Vendedor vendedorAutenticado;

    @FXML
    void btnAgregarProducto(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marketplace/dialogProductoView.fxml"));
        Parent root = loader.load();

        DialogProductoController dialog = loader.getController();
        dialog.initAtributos(productos);//Verifica que el código no se repita//Verificar que sea ruta de imagen

        Stage stage = new Stage();
        stage.setTitle("Agrega un producto");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        Producto p = dialog.getProducto();

        if(p != null) {
            this.productos.add(p);
            this.vendedorAutenticado.setProductos(productos);
            actualizarVendedorService.actualizar(vendedorAutenticado);
            loadProductos();
        }
    }

    @FXML
    void btnEditarProducto(ActionEvent event) {

    }

    @FXML
    void btnEliminarProducto(ActionEvent event) {

    }

    public void setChosenProducto(Producto producto) {
        // lbNombreProducto.setText(producto.getNombre());
        // lbPrecioProducto.setText("$"+producto.getPrecio());
        // image = new Image(getClass().getResourceAsStream(producto.getImagen()));
        // imgProducto.setImage(image);
        //añadir demás atributos
    }

    private void loadProductos() {
        gridPane.getChildren().clear(); // Limpiar el GridPane antes de agregar los nuevos productos
        int column = 0;
        int row = 1;
    
        try {
            for (Producto producto : productos) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("itemView.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
    
                ItemController itemController = fxmlLoader.getController();
                itemController.setData(producto, myListener);
    
                if (column == 3) {
                    column = 0;
                    row++;
                }
    
                gridPane.add(anchorPane, column++, row);
    
                //Set grid width
                gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxWidth(Region.USE_PREF_SIZE);
    
                //Set grid height
                gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxHeight(Region.USE_PREF_SIZE);
    
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (productos.size() > 0) {
            //setChosenProducto(productos.get(0));
            myListener = new MyListener() {

                @Override
                public void onClickListener(Producto producto) {
                    setChosenProducto(producto);//Cuando haga click se ejecuta este método
                }
                
            };
        }
    }

    public void setVendedor(Vendedor vendedor) {//Este método se llama despues de initialize
        this.vendedorAutenticado = vendedor;
        this.productos = vendedor.getProductos();
        System.out.println("Productos del vendedor: " + productos.size()); // Verifica si hay productos
        loadProductos();
    }
}