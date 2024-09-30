package com.marketplace.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.model.Vendedor;

public class RegisterService {

    private static final String AUTH_URL = "http://localhost:8080/vendedores/guardarVendedor";

    public Vendedor register(String nombre,String apellidos, String cedula, String direccion) { // Cambiado para devolver un Vendedor
        Vendedor vendedor = Vendedor.builder().apellidos(apellidos).nombre(nombre).direccion(direccion).cedula(cedula).build();

        try {
            // Convertir el objeto LoginRequest a JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonRequest = objectMapper.writeValueAsString(vendedor);

            // Crear la solicitud HTTP POST
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(AUTH_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .build();

            // Enviar la solicitud y recibir la respuesta
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Manejar la respuesta
            if (response.statusCode() == 200) {
                // Deserializar la respuesta JSON en un objeto Vendedor
                Vendedor vendedorCreado = objectMapper.readValue(response.body(), Vendedor.class);
                System.out.println("Login exitoso para: " + vendedorCreado.getNombre());
                return vendedorCreado; // Devuelve el objeto Vendedor
            } else {
                System.out.println("Error de autenticación: " + response.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Si la autenticación falla, devuelve null
    }
}