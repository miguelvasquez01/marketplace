package com.marketplace.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MisProductosController implements Initializable {

    @FXML
    private GridPane gridPane;

    @FXML
    private Label lbCategoria;

    @FXML
    private Label lbCodigo;

    @FXML
    private Label lbFechaPublicacion;

    @FXML
    private Label lbImagen;

    @FXML
    private Label lbMeGustas;

    @FXML
    private Label lbNombre;

    @FXML
    private Label lbPrecio;

    @FXML
    private Label lbEstado;

    private ActualizarVendedorService actualizarVendedorService = new ActualizarVendedorService();
    
    //Para eliminar y editar?
    static Producto productoSeleccionado;

    private MyListener myListener;
    private List<Producto> productos;
    private Vendedor vendedorAutenticado;

    @FXML
    void btnAgregarProducto(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marketplace/dialogProductoView.fxml"));
        Parent root = loader.load();

        DialogProductoController dialog = loader.getController();
        dialog.getvBoxEstado().setVisible(false);
        dialog.initAtributos(productos);//Verifica que el código no se repita

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
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
    void btnEditarProducto(ActionEvent event) throws IOException {
        
        if (productoSeleccionado != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marketplace/dialogProductoView.fxml"));
            Parent root = loader.load();

            DialogProductoController dialog = loader.getController();
            dialog.getLbTitulo().setText("Actualiza un producto");
            dialog.initAtributos(productos);//Verifica que el código no se repita
            dialog.initLabels(productoSeleccionado);//Establece los labels

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.showAndWait();

            Producto p = dialog.getProducto();

            if(p != null) {
                actualizarProducto(p);
                this.vendedorAutenticado.setProductos(productos);
                actualizarVendedorService.actualizar(vendedorAutenticado);
                loadProductos();
            }

        } else {
            System.out.println("Seleccione un producto");
            Alert alerta = new Alert(AlertType.INFORMATION);
            alerta.setTitle("Producto no seleccionado");
            alerta.setHeaderText("Error en la operación");
            alerta.setContentText("Por favor, seleccione un producto antes de continuar");
            alerta.showAndWait();
        }
    }

    @FXML
    void btnEliminarProducto(ActionEvent event) {
        
        if (productoSeleccionado != null) {
            Alert alerta = new Alert(AlertType.CONFIRMATION);
            alerta.setTitle("Eliminar Producto");
            alerta.setHeaderText("Confirmación de operación");
            alerta.setContentText("SE ELIMINARÁ SU PRODUCTO DE FORMA PERMANENTE");
            Optional<ButtonType> resultado = alerta.showAndWait();

            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                this.productos.remove(productoSeleccionado);
                this.vendedorAutenticado.setProductos(productos);
                actualizarVendedorService.actualizar(vendedorAutenticado);
                loadProductos();
                productoSeleccionado = null;//?
                
            } else {
                System.out.println("El usuario canceló la operación.");
            }
            
        } else {
            System.out.println("Seleccione un producto");
            Alert alerta = new Alert(AlertType.INFORMATION);
            alerta.setTitle("Producto no seleccionado");
            alerta.setHeaderText("Error en la operación");
            alerta.setContentText("Por favor, seleccione un producto antes de continuar");
            alerta.showAndWait();
        }
    }

    public void setChosenProducto(Producto producto) {

        MisProductosController.productoSeleccionado = producto;//Para eliminar y editar

        lbNombre.setText(producto.getNombre());
        lbCodigo.setText(Integer.toString(producto.getCodigo()));
        lbImagen.setText(producto.getImagen());
        lbCategoria.setText(producto.getCategoria());
        lbPrecio.setText(Double.toString(producto.getPrecio()));
        lbFechaPublicacion.setText(producto.getFechaPublicacion());
        lbMeGustas.setText(Integer.toString(producto.getMeGustas()));
        lbEstado.setText(producto.getEstado().toString());
    }

    private void loadProductos() {
        gridPane.getChildren().clear(); // Limpiar el GridPane antes de agregar los nuevos productos
        int column = 0;
        int row = 1;
    
        try {
            if (productos != null) {
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //setChosenProducto(productos.get(0));
        myListener = new MyListener() {

            @Override
            public void onClickListener(Producto producto) {//Producto del item
                setChosenProducto(producto);//Cuando haga click se ejecuta este método
            }
                
        };
    }

    public void setVendedor(Vendedor vendedor) {//Este método se llama despues de initialize
        this.vendedorAutenticado = vendedor;
        this.productos = vendedor.getProductos();
        if(productos != null) {
            System.out.println("Productos del vendedor: " + productos.size()); // Verifica si hay productos
        } else {
            this.productos = new ArrayList<>();//Si no hay crea un nuevo ArrayList
            System.out.println("Vendedor sin productos");
        }
        loadProductos();
    }

    private void actualizarProducto(Producto p) {
        for (Producto prod : productos) {
            if (prod.getCodigo() == p.getCodigo()) {
                // Actualizar directamente los atributos del producto encontrado
                prod.setNombre(p.getNombre());
                prod.setImagen(p.getImagen());
                prod.setCategoria(p.getCategoria());
                prod.setPrecio(p.getPrecio());
                prod.setEstado(p.getEstado());
                prod.setFechaPublicacion(p.getFechaPublicacion());
                break; // Salir del bucle una vez que se actualice el producto
            }
        }
    }
}