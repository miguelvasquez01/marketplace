package com.marketplace.controller;

import com.marketplace.model.Vendedor;
import com.marketplace.services.ActualizarVendedorService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AliadoItemController {

    @FXML
    private Label lbMeGustas;

    @FXML
    private Label lbNombre;

    @FXML
    private Button btnAgregar;

    private ActualizarVendedorService actualizarVendedorService = new ActualizarVendedorService();

    private Vendedor vendedorAutenticado;
    private Vendedor aliado;

    @FXML
    void btnAgregar(ActionEvent event) {

        this.aliado.getSolicitudesDeContacto().add(vendedorAutenticado);
        actualizarVendedorService.actualizar(aliado);

        btnAgregar.setDisable(true);
        btnAgregar.setText("Pendiente");
        btnAgregar.setStyle("-fx-background-color: grey;");
    }
    

    public void setData(Vendedor aliado, Vendedor vendedorAutenticado) {
        try {
            this.aliado = aliado;
            this.vendedorAutenticado = vendedorAutenticado;

            lbNombre.setText(aliado.getNombre() + " " + aliado.getApellidos());
            lbMeGustas.setText(Integer.toString(aliado.getMuro().getMeGustas()));

            // Verificar si ya se ha enviado una solicitud de contacto
            if (aliado.getSolicitudesDeContacto().contains(vendedorAutenticado)) {
                // Deshabilitar el botón y cambiar su texto y estilo si ya se envió la solicitud
                btnAgregar.setDisable(true);
                btnAgregar.setText("Pendiente");
                btnAgregar.setStyle("-fx-background-color: grey;");
            }

        } catch (NullPointerException e) {
            System.out.println("Hay algo nulo en los vendedores (Muro)");
        }
    }
}
