package com.marketplace.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class EliminarVendedorService {
 
    private static final String URL = "http://localhost:8080/vendedores/eliminarVendedor/";

    public void eliminar(String cedula) {
        try {
            // Crear la solicitud HTTP POST
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(URL + cedula))
                    .header("Content-Type", "application/json")
                    .DELETE()
                    .build();

            // Enviar la solicitud y recibir la respuesta
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Manejar la respuesta
            if (response.statusCode() == 200) {
                System.out.println("Vendedor eliminado");

            } else {
                System.out.println("Error de autenticación: " + response.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // return null; // Si la autenticación falla, devuelve null
    }
}
