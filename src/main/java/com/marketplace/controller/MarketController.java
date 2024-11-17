package com.marketplace.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

import com.marketplace.App;
import com.marketplace.interfaces.MyListener;
import com.marketplace.model.Producto;
import com.marketplace.model.Vendedor;
import com.marketplace.services.ActualizarVendedorService;

public class MarketController {

    @FXML
    private Label lbLike;

    @FXML
    private Button btnLikeAtributo;

    @FXML
    private Button btnComentarAtributo;

    @FXML
    private Label lbNombreVendedor;

    @FXML
    private Button btnComprarAtributo;
    
    @FXML
    private VBox chosenProductCard;

    @FXML
    private ImageView imgProducto;

    @FXML
    private Label lbNombreProducto;

    @FXML
    private Label lbPrecioProducto;

    @FXML
    private Label lbCategoria;

    @FXML
    private Label lbEstado;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane gridPane;

    private ActualizarVendedorService actualizarVendedorService = new ActualizarVendedorService();

    private Vendedor contacto;
    private Vendedor vendedorAutenticado;

    private List<Producto> productos;
    private Image image;
    private MyListener myListener;
    private List<Producto> productosConLike = new ArrayList<>();
    private Producto productoSeleccionado;

    @FXML
    void btnComentar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marketplace/comentarioView.fxml"));
        Pane root = loader.load();

        ComentarioController ComentarioController = loader.getController();
        ComentarioController.setProducto(productoSeleccionado, contacto);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    void btnLike(ActionEvent event) {
        int numLikes = productoSeleccionado.getMeGustas();
        productoSeleccionado.setMeGustas(numLikes + 1);
        lbLike.setText(numLikes + 1 + "");

        productosConLike.add(productoSeleccionado);
        btnLikeAtributo.setDisable(true);
        btnLikeAtributo.setStyle("-fx-background-color: grey;");
    }

    @FXML
    void btnComprar(ActionEvent event) {

        if (productoSeleccionado != null) {

            contacto.getProductos().remove(productoSeleccionado);

            List<Producto> productosA = vendedorAutenticado.getProductos();
            if (productosA == null) {//Si al comprar aún no tiene productos
                productosA = new ArrayList<>();
                vendedorAutenticado.setProductos(productosA);
            }
            vendedorAutenticado.getProductos().add(productoSeleccionado);

            actualizarVendedorService.actualizar(contacto);
            actualizarVendedorService.actualizar(vendedorAutenticado);
    
            productos.remove(productoSeleccionado);
            refreshGrid();
        }
    }

    public void setData(Vendedor contacto, Vendedor vendedorAutenticado) {
        this.contacto = contacto;
        this.vendedorAutenticado = vendedorAutenticado;
        this.productos = contacto.getProductos();

        lbNombreVendedor.setText(contacto.getNombre()+ " " +contacto.getApellidos());
    }

    public void setChosenProducto(Producto producto) {
        lbNombreProducto.setText(producto.getNombre());
        lbPrecioProducto.setText("$"+producto.getPrecio());
        lbCategoria.setText(producto.getCategoria());
        lbEstado.setText(producto.getEstado().toString());
        lbLike.setText(Integer.toString(producto.getMeGustas()));

        if (productosConLike.contains(producto)) {
            btnLikeAtributo.setDisable(true);
            btnLikeAtributo.setStyle("-fx-background-color: grey;");
        } else {
            btnLikeAtributo.setDisable(false);
            btnLikeAtributo.setStyle("-fx-background-color: orange;");
        }
        //image = new Image(getClass().getResourceAsStream(producto.getImagen()));
        imgProducto.setImage(image);
        //añadir demás atributos
    }

    @FXML
    void btnVolver(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/marketplace/dashboardView.fxml"));
        Pane root = loader.load();

        DashBoardController dashBoardController = loader.getController();
        dashBoardController.setVendedor(vendedorAutenticado);

        Stage stage = (Stage) scrollPane.getParent().getParent().getScene().getWindow(); //Obtener la ventana actual
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void initView() {

        btnComprarAtributo.setDisable(true);
        btnComprarAtributo.setStyle("-fx-background-color: grey;");

        btnLikeAtributo.setDisable(true);
        btnLikeAtributo.setStyle("-fx-background-color: grey;");

        btnComentarAtributo.setDisable(true);
        btnComentarAtributo.setStyle("-fx-background-color: grey;");

        if (productos == null) {
            productos = new ArrayList<>();
        }

        if (productos.size() > 0) {
            //setChosenProducto(productos.get(0));
            myListener = new MyListener() {

                @Override
                public void onClickListener(Producto producto) {
                    btnComprarAtributo.setDisable(false);
                    btnComprarAtributo.setStyle("-fx-background-color: orange;");

                    btnComentarAtributo.setDisable(false);
                    btnComentarAtributo.setStyle("-fx-background-color: orange;");
                    
                    setChosenProducto(producto);
                    productoSeleccionado = producto;
                }
                
            };
        }
        refreshGrid();
    }

    public void refreshGrid() {
        gridPane.getChildren().clear();

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
