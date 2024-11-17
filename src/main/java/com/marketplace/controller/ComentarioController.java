package com.marketplace.controller;

import java.util.ArrayList;
import java.util.List;

import com.marketplace.model.Comentario;
import com.marketplace.model.Producto;
import com.marketplace.model.Vendedor;
import com.marketplace.services.ActualizarVendedorService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ComentarioController {

    @FXML
    private ListView<String> listaComentarios;

    @FXML
    private TextField tfComentario;

    private ActualizarVendedorService actualizarVendedorService = new ActualizarVendedorService();

    private Producto producto;
    private Vendedor contacto;
    private List<Comentario> comentarios;

    @FXML
    void btnEnviar(ActionEvent event) {

        String comentarioNuevo = tfComentario.getText();
        if (!comentarioNuevo.isEmpty()) {
            Comentario comentario = new Comentario();
            comentario.setMensaje(comentarioNuevo);
            producto.getComentarios().add(comentario);
            listaComentarios.getItems().add(comentarioNuevo);
            tfComentario.clear();
            actualizarVendedorService.actualizar(contacto);
        }
    }

    public void setProducto(Producto producto, Vendedor contacto) {
        this.producto = producto;
        this.contacto = contacto;
        this .comentarios = producto.getComentarios();
        if (comentarios == null) {
            comentarios = new ArrayList<>();
            producto.setComentarios(comentarios);
        }
        listaComentarios.getItems().setAll(obtenerComentarios());
    }

    public List<String> obtenerComentarios() {
        List<String> mensajes = new ArrayList<>();

        for (Comentario comentario: comentarios) {
            mensajes.add(comentario.getMensaje());
        }
        return mensajes;
    }
}
