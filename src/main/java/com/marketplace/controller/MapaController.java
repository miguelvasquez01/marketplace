package com.marketplace.controller;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import javafx.scene.control.Alert;

public class MapaController {
    @FXML
    private WebView webView;

    @FXML
    public void initialize() {
        String htmlContent = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <meta http-equiv="permissions-policy" content="geolocation=*">
                <link rel="stylesheet" href="https://unpkg.com/leaflet@1.7.1/dist/leaflet.css"/>
                <script src="https://unpkg.com/leaflet@1.7.1/dist/leaflet.js"></script>
                <style>
                    #map { height: 100vh; width: 100%; }
                    body { margin: 0; }
                </style>
            </head>
            <body>
                <div id="map"></div>
                <script>
                    var map = L.map('map').setView([4.5709, -74.2973], 13);
                    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                        attribution: '© OpenStreetMap contributors'
                    }).addTo(map);
                </script>
            </body>
            </html>
            """;
        
        WebEngine webEngine = webView.getEngine();
        webEngine.loadContent(htmlContent);
    }

    @FXML
    public void handleObtenerUbicacion() {
        obtenerUbicacion();
    }

    private void obtenerUbicacion() {
        try {
            WebEngine webEngine = webView.getEngine();
            webEngine.executeScript(
                "if ('geolocation' in navigator) {" +
                "  navigator.geolocation.getCurrentPosition(function(position) {" +
                "    var lat = position.coords.latitude;" +
                "    var lng = position.coords.longitude;" +
                "    map.setView([lat, lng], 15);" +
                "    L.marker([lat, lng]).addTo(map)" +
                "      .bindPopup('Tu ubicación actual').openPopup();" +
                "  }, function(error) {" +
                "    alert('Error al obtener ubicación: ' + error.message);" +
                "  });" +
                "} else {" +
                "  alert('Geolocalización no está disponible en este navegador');" +
                "}"
            );
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo obtener la ubicación", e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String header, String contenido) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(header);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
} 