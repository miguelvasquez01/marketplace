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
    
        if (producto != null) { //Si estamos editando un producto
            fechaPublicacion = LocalDate.parse(producto.getFechaPublicacion(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            estado = cbEstado.getValue();
        }
    
        String nombre = tfNombre.getText();
        String codigoStr = tfCodigo.getText();
        String imagen = tfImagen.getText();
        String categoria = tfCategoria.getText();
        String precioStr = tfPrecio.getText();
    
        if (!validarCampos(nombre, codigoStr, imagen, categoria, precioStr)) {
            return; //Valida que todos los campos estén correctamente rellenados y números correctos
        }
    
        int codigo = Integer.parseInt(codigoStr);
        double precio = Double.parseDouble(precioStr);
    
        //Si productos no es nulo y estamos creando un nuevo producto o cambiando el código de uno existente
        if (productos != null) {
            boolean codigoRepetido = productos.stream()
                    .anyMatch(p -> p.getCodigo() == codigo && (producto == null || p != producto));//Evitar duplicados en edición
    
            if (codigoRepetido) {
                advertencia.setText("Ya existe un producto con este código");
                advertencia.setVisible(true);
                return;
            }
        }
    
        if (producto != null) { // Si es una edición, actualizamos los valores
            producto.setNombre(nombre);
            producto.setCodigo(codigo);
            producto.setImagen(imagen);
            producto.setCategoria(categoria);
            producto.setPrecio(precio);
            producto.setFechaPublicacion(fechaPublicacion.toString());
            producto.setEstado(estado);
        } else { // Si es un nuevo producto, lo creamos
            Producto nuevoProducto = Producto.builder()
                    .nombre(nombre)
                    .codigo(codigo)
                    .imagen(imagen)
                    .categoria(categoria)
                    .precio(precio)
                    .fechaPublicacion(fechaPublicacion)
                    .estado(estado)
                    .build();
    
            this.producto = nuevoProducto;
        }
        MisProductosController.productoSeleccionado = null;

        Stage stage = (Stage) lbTitulo.getScene().getWindow();
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
