package com.marketplace.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.marketplace.model.EstadoProducto;
import com.marketplace.model.Producto;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    private List<Producto> productos = new ArrayList<>();
    private Producto producto;

    @FXML
    void btnAceptar(ActionEvent event) {

        String nombre = tfNombre.getText();
        String codigoStr = tfCodigo.getText();
        String imagen = tfImagen.getText();
        String categoria = tfCategoria.getText();
        String precioStr = tfPrecio.getText();

        if (nombre.isEmpty() || codigoStr.isEmpty() || imagen.isEmpty() || categoria.isEmpty() || precioStr.isEmpty()) {
            advertencia.setText("Por favor, rellene todos los campos");
            advertencia.setVisible(true);
            return;
        }

        try {
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
                                              .fechaPublicacion(LocalDate.now())
                                              .estado(EstadoProducto.PUBLICADO)
                                              .build();
                                              System.out.println(producto.getFechaPublicacion());
            
            this.producto = producto;

            Stage stage = (Stage)lbTitulo.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            advertencia.setText("Ingreso incorrecto de código o precio");
            advertencia.setVisible(true);//Si no se ingresan números
        }
    }

    @FXML
    void btnCancelar(ActionEvent event) {

        Stage stage = (Stage)lbTitulo.getScene().getWindow();
        stage.close();
    }

    public void initAtributos(List<Producto> productos) {
        this.productos = productos;
    }

    public Producto getProducto() {
        return producto;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        advertencia.setVisible(false);
    }
}
