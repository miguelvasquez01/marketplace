package com.marketplace.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.marketplace.model.EstadoProducto;
import com.marketplace.model.Producto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DialogProductoController implements Initializable {

    @FXML
    private Label lbTitulo;

    @FXML
    private Label advertencia;

    @FXML
    private TextField tfCategoria;

    @FXML
    private TextField tfCodigo;

    @FXML
    private TextField tfImagen;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfPrecio;

    @FXML
    private ComboBox<EstadoProducto> cbEstado;

    @FXML
    private VBox vBoxEstado;

    private List<Producto> productos = new ArrayList<>();
    private Producto producto;

    @FXML
    void btnAceptar(ActionEvent event) {

        LocalDate fechaPublicacion = LocalDate.now();
        EstadoProducto estado = EstadoProducto.PUBLICADO;

        if (producto != null) {//Si voy a editar el producto
            fechaPublicacion = LocalDate.parse(producto.getFechaPublicacion(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            estado = (EstadoProducto) cbEstado.getValue();
        }

        String nombre = tfNombre.getText();
        String codigoStr = tfCodigo.getText();
        String imagen = tfImagen.getText();
        String categoria = tfCategoria.getText();
        String precioStr = tfPrecio.getText();

        if (!validarCampos(nombre, codigoStr, imagen, categoria, precioStr)) {
            return; //Valida que rellene todos los campos y números correctos
        }

        int codigo = Integer.parseInt(codigoStr);
        double precio = Double.parseDouble(precioStr);

        if (productos != null) {
            Boolean codigoRepetido = productos.stream().anyMatch(p -> p.getCodigo() == codigo);
            if (codigoRepetido) {
                advertencia.setText("Ya existe un producto con este código");
                advertencia.setVisible(true);
                return;
            }
        }

        Producto producto = Producto.builder().nombre(nombre)
                                              .codigo(codigo)
                                              .imagen(imagen)//Verificar que sea ruta de imagen
                                              .categoria(categoria)
                                              .precio(precio)
                                              .fechaPublicacion(fechaPublicacion)
                                              .estado(estado)//Agregar me gustas?
                                              .build();
            
        this.producto = producto;

        Stage stage = (Stage)lbTitulo.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnCancelar(ActionEvent event) {
        this.producto = null;
        Stage stage = (Stage)lbTitulo.getScene().getWindow();
        stage.close();
    }

    public void initAtributos(List<Producto> productos) {
        this.productos = productos;
    }

    public void initLabels(Producto producto) {
        this.producto = producto;
        this.productos.remove(producto);

        tfNombre.setText(producto.getNombre());
        tfCodigo.setText(Integer.toString(producto.getCodigo()));
        tfImagen.setText(producto.getImagen());
        tfCategoria.setText(producto.getCategoria());
        tfPrecio.setText(Double.toString(producto.getPrecio()));
        cbEstado.setValue(producto.getEstado());
    }

    private boolean validarCampos(String nombre, String codigoStr, String imagen, String categoria, String precioStr) {
        if (nombre.isEmpty() || codigoStr.isEmpty() || imagen.isEmpty() || categoria.isEmpty() || precioStr.isEmpty()) {
            advertencia.setText("Por favor, rellene todos los campos");
            advertencia.setVisible(true);
            return false;
        }  
        try {
            Integer.parseInt(codigoStr);
            Double.parseDouble(precioStr);

        } catch (NumberFormatException e) {
            advertencia.setText("Ingreso incorrecto de código o precio");
            advertencia.setVisible(true);
            return false;
        }
        return true;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto (Producto producto) {
        this.producto = producto;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        advertencia.setVisible(false);

        ObservableList<EstadoProducto> list = FXCollections.observableArrayList(EstadoProducto.values());
        cbEstado.setItems(list);
    }

    @SuppressWarnings("exports")
    public VBox getvBoxEstado() {
        return vBoxEstado;
    }

    @SuppressWarnings("exports")
    public Label getLbTitulo() {
        return lbTitulo;
    }
}
