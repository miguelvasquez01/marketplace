package com.marketplace.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.marketplace.model.Vendedor;

public class ActualizarVendedorService {
    
    private static final String URL = "http://localhost:8080/vendedores/actualizarVendedor/";

    public void actualizar(Vendedor vendedor) {
        try {
            // Convertir el objeto LoginRequest a JSON
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            String jsonRequest = objectMapper.writeValueAsString(vendedor);

            // Crear la solicitud HTTP POST
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(URL + vendedor.getCedula()))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .build();

            // Enviar la solicitud y recibir la respuesta
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Manejar la respuesta
            if (response.statusCode() == 200) {
                // Deserializar la respuesta JSON en un objeto Vendedor
                // Vendedor vendedorCreado = objectMapper.readValue(response.body(), Vendedor.class);
                // System.out.println("Actualización exitosa para: " + vendedorCreado.getNombre());
                // return vendedorCreado; // Devuelve el objeto Vendedor
                System.out.println("actualizado");
                System.out.println(jsonRequest);
            } else {
                System.out.println("Error de autenticación: " + response.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // return null; // Si la autenticación falla, devuelve null
    }
}
