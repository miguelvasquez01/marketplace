package com.marketplace.controller;

import java.util.ArrayList;

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

        ArrayList<Vendedor> solicitudes = aliado.getSolicitudesDeContacto();
        if (solicitudes == null) {
            solicitudes = new ArrayList<>();
            aliado.setSolicitudesDeContacto(solicitudes);
        }
        try {
            this.aliado = aliado;
            this.vendedorAutenticado = vendedorAutenticado;

            lbNombre.setText(aliado.getNombre() + " " + aliado.getApellidos());
            lbMeGustas.setText("");//Integer.toString(aliado.getMuro().getMeGustas()) cuando el muro no sea null poner esto

            boolean solicitudYaEnviada = solicitudes.stream()
                .anyMatch(solicitud -> solicitud.getCedula().equals(vendedorAutenticado.getCedula()));

            if (solicitudYaEnviada) {
                System.out.println("loololololol");
                System.out.println("Solicitud ya enviada");
                btnAgregar.setDisable(true);
                btnAgregar.setText("Pendiente");
                btnAgregar.setStyle("-fx-background-color: grey;");
            }

        } catch (NullPointerException e) {
            System.out.println("Hay algo nulo en los vendedores (Muro)");
        }
    }
}
